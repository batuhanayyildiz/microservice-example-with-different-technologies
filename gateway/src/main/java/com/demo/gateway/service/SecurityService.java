package com.demo.gateway.service;

import com.demo.authservice.AuthServiceGrpc;
import com.demo.authservice.Token;
import com.demo.authservice.ValidationResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {


    @GrpcClient("auth-service")
    private AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    public Boolean validateToken(String token){
        ValidationResponse validationResponse=authServiceBlockingStub.validateToken(Token.newBuilder()
                .setToken(token)
                .build());
        return validationResponse.getIsValid();

    }
}
