package com.adziri.springweb.controllers;

import com.adziri.springweb.dto.JwtRequest;
import com.adziri.springweb.dto.RegistrationUserDTO;
import com.adziri.springweb.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO){
        return authService.createNewUser(registrationUserDTO);
    }
}
