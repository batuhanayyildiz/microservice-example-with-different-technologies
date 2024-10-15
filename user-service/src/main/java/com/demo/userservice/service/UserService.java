package com.demo.userservice.service;

import com.demo.userservice.model.converter.UserCreateRequestConverter;
import com.demo.userservice.model.converter.UserDTOConverter;
import com.demo.userservice.model.converter.UserUpdateRequestConverter;
import com.demo.userservice.model.dto.UserCreateRequest;
import com.demo.userservice.model.dto.UserDTO;
import com.demo.userservice.model.dto.UserUpdateRequest;
import com.demo.userservice.model.entity.User;
import com.demo.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final UserCreateRequestConverter USER_CREATE_REQUEST_CONVERTER = Mappers.getMapper(UserCreateRequestConverter.class);

    private static final UserDTOConverter USER_DTO_CONVERTER= Mappers.getMapper(UserDTOConverter.class);




    @CacheEvict(value = {"users"},allEntries = true) // When create User is called, remove users cache.
    public User createUser(UserCreateRequest createRequest){

        return userRepository.save(USER_CREATE_REQUEST_CONVERTER.toEntity(createRequest));


    }

    @Cacheable(value="user_id",key = "#root.methodName+ #id ",unless="#result==null")
    public UserDTO getUserById(String id) throws Exception {
        User user=userRepository.findById(UUID.fromString(id)).orElseThrow(()->{
            log.error("User is not found by id "+id);
            return new Exception("User is not found by id "+id);});
        return USER_DTO_CONVERTER.toDTO(user);

    }
    @Cacheable(value="users",key = "#root.methodName",unless="#result==null")
    public List<UserDTO> getAllUsersByUsername(String username) throws Exception {
        List<User> users=userRepository.findAllByUsername(username).filter(a -> !a.isEmpty()).orElseThrow(()->{
            log.error("Users are not found by username "+username);
            return new Exception("Users are not found by username "+username);});

        return USER_DTO_CONVERTER.toDTO(users);

    }

    @CachePut(cacheNames = "user_id",key="'getUserById'+#request.id" ,unless="#result==null")
    public UserDTO updateUser(UserUpdateRequest request) {
        Optional<User> updateUser = userRepository.findById(request.getId());
        if(updateUser.isPresent()){
            User user1= updateUser.get();
            if(!request.getName().isEmpty()){ user1.setName(request.getName());}
            if(!request.getSurname().isEmpty()){ user1.setSurname(request.getSurname());}
            if(!request.getEmail().isEmpty()){ user1.setEmail(request.getEmail());}
            if(!request.getAddress().isEmpty()){ user1.setAddress(request.getAddress());}
            if(!request.getPhoneNumber().isEmpty()){ user1.setPhoneNumber(request.getPhoneNumber());}
            return USER_DTO_CONVERTER.toDTO(userRepository.save(user1));
        }
        else {
            return null;
        }
    }
}
