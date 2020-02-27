package com.shopping.api.service;

import com.shopping.api.model.Family;
import com.shopping.api.repository.FamilyRepository;
import com.shopping.api.repository.helpers.FamilyRepositoryHelper;
import com.shopping.api.validator.ValidatorFactory;
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
      boolean isValid = new ValidatorFactory(family).getValidator().validate();
      if(isValid && repository.findByName(family.getName()) != null)
          return false;
      return true;
    }
}
