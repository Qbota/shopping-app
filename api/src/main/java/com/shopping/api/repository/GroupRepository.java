package com.shopping.api.repository;

import com.shopping.api.model.Group;
import com.shopping.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {


}
