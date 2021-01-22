package com.easy.ecomm.service;

import com.easy.ecomm.model.User;
import com.easy.ecomm.model.dto.UserDto;
import com.easy.ecomm.repositories.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserDto userDto, String password){
        return userRepository.save(
                User.builder()
                .password(BcryptUtil.bcryptHash(password))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .createdAt(LocalDate.now())
                .active(false)
                .build());
    }

    public User findUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public User finderByEmail(String email){
        return userRepository.findUserByEmail(email)
                .orElseThrow(NotFoundException::new);
    }


    public List<User> findAll() {
        return userRepository.findAll().list();
    }
}
