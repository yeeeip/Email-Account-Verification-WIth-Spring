package com.nuzhd.springemailverification.controller;

import com.nuzhd.springemailverification.model.ConfirmationToken;
import com.nuzhd.springemailverification.model.RegistrationRequest;
import com.nuzhd.springemailverification.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
