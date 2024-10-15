package com.demo.userservice.model.converter;


import com.demo.userservice.model.dto.UserCreateRequest;
import com.demo.userservice.model.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserCreateRequestConverter {

    User toEntity(UserCreateRequest request);
    UserCreateRequest toRequest(User user);



}
