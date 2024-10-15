package com.demo.userservice.model.converter;

import com.demo.userservice.model.dto.UserCreateRequest;
import com.demo.userservice.model.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-13T11:31:08+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class UserCreateRequestConverterImpl implements UserCreateRequestConverter {

    @Override
    public User toEntity(UserCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( request.getUsername() );
        user.setEmail( request.getEmail() );

        return user;
    }

    @Override
    public UserCreateRequest toRequest(User user) {
        if ( user == null ) {
            return null;
        }

        UserCreateRequest userCreateRequest = new UserCreateRequest();

        userCreateRequest.setUsername( user.getUsername() );
        userCreateRequest.setEmail( user.getEmail() );

        return userCreateRequest;
    }
}
