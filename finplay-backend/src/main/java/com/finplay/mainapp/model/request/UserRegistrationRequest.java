package com.finplay.mainapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegistrationRequest {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
}
