package com.finplay.mainapp.model.request;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginRequest {
    private String username;
    private String password;
}
