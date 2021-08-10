package dev.kangoo.core.customer;

import dev.kangoo.core.AccountAlreadyActiveException;
import dev.kangoo.core.InvalidActivationKeyException;
import dev.kangoo.core.email.EmailTakenException;
import dev.kangoo.core.email.EmailService;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    EmailService emailService;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createUser(CustomerDto customerDto, String password){
        String activationKey = UUID.randomUUID().toString();
        validateEmail(customerDto.getEmail());
        emailService.sendActivationEmail(customerDto.getEmail(), activationKey);
        return customerRepository.save(
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
        customerRepository.persist(currentCustomer);
        return currentCustomer;
    }

    public Customer findUserById(long id){
        return customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Customer finderByEmail(String email){
        return customerRepository.findUserByEmail(email)
                .orElseThrow(NotFoundException::new);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll().list();
    }

    public void activateUser(String key) {
        Customer customer = customerRepository.findActivationKey(key)
                .orElseThrow(() -> new InvalidActivationKeyException("Invalid activation key"));
        if (!customer.isActive()){
            customer.setActive(true);
            customer.setActivationKey(null);
            customerRepository.persist(customer);
        } else {
            throw new AccountAlreadyActiveException("Account is already active");
        }
    }

    private void validateEmail(String email) {
        if(customerRepository.findUserByEmail(email).isPresent()){
            throw new EmailTakenException("This email is already registered");
        }
    }

    public boolean deleteUserById(long id) {
        return this.customerRepository.deleteById(id);
    }
}
