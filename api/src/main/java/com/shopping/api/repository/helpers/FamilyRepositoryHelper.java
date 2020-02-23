package com.shopping.api.repository.helpers;

import com.shopping.api.model.Family;
import com.shopping.api.model.User;
import com.shopping.api.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FamilyRepositoryHelper {

    @Autowired
    private FamilyRepository repository;

    public Family addUserToFamily(User user, Family family){
        family.addMemeber(user);
        return repository.save(family);
    }
}
