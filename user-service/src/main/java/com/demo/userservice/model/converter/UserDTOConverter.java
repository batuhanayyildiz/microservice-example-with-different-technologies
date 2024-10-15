package com.demo.userservice.model.converter;

import com.demo.userservice.model.dto.UserDTO;
import com.demo.userservice.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserDTOConverter {
    UserDTO toDTO(User entity);

    List<UserDTO> toDTO (List<User> users);

    User toEntity(UserDTO dto);



}
