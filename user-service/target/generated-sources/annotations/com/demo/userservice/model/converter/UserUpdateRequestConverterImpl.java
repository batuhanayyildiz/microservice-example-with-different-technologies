package com.demo.userservice.model.converter;

import com.demo.userservice.model.dto.UserUpdateRequest;
import com.demo.userservice.model.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-23T16:49:02+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class UserUpdateRequestConverterImpl implements UserUpdateRequestConverter {

    @Override
    public User toEntity(UserUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setId( request.getId() );
        user.setEmail( request.getEmail() );
        user.setName( request.getName() );
        user.setSurname( request.getSurname() );
        user.setAddress( request.getAddress() );
        user.setPhoneNumber( request.getPhoneNumber() );

        return user;
    }
}
