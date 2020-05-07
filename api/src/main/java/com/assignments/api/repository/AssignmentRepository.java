package com.assignments.api.repository;

import com.assignments.api.model.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {

    Optional<Assignment> findById(String id);
    Optional<List<Assignment>> findByAssignee(String assignee);
}
