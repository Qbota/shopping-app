package com.shopping.api.service;

import com.shopping.api.model.Family;
import com.shopping.api.model.Product;
import com.shopping.api.model.User;
import com.shopping.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.security.InvalidParameterException;
import java.util.ArrayList;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product registerNewProductTemplate(Product product) throws InvalidParameterException {
        if(productCanBeRegistered(product))
            return productRepository.save(product);
        else {
            throw new InvalidParameterException();
        }
    }

    private boolean productCanBeRegistered(Product product){
        boolean canBeRegistered = true;
        if(product == null)
            canBeRegistered = false;
        else if(product.getName().isBlank() || product.getName().isEmpty())
            canBeRegistered = false;
        return canBeRegistered;
    }

//    public List<Product> getAllProductTemplates(){
//        return productRepository.findAll();
//    }
//
//    public List<Product> getFamilyShoppingList(Family family){
//
//    }
//
//    public List<Product> getUserShoppingList(User user){
//
//    }
//
//    public Product assignProductToUserShoppingList(Product product, User user){
//
//    }
//
//    public Product markProductAsBought(Product product, User user){
//
//    }
}
