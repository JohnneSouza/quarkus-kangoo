package com.easy.ecomm.service;

import com.easy.ecomm.exceptions.EmailTakenException;
import com.easy.ecomm.exceptions.InvalidActivationKeyException;
import com.easy.ecomm.exceptions.UserAccountAlreadyActiveException;
import com.easy.ecomm.model.User;
import com.easy.ecomm.model.dto.UserDto;
import com.easy.ecomm.repositories.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    @Inject
    EmailService emailService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDto userDto, String password){
        String activationKey = UUID.randomUUID().toString();
        validateEmail(userDto.getEmail());
        emailService.sendActivationEmail(userDto.getEmail(), activationKey);
        return userRepository.save(
                User.builder()
                .password(BcryptUtil.bcryptHash(password))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .createdAt(LocalDate.now())
                .activationKey(activationKey)
                .active(false)
                .build());
    }

    public User updateUser(UserDto newUser, long id, String password) {
        User currentUser = findUserById(id);
        currentUser.setLastName(newUser.getLastName());
        currentUser.setFirstName(newUser.getFirstName());
        userRepository.persist(currentUser);
        return currentUser;
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

    public void activateUser(String key) {
        User user = userRepository.findActivationKey(key)
                .orElseThrow(() -> new InvalidActivationKeyException("Invalid activation key"));
        if (!user.isActive()){
            user.setActive(true);
            user.setActivationKey(null);
            userRepository.persist(user);
        } else {
            throw new UserAccountAlreadyActiveException("Account is already active");
        }
    }

    private void validateEmail(String email) {
        if(userRepository.findUserByEmail(email).isPresent()){
            throw new EmailTakenException("This email is already registered");
        }
    }
}
