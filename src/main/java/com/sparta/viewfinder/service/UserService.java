package com.sparta.viewfinder.service;


import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.entity.User;
import org.springframework.stereotype.Service;
import com.sparta.viewfinder.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDto request) {
        User saveUser = new User(request);
        userRepository.save(saveUser);
        return saveUser;
    }


}
