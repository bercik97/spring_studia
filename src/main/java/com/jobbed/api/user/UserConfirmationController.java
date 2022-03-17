package com.jobbed.api.user;

import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenUuid;
import com.jobbed.api.user.domain.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account-confirmation")
@RequiredArgsConstructor
class UserConfirmationController {

    private final UserFacade facade;

    @GetMapping
    public String view(Model model, @RequestParam(value = "token", required = false) String token) {
        boolean success = facade.confirmAccount(new ConfirmationTokenUuid(token));
        model.addAttribute("success", success);
        return "account_confirmation";
    }
}
