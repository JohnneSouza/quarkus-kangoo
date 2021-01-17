package com.easy.ecomm.repositories;

import com.easy.ecomm.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserDTO, Integer> {

    @Override
    Page<UserDTO> findAll(Pageable pageable);

    @Override
    <S extends UserDTO> S save(S s);

    @Override
    Optional<UserDTO> findById(Integer s);

    @Override
    boolean existsById(Integer s);

    @Override
    void deleteById(Integer s);

    Optional<UserDTO> findUserByEmail(String email);
}
