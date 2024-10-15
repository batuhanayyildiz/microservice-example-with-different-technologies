package com.demo.authservice.controller;


import com.demo.authservice.model.dto.AuthRequest;
import com.demo.authservice.model.dto.RegisterRequest;
import com.demo.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;


    @PostMapping
    @RequestMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token){

        return ResponseEntity.ok(authService.validateToken(token));
    }
    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<String> login( @RequestBody  AuthRequest authRequest) throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if(!authentication.isAuthenticated()){
            throw new Exception("invalid.access");
        }
        return ResponseEntity.ok(authService.generateToken(authRequest.username()));
    }


    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<String> createUserCredential(@RequestBody RegisterRequest registerRequest) throws Exception {

        return ResponseEntity.ok(authService.createUserCredential(registerRequest));


    }





}
