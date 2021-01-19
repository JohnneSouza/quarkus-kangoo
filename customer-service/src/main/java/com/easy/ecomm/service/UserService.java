package com.easy.ecomm.service;

import com.easy.ecomm.model.User;
import com.easy.ecomm.model.UserRegisterRequest;
import com.easy.ecomm.repositories.UserRepository;
import com.easy.ecomm.translator.UserTranslator;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserRegisterRequest user){
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        User newUser = UserTranslator.userRegisterRequestToUser(user);
        return UserTranslator.userToUserRegisterResponse(userRepository.save(newUser));
    }

    public User findUserById(int id){
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public User finderByEmail(String email){
        return userRepository.findUserByEmail(email)
                .orElseThrow(NotFoundException::new);
    }


    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
