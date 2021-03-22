package com.assignments.api.controller;

import com.assignments.api.model.Product;
import com.assignments.api.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group/{id}/shopping-list")
public class ShoppingListController {

    @Autowired
    private ShoppingService shoppingService;

    @GetMapping
    //@PreAuthorize("#id == authentication.principal.get('GroupId')")
    public ResponseEntity<Object> getShoppingListForGroup(@PathVariable String id){
        return ResponseEntity.ok(shoppingService.getShoppingList(id));
    }

    @PostMapping
    public ResponseEntity<Object> addNewProduct(@PathVariable String id, @RequestBody Product product){
        return ResponseEntity.accepted().body(shoppingService.addNewProduct(id, product));
    }

    @PutMapping
    public ResponseEntity<Object> editProduct(@PathVariable String id, @RequestBody Product product){
        return ResponseEntity.accepted().body(shoppingService.editProduct(id, product));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteProduct(@PathVariable String id, @RequestBody Product product){
        shoppingService.removeProduct(id, product);
        return ResponseEntity.ok().build();
    }
}
