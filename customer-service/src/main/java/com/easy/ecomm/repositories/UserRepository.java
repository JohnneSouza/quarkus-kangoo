package com.easy.ecomm.repositories;

import com.easy.ecomm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    @Override
    Page<User> findAll(Pageable pageable);

    @Override
    <S extends User> S save(S s);

    @Override
    Optional<User> findById(Integer s);

    @Override
    boolean existsById(Integer s);

    @Override
    void deleteById(Integer s);

    Optional<User> findUserByEmail(String email);
}
