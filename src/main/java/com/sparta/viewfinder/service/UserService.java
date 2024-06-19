package com.sparta.viewfinder.service;


import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.UserErrorCode;
import org.springframework.stereotype.Service;
import com.sparta.viewfinder.repository.UserRepository;
import com.sparta.viewfinder.dto.LoginRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private Long id;

    public User createUser(UserRequestDto request) {
        User saveUser = new User(request);
        userRepository.save(saveUser);
        return saveUser;
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
