package com.audiostock.config;

import com.audiostock.entities.UserEntity;
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

    private final DataSource dataSource;
    private final UserRepo userRepo;

    public WebSecurityConfig(final DataSource dataSource, final UserRepo userRepo) {
        this.dataSource = dataSource;
        this.userRepo = userRepo;
    }

    @Override
    //ToDo
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/register").permitAll()
//                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .successForwardUrl("/home")
                .permitAll()
            .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            try {
                System.out.println(username);
                UserEntity candidate = userRepo.findByLogin(username).orElseThrow();
                return org.springframework.security.core.userdetails.User
                        .withUsername(candidate.getLogin())
                        .password(candidate.getPassword())
                        .roles("USER")
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new UsernameNotFoundException(username);
        });
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
