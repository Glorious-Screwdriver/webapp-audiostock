package com.audiostock.controller.consumer;

import com.audiostock.entities.PaymentInfo;
import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/balance")
public class BalanceController {

    UserService userService;

    private final Map<Principal, String> referers;

    public BalanceController(UserService userService) {
        this.userService = userService;
        referers = new HashMap<>();
    }

    @GetMapping
    public String prepareDeposit(Principal principal, Model model, @RequestHeader String referer) {
        if (referers.containsKey(principal)) {
            referers.replace(principal, referer);
        } else {
            referers.put(principal, referer);
        }

        User user = Utils.getUserFromPrincipal(principal, userService);

        final PaymentInfo paymentInfo = user.getPaymentInfo();
        if (paymentInfo != null) {
            model.addAttribute("cardOwner", paymentInfo.getCardOwner());
            model.addAttribute("cardNumber", paymentInfo.getCardNumber());
            model.addAttribute("expireDate", paymentInfo.getExpireDate());
            model.addAttribute("cvv", paymentInfo.getCvv());
            model.addAttribute("address", paymentInfo.getAddress());
            model.addAttribute("postalCode", paymentInfo.getPostalCode());
        }

        //TODO prepareDeposit view
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public String makeDeposit(
            Principal principal,
            @RequestParam Long amount,
            @RequestParam String cardOwner,
            @RequestParam int cardNumber,
            @RequestParam String expireDate,
            @RequestParam int cvv,
            @RequestParam int postalCode,
            @RequestParam String address) {
        User user = Utils.getUserFromPrincipal(principal, userService);
        PaymentInfo paymentInfo = new PaymentInfo(cardOwner, cardNumber, expireDate, cvv, postalCode, address);

        userService.savePaymentMethod(user, paymentInfo);
        userService.makeDeposit(user, amount);

        String referer = referers.get(principal);
        referers.remove(principal);
        return "redirect:" + referer;
    }

}
