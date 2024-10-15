package com.demo.userservice.model.dto;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String email;

}
