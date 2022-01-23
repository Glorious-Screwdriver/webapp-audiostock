package com.audiostock.config;

import com.audiostock.auth.UserEntityDetailsService;
import com.audiostock.repos.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final private String CONSUMER = "CONSUMER";
    final private String AUTHOR = "CONSUMER"; // Пока не реализовано разделение
    final private String MODERATOR = "MODERATOR";

    private final UserRepo userRepo;

    public WebSecurityConfig(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Styles
                .antMatchers("/styles/cssandjs/*", "/img/*").permitAll()
                // Open data
                .antMatchers("/data/previews/*", "/data/avatars/*", "/data/covers/*").permitAll()
                // Prohibited data
                .antMatchers("/data/tracks/*").hasRole(MODERATOR)

                // UNAUTHORIZED
                .antMatchers("/", "/register", "/track/*", "/user/*", "/user/*/tracks").permitAll()

                // CONSUMERS
                .antMatchers("/favorite", "/cart", "/cart/checkout", "/track/*/*").hasRole(CONSUMER)
                .antMatchers( "/balance", "/purchased", "/purchased/*").hasRole(CONSUMER)

                // AUTHORS
                .antMatchers("/profile", "/profile/releases", "/profile/releases/*", "/profile/releases/*/*").hasRole(AUTHOR)

                // MODERATORS
                .antMatchers("/moderation", "/moderation/*").hasRole(MODERATOR)

                .anyRequest().permitAll()
            .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserEntityDetailsService(userRepo));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
