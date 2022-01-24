package com.audiostock.controller.consumer;

import com.audiostock.entities.PaymentInfo;
import com.audiostock.entities.User;
import com.audiostock.service.UserService;
import com.audiostock.service.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
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

        model.addAttribute("total", Math.max(0, userService.totalCartPrice(user) - user.getBalance()));
        model.addAttribute("paymentInfo", user.getPaymentInfo());

        return "balance";
    }


    @PostMapping
    public String makeDeposit(Principal principal, @RequestParam Map<String, String> params, Model model) {
        User user = Utils.getUserFromPrincipal(principal, userService);

        // Проверяем на пустые поля
        for (String value : params.values()) {
            if (value == null) {
                model.addAttribute("message", "Необходимо заполнить все поля!");
                model.addAttribute("paymentInfo", user.getPaymentInfo());
                return "balance";
            }
        }


        // Тут была бы проверка платежных данных...


        // Сохраняем новую платежную информацию
        final String cardOwner = params.get("cardOwner");
        PaymentInfo paymentInfo = new PaymentInfo(
                cardOwner,
                params.get("cardNumber"),
                LocalDate.of(
                        Integer.parseInt(params.get("year")),
                        Integer.parseInt(params.get("month")),
                        1
                ),
                params.get("cvv"),
                params.get("postalCode"),
                params.get("address")
        );
        userService.savePaymentMethod(user, paymentInfo);

        // Добавляем деньги пользователю
        userService.makeDeposit(user, Long.parseLong(params.get("amount")));

        // Возвращаем на страницу, с которой пришел пользователь
        String referer = referers.get(principal);
        referers.remove(principal);
        return "redirect:" + referer;
    }

}
