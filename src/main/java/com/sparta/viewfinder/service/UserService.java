package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.LoginRequestDto;
import com.sparta.viewfinder.dto.UserRequestDto;

import com.sparta.viewfinder.dto.UserResponseDto;
import com.sparta.viewfinder.entity.Profile;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.DuplicatedException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.UserErrorCode;
import com.sparta.viewfinder.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private Long id;


    public UserResponseDto createUser(UserRequestDto request) {

        User saveUser = new User(request);
        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if(user.isPresent()){
            throw new DuplicatedException(UserErrorCode.DUPLICATED_USER);
        }
        userRepository.save(saveUser);

        return new UserResponseDto(saveUser);
    }

    public boolean login(LoginRequestDto requestDto) {
        User findUser = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new NotFoundException(UserErrorCode.USER_NOT_FOUND)
        );
        if (!findUser.getPassword().equals(requestDto.getPassword()))
            throw new NotFoundException(UserErrorCode.USER_NOT_FOUND);
        return true;
    }

    @Transactional
    public boolean logout(Long id) {
        User user = findById(id);
        return user.logout();
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()->new NotFoundException(UserErrorCode.USER_NOT_FOUND)
        );
    }
}
