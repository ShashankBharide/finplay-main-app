package com.finplay.mainapp.controller;


import com.finplay.mainapp.model.request.LoginRequest;
import com.finplay.mainapp.model.response.AuthResponse;
import com.finplay.mainapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    /*@Autowired
    private UserRepository userRepository;*/

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse response = new AuthResponse("You are authorised");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
