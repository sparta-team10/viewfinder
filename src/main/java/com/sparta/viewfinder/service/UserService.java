package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.LoginRequestDto;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private Long id;

    public boolean login(LoginRequestDto requestDto){
        User findUser = userRepository.findByUsername(requestDto.getUsername()).get();
        if(findUser == null || !findUser.getPassword().equals(requestDto.getPassword())){
            return false;
        }
        return true;
    }

    @Transactional
    public boolean logout(Long id){
        this.id = id;
        User user = userRepository.findById(id).get();
        return user.logout();
    }
}
