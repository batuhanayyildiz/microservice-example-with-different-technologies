package com.demo.userservice.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserUpdateRequest {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String phoneNumber;
}
