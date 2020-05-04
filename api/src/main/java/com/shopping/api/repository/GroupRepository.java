package com.shopping.api.repository;

import com.shopping.api.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GroupRepository extends MongoRepository<Group, String> {

    Optional<Group> findByInviteCode(String code);
}
