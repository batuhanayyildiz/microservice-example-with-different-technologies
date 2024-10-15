package com.demo.authservice.service;


import com.demo.authservice.AuthServiceGrpc;
import com.demo.authservice.Token;
import com.demo.authservice.ValidationResponse;
import com.demo.authservice.model.entity.UserCredential;
import com.demo.authservice.repository.UserCredentialRepository;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
public class AuthGrpcServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {
    private static final Logger logger= LoggerFactory.getLogger(AuthGrpcServiceImpl.class);
    private final AuthService authService;



    @Override
    public void validateToken(Token request, StreamObserver<ValidationResponse> responseObserver){

        logger.info("Grpc call received: "+ request.getToken());
        boolean isValid=authService.validateToken(request.getToken());

        responseObserver.onNext(
                ValidationResponse.newBuilder()
                        .setIsValid(isValid)
                        .build());
        responseObserver.onCompleted();

    }

}
