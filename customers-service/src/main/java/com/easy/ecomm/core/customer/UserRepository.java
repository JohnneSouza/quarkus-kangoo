package com.easy.ecomm.core.customer;

import com.easy.ecomm.core.customer.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<Customer> {

    public Optional<Customer> findUserByEmail(String email){
        return find("email", email).firstResultOptional();
    }

    public Optional<Customer> findById(long id){
        return findByIdOptional(id);
    }

    public Customer save(Customer customer){
        persist(customer);
        return customer;
    }


    public Optional<Customer> findActivationKey(String key) {
        return find("activationKey", key).firstResultOptional();
    }
}
