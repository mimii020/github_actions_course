package com.example.LunchMatch.controllers;

import com.example.LunchMatch.auth.AuthenticationRequest;
import com.example.LunchMatch.auth.AuthenticationResponse;

import com.example.LunchMatch.auth.RegistrationRequest;
import com.example.LunchMatch.auth.TokenBlackListService;
import com.example.LunchMatch.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    private TokenBlackListService tokenBlacklistService;
    private final AuthenticationManager authenticationManager;

    //register the user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) { //used to bind the incoming HTTP request body to a method parameter in your controller.
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    //authenticate the user
    //The type specified in the ResponseEntity<> is the type of the response body. This type parameter defines the data type of the body content that will be returned in the HTTP response
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authenticationService.logout(request);

        return ResponseEntity.ok().body("Logged out successfully");
    }



}
