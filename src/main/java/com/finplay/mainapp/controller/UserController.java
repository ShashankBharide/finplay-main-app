package com.finplay.mainapp.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/user/login")
    public Map<String, Object> loginUser() {
        return Map.of("User:","Logged in");
    }

    @GetMapping("/user/register")
    public Map<String, Object> registerUser() {
        return Map.of("User:","Registered in");
    }
}
