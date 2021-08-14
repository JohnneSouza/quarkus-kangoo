package dev.kangoo.core.customer;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    @Inject
    CustomerMapper customerMapper;

    public Optional<Customer> findUserByEmail(String email){
        return find("email", email).firstResultOptional();
    }

    public Optional<Customer> findById(long id){
        return findByIdOptional(id);
    }

    public CustomerDto save(Customer customer){
        persist(customer);
        return customerMapper.customerToDto(customer);
    }


    public Optional<Customer> findCustomerByActivationKey(String key) {
        return find("activationKey", key).firstResultOptional();
    }
}
