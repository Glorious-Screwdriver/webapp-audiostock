package com.audiostock.auth;

import com.audiostock.entities.User;
import com.audiostock.service.LogonService;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    UserService userService;
    LogonService logonService;

    public LoginSuccessListener(UserService userService, LogonService logonService) {
        this.userService = userService;
        this.logonService = logonService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        final Authentication authentication = event.getAuthentication();

        User user;
        try {
            user = userService.getUserByUsername(event.getAuthentication().getName());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        final WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        final String remoteAddress = details.getRemoteAddress();

        if (remoteAddress.contains(":")) {
            logonService.logon(user, remoteAddress);
        } else {
            final String[] split = remoteAddress.split("\\.");

            Byte[] ip = new Byte[split.length];
            for (int i = 0; i < split.length; i++) {
                ip[i] = (byte) Integer.parseInt(split[i]);
            }

            logonService.logon(user, ip);
        }
    }

}