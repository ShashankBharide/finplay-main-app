package com.finplay.mainapp.config;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@Getter
public class CognitoUserPoolConfig {
    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.region}")
    private String region;

    @Bean
    public AWSCognitoIdentityProvider cognitoIdentityProvider() {
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withRegion(region)
                .build();
    }
}