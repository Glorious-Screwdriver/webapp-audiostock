package com.audiostock.controller.consumer;

import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.service.TrackService;
import com.audiostock.service.UserService;
import com.audiostock.service.reports.CheckoutFailureReason;
import com.audiostock.service.reports.CheckoutReport;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
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
    public String cart(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Printing username in the header
        model.addAttribute("user", user);

        // Track map
        List<Track> cart = userService.getCartSortedByName(user);
        Map<Track, Boolean[]> map = Utils.getTrackMap(user, cart, trackService);
        model.addAttribute("tracks", map);

        // Total cost
        Long totalCost = userService.totalCartPrice(user);
        model.addAttribute("total", totalCost);

        return "cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        long balance = user.getBalance();
        long totalCost = userService.totalCartPrice(user);

        model.addAttribute("balance", balance);
        model.addAttribute("total", totalCost);
        model.addAttribute("nem", totalCost > balance);

        return "checkout";
    }

    @PostMapping("/checkout")
    public String checkout(Principal principal, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        final CheckoutReport report = userService.checkout(user);

        if (report.isSuccessful()) {
            return "redirect:/purchased";
        } else {
            model.addAttribute("message", report.getMessage());
            if (report.getCheckoutFailureReason() == CheckoutFailureReason.NOT_ENOUGH_MONEY) {
                return "forward:/balance";
            } else { // CheckoutFailureReason.TRACK_IS_ALREADY_PURCHASED
                return "forward:/checkout";
            }
        }
    }

}
