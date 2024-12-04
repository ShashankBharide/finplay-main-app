package com.finplay.mainapp.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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

    @Value("${aws.iam.access-key-id}")
    private String accessKeyId;

    @Value("${aws.iam.secret-access-key}")
    private String secretAccessKey;

    @Value("${aws.cognito.region}")
    private String region;

    @Bean
    public AWSCognitoIdentityProvider cognitoIdentityProvider() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}