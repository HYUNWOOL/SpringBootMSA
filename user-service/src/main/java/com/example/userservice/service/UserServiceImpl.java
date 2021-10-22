package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
    System.out.println(user.toString());
        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), user.getEncryptedPwd(),
            true, true, true, true, new ArrayList<>());
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
    System.out.println(userDto.toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = mapper.map(userDto, User.class);
        user.setEncryptedPwd(encoder.encode(userDto.getPassword()));

        userRepository.save(user);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        if(!user.isPresent()){
            throw new UsernameNotFoundException("User Not Found");
        }
       UserDto userDto = new ModelMapper().map(user.get(), UserDto.class);

        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public Iterable<User> getUserByAll() {
        return userRepository.findAll();
    }
}
