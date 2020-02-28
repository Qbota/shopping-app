package com.shopping.api.service;

import com.shopping.api.model.Family;
import com.shopping.api.model.Product;
import com.shopping.api.model.User;
import com.shopping.api.repository.FamilyRepository;
import com.shopping.api.repository.ProductRepository;
import com.shopping.api.repository.helpers.FamilyRepositoryHelper;
import com.shopping.api.validator.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.security.InvalidParameterException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private FamilyRepositoryHelper familyRepositoryHelper;

    public Product registerNewProductTemplate(Product product) throws InvalidParameterException {
        if(productCanBeRegistered(product))
            return productRepository.save(product);
        else {
            throw new InvalidParameterException();
        }
    }

    private boolean productCanBeRegistered(Product product){
        boolean isValid = new ValidatorFactory(product).getValidator().validate();
        if(isValid && productRepository.findByName(product.getName()) != null)
            return false;
        return true;
    }

    public List<Product> getAllProductTemplates(){
        return productRepository.findAll();
    }

    public List<Product> getFamilyShoppingList(String familyName) throws ChangeSetPersister.NotFoundException {
        Family targetFamily = familyRepository.findByName(familyName);
        if(targetFamily == null)
            throw new ChangeSetPersister.NotFoundException();
        return targetFamily.getDemandedProducts();
    }

    public List<Product> getUserShoppingList(String userLogin) throws ChangeSetPersister.NotFoundException {
        Family targetFamily = familyRepository.findByMembersLogin(userLogin);
        if(targetFamily == null)
            throw new ChangeSetPersister.NotFoundException();
        return targetFamily.getMember(userLogin).getShoppingList();
    }

    public Product addProductToUserShoppingList(Product product, String userLogin) throws ChangeSetPersister.NotFoundException {
        Family targetFamily = familyRepository.findByMembersLogin(userLogin);
        if(targetFamily == null)
            throw new ChangeSetPersister.NotFoundException();
        User user = targetFamily.getMember(userLogin);
        user.addProductToShoppingList(product);
        return familyRepositoryHelper.modifyUser(user,targetFamily).getMember(userLogin).getProduct(product.getId());
    }

    public Product markUserProductAsBought(String productId, String userLogin) throws ChangeSetPersister.NotFoundException {
        Family targetFamily = familyRepository.findByMembersLogin(userLogin);
        if(targetFamily == null)
            throw new ChangeSetPersister.NotFoundException();
        User user = targetFamily.getMember(userLogin);
        user.setProductAsBought(productId);
        return familyRepositoryHelper.modifyUser(user, targetFamily).getMember(userLogin).getProduct(productId);
    }

    public Product markFamilyProductAsBought(String productId, String familyName) throws ChangeSetPersister.NotFoundException {
        Family targetFamily = familyRepository.findByName(familyName);
        if(targetFamily == null)
            throw new ChangeSetPersister.NotFoundException();
        targetFamily.setProductAsBought(productId);
        return familyRepository.save(targetFamily).getDemandedProduct(productId);
    }

    public Product addProductToFamilyShoppingList(Product product, String familyName) throws ChangeSetPersister.NotFoundException, InvalidParameterException {
        Family targetFamily = familyRepository.findByName(familyName);
        if(targetFamily == null)
            throw new ChangeSetPersister.NotFoundException();
        if(!productCanBeAssigned(product)){
            throw new InvalidParameterException();
        }
        targetFamily.addDemandedProduct(product);
        return familyRepository.save(targetFamily).getDemandedProduct(product.getId());
    }

    private boolean productCanBeAssigned(Product product) {
        boolean isValid = new ValidatorFactory(product).getValidator().validate();
        if(isValid && product.getDateToBuy() != null){
            return true;
        }
        return false;
    }

    public Product assignProductFromFamilyToUser(String productId, String familyName, String userLogin) throws ChangeSetPersister.NotFoundException {
        Family targetFamily = familyRepository.findByName(familyName);
        if(targetFamily == null)
            throw new ChangeSetPersister.NotFoundException();
        Product product = targetFamily.getDemandedProduct(productId);
        if(product == null)
            throw new ChangeSetPersister.NotFoundException();
        User user = targetFamily.getMember(userLogin);
        if(user == null)
            throw new ChangeSetPersister.NotFoundException();
        targetFamily.removeProduct(product);
        user.addProductToShoppingList(product);
        targetFamily.modifyUser(user);
        List<Product> productList = familyRepository.save(targetFamily).getMember(userLogin).getShoppingList();
        return productList.get(productList.size()-1);
    }
}