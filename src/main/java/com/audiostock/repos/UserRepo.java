package com.audiostock.repos;

import com.audiostock.entities.Status;
import com.audiostock.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByLogin(String login);

    List<User> findAllByStatusName(String status);
}