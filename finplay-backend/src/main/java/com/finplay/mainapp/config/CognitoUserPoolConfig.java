package com.finplay.mainapp.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
/*@Builder
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@Getter*/
public class CognitoUserPoolConfig {
/*
    @Value("${cognito.userPoolId}")
    private String userPoolId;

    @Value("${cognito.clientId}")
    private String clientId;

    @Value("${cognito.clientSecret}")
    private String clientSecret;

    @Value("${cognito.region}")
    private String region;

    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider() {
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("<your-access-key>", "<your-secret-key>")))
                .withRegion(Regions.fromName(region))
                .build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration cognitoRegistration = ClientRegistration.withRegistrationId("cognito")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope("openid", "profile", "email")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://localhost:8001/login/oauth2/code/cognito")
                .authorizationUri("https://finplayapp.auth.us-east-1.amazoncognito.com/oauth2/authorize")
                .tokenUri("https://finplayapp.auth.us-east-1.amazoncognito.com/oauth2/token")
                .userInfoUri("https://finplayapp.auth.us-east-1.amazoncognito.com/oauth2/userInfo")
                .clientName("Cognito")
                .build();
        return new InMemoryClientRegistrationRepository(cognitoRegistration);
    }*/
}