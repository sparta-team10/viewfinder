package com.sparta.viewfinder.service;


import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.entity.Profile;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import com.sparta.viewfinder.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public User createUser(UserRequestDto request) {
        User saveUser = new User(request);
        userRepository.save(saveUser);
//        Profile profile = new Profile();
//        profile.setUserId(saveUser.getId());
//        profileRepository.save(profile);
        return saveUser;
    }


}
