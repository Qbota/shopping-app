package com.assignments.api.service;

import com.assignments.api.error.exception.DocumentInvalidException;
import com.assignments.api.error.exception.DocumentNotFoundException;
import com.assignments.api.model.Group;
import com.assignments.api.model.Product;
import com.assignments.api.repository.GroupRepository;
import com.assignments.api.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShoppingService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Product> getShoppingList(String id) {
        Group group = groupRepository.findByIdAndIsActiveTrue(id).orElseThrow(DocumentNotFoundException::new);
        return group.getShoppingList();
    }

    public Product addNewProduct(String id, Product product) {
        Validators.createShoppingProductValidator(product);
        Group group = groupRepository.findByIdAndIsActiveTrue(id).orElseThrow(DocumentNotFoundException::new);
        group.getShoppingList().add(product);
        groupRepository.save(group);
        return product;
    }

    public void removeProduct(String id, Product product) {
        Group group = groupRepository.findByIdAndIsActiveTrue(id).orElseThrow(DocumentNotFoundException::new);
        group.removeProduct(product);
        groupRepository.save(group);
    }

    public Product editProduct(String id, Product product) {
        Group group = groupRepository.findByIdAndIsActiveTrue(id).orElseThrow(DocumentNotFoundException::new);
        List<Product> products = group.getShoppingList();
        int index = products.indexOf(product);
        checkIfProductExists(index);
        products.set(index, product);
        groupRepository.save(group);
        return product;
    }

    private void checkIfProductExists(int index) {
        if(index == -1){
            throw new DocumentNotFoundException();
        }
    }
}
