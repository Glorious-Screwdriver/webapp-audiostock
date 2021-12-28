package com.audiostock.service.util;

import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;
import com.audiostock.service.exceptions.UserNotLoggedInException;

import java.security.Principal;

public class Utils {

    public static User getUserFromPrincipal(Principal principal, UserService userService)
            throws UserNotLoggedInException {
        if (principal == null) throw new UserNotLoggedInException();

        User user;
        try {
            user = userService.getUserByUsername(principal.getName());
        } catch (UserNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return user;
    }

    public static User getUserFromPrincipalNoException(Principal principal, UserService userService) {
        User user;
        try {
            user = userService.getUserByUsername(principal.getName());
        } catch (UserNotFoundException e) {
            return null;
        }
        return user;
    }

}
