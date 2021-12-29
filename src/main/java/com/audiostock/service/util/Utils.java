package com.audiostock.service.util;

import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;

import java.security.Principal;

public class Utils {

    public static User getUserFromPrincipal(Principal principal, UserService userService) {
        User user = null;
        if (principal != null) {
            try {
                user = userService.getUserByUsername(principal.getName());
            } catch (UserNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        return user;
    }

}
