package com.assignments.api.repository;

import com.assignments.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByIdAndIsActiveTrue(String id);
    Optional<User> findByLoginAndIsActiveTrue(String login);
    Optional<User> findById(String id);
    Optional<List<User>> findByGroupId(String groupId);
}
