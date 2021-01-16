package com.easy.ecomm.service;

import com.easy.ecomm.model.User;
import com.easy.ecomm.repositories.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        return userRepository.save(user);
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
