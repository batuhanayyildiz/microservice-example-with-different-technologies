package com.demo.gateway.controller;


import com.demo.gateway.service.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
       private final SecurityService securityService;

    public TestController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Boolean> validateTokenTest(@RequestParam String token){

        return ResponseEntity.ok(securityService.validateToken(token));

    }
}
