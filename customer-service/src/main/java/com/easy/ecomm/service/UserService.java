package com.easy.ecomm.service;

import com.easy.ecomm.model.UserDTO;
import com.easy.ecomm.repositories.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO saveUser(UserDTO userDTO){
        userDTO.setPassword(BcryptUtil.bcryptHash(userDTO.getPassword()));
        return userRepository.save(userDTO);
    }

    public UserDTO findUserById(int id){
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public UserDTO finderByEmail(String email){
        return userRepository.findUserByEmail(email)
                .orElseThrow(NotFoundException::new);
    }


    public Iterable<UserDTO> findAll() {
        return userRepository.findAll();
    }
}
