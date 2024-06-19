package com.sparta.viewfinder.service;


import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.dto.UserResponseDto;
import com.sparta.viewfinder.entity.Profile;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import com.sparta.viewfinder.repository.UserRepository;
import com.sparta.viewfinder.dto.LoginRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private Long id;

    public UserResponseDto createUser(UserRequestDto request) {
        User saveUser = new User(request);
        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if( user.isPresent() ){
            throw new RuntimeException("사용중인 이름입니다");
        }
        userRepository.save(saveUser);


        return new UserResponseDto(saveUser);
    }

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
