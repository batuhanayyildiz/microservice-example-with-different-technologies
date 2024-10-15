package com.demo.userservice.controller;

import com.demo.userservice.model.dto.UserCreateRequest;
import com.demo.userservice.model.dto.UserDTO;
import com.demo.userservice.model.dto.UserUpdateRequest;
import com.demo.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam String username) throws Exception {
        return ResponseEntity.ok(userService.getAllUsersByUsername(username));
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(userService.updateUser(request));
    }

}
