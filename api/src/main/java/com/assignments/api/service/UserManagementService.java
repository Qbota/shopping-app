package com.assignments.api.service;

import com.assignments.api.error.exception.AuthenticationException;
import com.assignments.api.error.exception.DocumentInvalidException;
import com.assignments.api.error.exception.DocumentNotFoundException;
import com.assignments.api.model.Assignment;
import com.assignments.api.repository.AssignmentRepository;
import com.assignments.api.util.AuthenticationUtils;
import com.assignments.api.util.Validators;
import com.assignments.api.model.User;
import com.assignments.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    public User createUser(User user){
        Validators.insertUserValidator(user);
        validateLoginExists(user);
        String salt = AuthenticationUtils.generateSalt();
        user.setSalt(salt);

        String hashedPassword = AuthenticationUtils.generateSHA256HashOf(user.getPassword()+user.getSalt());
        user.setPassword(hashedPassword);
        user.setToken(AuthenticationUtils.generateTokenFor(user));

        return AuthenticationUtils.secureUserDataBeforeReturn(userRepository.insert(user));
    }

    private void validateLoginExists(User user) {
        if(userAlreadyExists(user.getLogin())){
            throw new DocumentInvalidException("User already exists");
        }
    }

    private boolean userAlreadyExists(String login) {
        return userRepository.findByLoginAndIsActiveTrue(login).isPresent();
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public User changeStatusOfUser(boolean status, String id) {
        User user = userRepository.findById(id).orElseThrow(DocumentNotFoundException::new);
        user.setActive(status);
        return userRepository.save(user);
    }

    public User authenticate(User user) {
        Validators.authenticateUserValidator(user);
        User repoUser = userRepository.findByLoginAndIsActiveTrue(user.getLogin()).orElseThrow(DocumentNotFoundException::new);

        if(!passwordsAreMatching(user, repoUser))
            throw new AuthenticationException("Passwords not matching");

        String token = AuthenticationUtils.generateTokenFor(repoUser);
        repoUser.setToken(token);
        return AuthenticationUtils.secureUserDataBeforeReturn(repoUser);
    }

    private boolean passwordsAreMatching(User inputUser, User repoUser){
        String inputPasswordHash = AuthenticationUtils.generateSHA256HashOf(inputUser.getPassword() + repoUser.getSalt());
        String repoPasswordHash = repoUser.getPassword();
        return inputPasswordHash.equals(repoPasswordHash);
    }

    public Map<String, Object> getUserWithAssignments(String id){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(id).orElseThrow(DocumentNotFoundException::new);
        result.put("data", user);
        List<Assignment> assignmentList = assignmentRepository.findByAssignee(user.getId()).orElse(new ArrayList<>());
        result.put("items", assignmentList);
        return result;
    }
}
