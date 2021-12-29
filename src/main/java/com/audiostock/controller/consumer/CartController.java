package com.audiostock.controller.consumer;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    UserService userService;
    TrackService trackService;

    public CartController(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @GetMapping
    public String cart(Principal principal, Model model){
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Printing username in the header
        model.addAttribute("username", user.getLogin());

        // Track map
        Map<Track, Boolean[]> map = new LinkedHashMap<>();

        List<Track> cart = userService.getCartSortedByName(user);
        for (Track track : cart) {
            map.put(track, new Boolean[]{
                    trackService.isInFavorite(track, user),
                    trackService.isInCart(track, user)
            });
        }
        model.addAttribute("tracks", map);
        Long totalCost = userService.totalCartPrice(user);
        model.addAttribute("total", totalCost);
        return "cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(Principal principal){
        User user = Utils.getUserFromPrincipal(principal, userService);

        long balance = user.getBalance();
        long totalCost = userService.totalCartPrice(user);

        //TODO /cart/checkout view
        throw new UnsupportedOperationException();
    }

    @PostMapping("/checkout")
    public String postCheckout(Principal principal) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        final boolean checkoutSucceeded = userService.checkout(user);

        if (checkoutSucceeded) {
            //TODO /purchased view
            throw new UnsupportedOperationException("/purchased view is not supported");
        } else {
            //TODO /deposit view
            throw new UnsupportedOperationException("/deposit view is not supported");
        }
    }

}
