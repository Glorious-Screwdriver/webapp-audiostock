package com.audiostock.controller;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.exceptions.UserNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    UserService userService;

    public CartController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String cart(Principal principal, Model model) {
        User user = getUserFromPrincipal(principal);
        model.addAttribute("username", principal.getName());
        List<Track> cart = userService.getCartSortedByName(user);
        Long totalCost = userService.totalCartPrice(user);

        return "cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(Principal principal) {
        User user = getUserFromPrincipal(principal);

        long balance = user.getBalance();
        long totalCost = userService.totalCartPrice(user);

        //TODO /cart/checkout view
        throw new UnsupportedOperationException();
    }

    @PostMapping("/checkout")
    public String postCheckout(Principal principal) {
        User user = getUserFromPrincipal(principal);

        final boolean checkoutSucceeded = userService.checkout(user);

        if (checkoutSucceeded) {
            //TODO /purchased view
            throw new UnsupportedOperationException("/purchased view is not supported");
        } else {
            //TODO /deposit view
            throw new UnsupportedOperationException("/deposit view is not supported");
        }
    }

    private User getUserFromPrincipal(Principal principal) {
        User user;
        try {
            user = userService.getUserByUsername(principal.getName());
        } catch (UserNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return user;
    }

}
