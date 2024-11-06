package com.finplay.mainapp.controller;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.finplay.mainapp.config.CognitoUserPoolConfig;
import com.finplay.mainapp.model.request.UserLoginRequest;
import com.finplay.mainapp.model.request.UserRegistrationRequest;
import com.finplay.mainapp.model.response.UserEntity;
import com.finplay.mainapp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AWSCognitoIdentityProvider cognitoIdentityProvider;

    @Autowired
    private CognitoUserPoolConfig cognitoUserPoolConfig;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        // Register user in Cognito
        AdminCreateUserRequest createUserRequest = new AdminCreateUserRequest()
                .withUserPoolId("us-east-1_oWCG4e8UI")
                .withUsername(request.getUsername())
                .withUserAttributes(
                        new AttributeType().withName("email").withValue(request.getEmail()),
                        new AttributeType().withName("phone_number").withValue(request.getPhoneNumber())
                )
                .withTemporaryPassword(request.getPassword())
                .withMessageAction("SUPPRESS");

        cognitoIdentityProvider.adminCreateUser(createUserRequest);

        // Save user in the database
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setEmail(request.getEmail());
        userEntity.setPhoneNumber(request.getPhoneNumber());
        userEntity.setRole(request.getRole());
        userRepository.save(userEntity);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest request) {
        AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withClientId(cognitoUserPoolConfig.getClientId())
                .withUserPoolId(cognitoUserPoolConfig.getUserPoolId())
                .withAuthParameters(Map.of(
                        "USERNAME", request.getUsername(),
                        "PASSWORD", request.getPassword()
                ));

        AdminInitiateAuthResult authResult = cognitoIdentityProvider.adminInitiateAuth(authRequest);
        String idToken = authResult.getAuthenticationResult().getIdToken();
        return ResponseEntity.ok(idToken);
    }
}
