package com.demo.authservice.model.dto;

import java.util.Date;

public record TokenValidationResponseDTO(boolean isValid, Date expirationDate) {
}
