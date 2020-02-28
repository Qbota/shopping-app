package com.shopping.api.controller;

import com.shopping.api.model.Product;
import com.shopping.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import java.security.InvalidParameterException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<Product> addNewProductTemplate(@RequestBody Product product){
        try{
            return new ResponseEntity<>(productService.registerNewProductTemplate(product), HttpStatus.CREATED);
        } catch (InvalidParameterException e){
            return new ResponseEntity<>(new Product(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getListOfProductsTemplates(){
        return new ResponseEntity<>(productService.getAllProductTemplates(), HttpStatus.FOUND);
    }

    @PostMapping("/family/{name}/product")
    public ResponseEntity<Product> addProductToFamily(@PathVariable String name, @RequestBody Product product){
        try {
            return new ResponseEntity<>(productService.addProductToFamilyShoppingList(product,name), HttpStatus.CREATED);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
        } catch (InvalidParameterException e){
            return new ResponseEntity<>(new Product(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/family/{name}/product")
    public ResponseEntity<List<Product>> getFamilyShoppingList(@PathVariable String name){
        try {
            return new ResponseEntity<>(productService.getFamilyShoppingList(name), HttpStatus.FOUND);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/{login}/product")
    public ResponseEntity<Product> assignProductToUser(@PathVariable String login, @RequestBody Product product){
        try {
            return new ResponseEntity<>(productService.addProductToUserShoppingList(product, login), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{login}/product")
    public ResponseEntity<List<Product>> getUserShoppingList(@PathVariable String login){
        try {
            return new ResponseEntity<>(productService.getUserShoppingList(login), HttpStatus.FOUND);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/{login}/product/{productId}")
    public ResponseEntity<Product> markUserProductAsBought(@PathVariable String login, @PathVariable String productId){
        try {
            return new ResponseEntity<>(productService.markUserProductAsBought(productId,login), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/family/{name}/product/{productId}")
    public ResponseEntity<Product> markFamilyProductAsBought(@PathVariable String name, @PathVariable String productId){
        try {
            return new ResponseEntity<>(productService.markFamilyProductAsBought(productId,name), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/family/{name}/product/{productId}/{login}")
    public ResponseEntity<Product> assignProductFromFamilyToUser(@PathVariable String name, @PathVariable String productId, @PathVariable String login){
        try {
            return new ResponseEntity<>(productService.assignProductFromFamilyToUser(productId, name, login), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
        }
    }

}
