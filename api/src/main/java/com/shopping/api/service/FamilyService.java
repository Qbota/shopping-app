package com.shopping.api.service;

import com.shopping.api.model.Family;
import com.shopping.api.repository.FamilyRepository;
import com.shopping.api.repository.helpers.FamilyRepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyService {

    @Autowired
    private FamilyRepository repository;
    @Autowired
    private FamilyRepositoryHelper repositoryHelper;

    public Family createNewFamily(Family family) throws Exception{
        if(validateFamilyToCreate(family)){
            repository.insert(family);
            return family;
        }
        else {
            throw new Exception();
        }
    }

    public List<Family> getFamilyList(){
        return repository.findAll();
    }

    public Family getFamilyByName(String name) throws IllegalArgumentException, ChangeSetPersister.NotFoundException {
        if(name.isEmpty() || name.isBlank()){
            throw new IllegalArgumentException();
        }
        Family extractedFamily = repository.findByName(name);
        if(extractedFamily == null){
            throw new ChangeSetPersister.NotFoundException();
        }
        return extractedFamily;
    }

    private boolean validateFamilyToCreate(Family family){
        boolean canBeCreated = true;
        if(family == null){
            return !canBeCreated;
        }
        else if(family.getName().isBlank() || family.getName().isEmpty()){
            return !canBeCreated;
        }
        else if(repository.findByName(family.getName()) != null){
            return !canBeCreated;
        }
        else {
            return canBeCreated;
        }
    }
}
