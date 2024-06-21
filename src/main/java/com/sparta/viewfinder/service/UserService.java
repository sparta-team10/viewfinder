package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.LoginRequestDto;
import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.dto.UserResponseDto;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.DuplicatedException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.UserErrorCode;
import com.sparta.viewfinder.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private Long id;


    public UserResponseDto createUser(UserRequestDto request) {
        String password = passwordEncoder.encode(request.getPassword());

        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new DuplicatedException(UserErrorCode.DUPLICATED_USER);
        }
        User user = new User(
                request.getUsername(),
                password,
                request.getName(),
                request.getEmail()
        );

        userRepository.save(user);

        return new UserResponseDto(user);
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
