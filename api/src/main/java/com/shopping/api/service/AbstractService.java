package com.shopping.api.service;

import com.shopping.api.model.Group;
import com.shopping.api.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public abstract class AbstractService {

    @Autowired
    public GroupRepository _repo;

}
