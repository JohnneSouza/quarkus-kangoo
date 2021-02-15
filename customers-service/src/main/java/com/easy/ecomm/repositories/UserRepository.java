package com.easy.ecomm.repositories;

import com.easy.ecomm.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Optional<User> findUserByEmail(String email){
        return find("email", email).firstResultOptional();
    }

    public Optional<User> findById(long id){
        return findByIdOptional(id);
    }

    public User save(User user){
        persist(user);
        return user;
    }


    public Optional<User> findActivationKey(String key) {
        return find("activationKey", key).firstResultOptional();
    }
}
