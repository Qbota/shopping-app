package com.shopping.api.service;

import com.shopping.api.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService extends AbstractService{

    public List<Group> getAllGroups(){
        return _repo.findAll();
    }
}
