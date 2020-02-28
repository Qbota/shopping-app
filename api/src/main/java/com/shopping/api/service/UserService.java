package com.shopping.api.service;

import com.shopping.api.model.Family;
import com.shopping.api.model.User;
import com.shopping.api.repository.FamilyRepository;
import com.shopping.api.repository.helpers.FamilyRepositoryHelper;
import com.shopping.api.validator.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private FamilyRepositoryHelper familyRepositoryHelper;

    public List<User> getListOfUsers(){
        List<Family> familyList = familyRepository.findAll();
        if(familyList != null){
            return familyList.stream().map(x -> x.getMembers()).flatMap(List::stream).collect(Collectors.toList());
        }else{
            return new ArrayList();
        }
    }

    public User getUser(String login) throws ChangeSetPersister.NotFoundException {
        User extractedUser = familyRepository.findByMembersLogin(login).getMember(login);
        if(extractedUser == null){
            throw new ChangeSetPersister.NotFoundException();
        }
        return extractedUser;
    }

    public User addNewUserToFamily(User user, String familyName) throws ChangeSetPersister.NotFoundException, InvalidParameterException {
        if(!userIsValid(user)){
            throw new InvalidParameterException();
        }
        Family targetFamily = getTargetFamily(familyName);
        familyRepositoryHelper.addUserToFamily(user, targetFamily);
        return user;
    }

    public User removeUserFromFamily(User user, String familyName) throws ChangeSetPersister.NotFoundException, InvalidParameterException {
        if(!userIsValid(user)){
            throw new InvalidParameterException();
        }
        Family targetFamily = getTargetFamily(familyName);
        familyRepositoryHelper.removeUserFromFamily(user, targetFamily);
        return user;
    }

    private Family getTargetFamily(String familyName) throws ChangeSetPersister.NotFoundException, InvalidParameterException {
        if(familyName.isEmpty() || familyName.isBlank()){
            throw new InvalidParameterException();
        }
        Family targetFamily = familyRepository.findByName(familyName);
        if(targetFamily == null){
            throw new ChangeSetPersister.NotFoundException();
        }
        else{
            return targetFamily;
        }
    }

    private boolean userIsValid(User user) {
        boolean isValid = new ValidatorFactory(user).getValidator().validate();
        if(isValid && familyRepository.findByMembersLogin(user.getLogin()) != null){
            return false;
        }
        return true;
    }
}
