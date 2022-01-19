package com.audiostock.auth;

import com.audiostock.entities.User;
import com.audiostock.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;

public class UserEntityDetailsService implements UserDetailsService {

    UserRepo userRepo;

    public UserEntityDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User candidate = userRepo.findByLogin(username).orElseThrow();
            return org.springframework.security.core.userdetails.User
                    .withUsername(candidate.getLogin())
                    .password(candidate.getPassword())
                    .roles(candidate.getStatus().getName())
                    .build();
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException(username, e);
        }
    }
}
