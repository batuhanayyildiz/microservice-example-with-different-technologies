package com.demo.userservice.model.converter;


import com.demo.userservice.model.dto.UserUpdateRequest;
import com.demo.userservice.model.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserUpdateRequestConverter {

    User toEntity(UserUpdateRequest request);
}
