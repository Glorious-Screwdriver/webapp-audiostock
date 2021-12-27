package com.audiostock.repos;

import com.audiostock.entities.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}