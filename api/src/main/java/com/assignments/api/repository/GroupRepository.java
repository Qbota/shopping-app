package com.assignments.api.repository;

import com.assignments.api.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    Optional<Group> findByInviteCode(String code);
}
