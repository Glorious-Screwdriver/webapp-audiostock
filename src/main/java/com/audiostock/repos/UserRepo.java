package com.audiostock.repos;

import com.audiostock.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Page<User> findAllByLogin(String login, Pageable pageable);
}