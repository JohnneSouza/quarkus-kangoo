package com.easy.ecomm.service;

import com.easy.ecomm.exceptions.EmailTakenException;
import com.easy.ecomm.exceptions.InvalidActivationKeyException;
import com.easy.ecomm.exceptions.UserAccountAlreadyActiveException;
import com.easy.ecomm.model.Customer;
import com.easy.ecomm.model.dto.CustomerDto;
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

    public Customer createUser(CustomerDto customerDto, String password){
        String activationKey = UUID.randomUUID().toString();
        validateEmail(customerDto.getEmail());
        emailService.sendActivationEmail(customerDto.getEmail(), activationKey);
        return userRepository.save(
                Customer.builder()
                .password(BcryptUtil.bcryptHash(password))
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .createdAt(LocalDate.now())
                .activationKey(activationKey)
                .active(false)
                .build());
    }

    public Customer updateUser(CustomerDto newUser, long id, String password) {
        Customer currentCustomer = findUserById(id);
        currentCustomer.setLastName(newUser.getLastName());
        currentCustomer.setFirstName(newUser.getFirstName());
        userRepository.persist(currentCustomer);
        return currentCustomer;
    }

    public Customer findUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Customer finderByEmail(String email){
        return userRepository.findUserByEmail(email)
                .orElseThrow(NotFoundException::new);
    }

    public List<Customer> findAll() {
        return userRepository.findAll().list();
    }

    public void activateUser(String key) {
        Customer customer = userRepository.findActivationKey(key)
                .orElseThrow(() -> new InvalidActivationKeyException("Invalid activation key"));
        if (!customer.isActive()){
            customer.setActive(true);
            customer.setActivationKey(null);
            userRepository.persist(customer);
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
