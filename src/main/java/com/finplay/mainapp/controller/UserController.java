package com.finplay.mainapp.controller;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.finplay.mainapp.entity.User;
import com.finplay.mainapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/finplay/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientSecret}")
    private String clientSecret;


    @Value("${aws.cognito.region}")
    private String region;


    private final AWSCognitoIdentityProvider cognitoIdentityProvider;

    public UserController(AWSCognitoIdentityProvider cognitoIdentityProvider) {
        this.cognitoIdentityProvider = cognitoIdentityProvider;
    }

    @PostMapping("/register")
    @Operation(summary = "Register User", description = "Please send the user request to register the user and accept the token")
    public ResponseEntity<String> register(@RequestBody UserRequest request) {
        try {
            // Create the user with a temporary password
            AdminCreateUserRequest userRequest = new AdminCreateUserRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(request.getEmail())
                    .withUserAttributes(
                            new AttributeType().withName("email").withValue(request.getEmail())
                    )
                    .withTemporaryPassword(request.getPassword());

            cognitoIdentityProvider.adminCreateUser(userRequest);

            AdminSetUserPasswordRequest setPasswordRequest = new AdminSetUserPasswordRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(request.getEmail())
                    .withPassword(request.getPassword())
                    .withPermanent(true);

            cognitoIdentityProvider.adminSetUserPassword(setPasswordRequest);

            User user = new User();
            user.setUsername(request.getEmail());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword()); // Ensure to store hashed passwords if necessary.
            userService.createUser(user);

            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering user: " + e.getMessage());
        }
    }



    @PostMapping("/login")
    @Operation(summary = "Login User", description = "Authenticate the user and return tokens")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String secretHash = calculateSecretHash(
                    loginRequest.getEmail(),
                    clientId,
                    clientSecret
            );

            Map<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", loginRequest.getEmail());
            authParams.put("PASSWORD", loginRequest.getPassword());
            authParams.put("SECRET_HASH", secretHash);

            AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                    .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .withClientId(clientId)
                    .withUserPoolId(userPoolId)
                    .withAuthParameters(authParams);

            AdminInitiateAuthResult authResult = cognitoIdentityProvider.adminInitiateAuth(authRequest);

            String accessToken = authResult.getAuthenticationResult().getAccessToken();
            String idToken = authResult.getAuthenticationResult().getIdToken();

            AuthResponse response = new AuthResponse(accessToken, idToken);
            return ResponseEntity.ok(response);

        } catch (AWSCognitoIdentityProviderException e) {
            // Handle specific Cognito exceptions here
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @Data
    public static class UserRequest {
        private String email;
        private String password;
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class AuthResponse {
        private String accessToken;
        private String idToken;
    }

    public static String calculateSecretHash(String userName, String clientId, String clientSecret) throws Exception {
        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
        SecretKeySpec signingKey = new SecretKeySpec(
                clientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        mac.update(userName.getBytes(StandardCharsets.UTF_8));
        byte[] rawHmac = mac.doFinal(clientId.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(rawHmac);
    }

}
