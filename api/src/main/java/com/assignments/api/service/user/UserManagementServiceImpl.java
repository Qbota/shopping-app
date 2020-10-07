package com.assignments.api.service.user;

import com.assignments.api.util.AuthenticationUtils;
import com.assignments.api.util.Validators;
import com.assignments.api.model.User;
import com.assignments.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService{

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) throws Exception{
        Validators.insertUserValidator(user);
        String salt = AuthenticationUtils.generateSalt();
        user.setSalt(salt);

        String hashedPassword = AuthenticationUtils.generateSHA256HashOf(user.getPassword()+user.getSalt());
        user.setPassword(hashedPassword);
        user.setToken(AuthenticationUtils.generateTokenFor(user));

        return AuthenticationUtils.secureUserDataBeforeReturn(userRepository.insert(user));
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public User changeStatusOfUser(boolean status, String id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(Exception::new);
        user.setActive(status);
        return userRepository.save(user);
    }

    public User authenticate(User user) throws Exception{
        Validators.authenticateUserValidator(user);
        User repoUser = userRepository.findByLoginAndIsActiveTrue(user.getLogin()).orElseThrow(Exception::new);

        if(!passwordsAreMatching(user, repoUser))
            throw new Exception("Passwords not matching");

        String token = AuthenticationUtils.generateTokenFor(repoUser);
        repoUser.setToken(token);
        return AuthenticationUtils.secureUserDataBeforeReturn(repoUser);
    }

    private boolean passwordsAreMatching(User inputUser, User repoUser){
        String inputPasswordHash = AuthenticationUtils.generateSHA256HashOf(inputUser.getPassword() + repoUser.getSalt());
        String repoPasswordHash = repoUser.getPassword();
        return inputPasswordHash.equals(repoPasswordHash);
    }

}
