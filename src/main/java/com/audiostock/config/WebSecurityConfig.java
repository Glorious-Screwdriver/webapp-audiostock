package com.audiostock.config;

import com.audiostock.entities.User;
import com.audiostock.repos.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;
    private UserRepo userRepo;

    public WebSecurityConfig(final DataSource dataSource, final UserRepo userRepo) {
        this.dataSource = dataSource;
        this.userRepo = userRepo;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO configure HttpSecurity
        super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            try {
                User candidate = userRepo.findByLogin(username).orElseThrow();

                return org.springframework.security.core.userdetails.User.withUsername(candidate.getLogin())
                        .password(candidate.getPassword())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new UsernameNotFoundException(username);
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
