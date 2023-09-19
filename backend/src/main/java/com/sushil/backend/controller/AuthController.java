package com.sushil.backend.controller;

import com.sushil.backend.payload.LoginDto;
import com.sushil.backend.payload.RegisterDto;
import com.sushil.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping(value = {"login", "signin"})
    public ResponseEntity<String> login(@RequestBody  LoginDto loginDt){
        String ans = authService.loginDto(loginDt);
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    @PostMapping(value = {"register", "signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDt){
        String ans = authService.registerDto(registerDt);
        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }


}
