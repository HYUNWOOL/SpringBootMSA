package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser (UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<User> getUserByAll();
}
