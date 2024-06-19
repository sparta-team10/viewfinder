package com.sparta.viewfinder.service;


import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.entity.Profile;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import com.sparta.viewfinder.repository.UserRepository;
import com.sparta.viewfinder.dto.LoginRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private Long id;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public User createUser(UserRequestDto request) {
        User saveUser = new User(request);
        userRepository.save(saveUser);
        return saveUser;
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
