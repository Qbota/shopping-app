package com.shopping.api.repository;

import com.shopping.api.model.Family;
import com.shopping.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends MongoRepository<Family, String> {

    Family findByName(String name);
    Family findByMembersLogin(String login);
}
