package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.LoginRequestDto;
import com.sparta.viewfinder.dto.UserRequestDto;
import com.sparta.viewfinder.dto.UserResponseDto;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.entity.UserStatusEnum;
import com.sparta.viewfinder.exception.DuplicatedException;
import com.sparta.viewfinder.exception.MismatchException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.UserErrorCode;
import com.sparta.viewfinder.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private Long id;


  public UserResponseDto signUp(UserRequestDto requestDto) {
    String password = passwordEncoder.encode(requestDto.getPassword());

    if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
      throw new DuplicatedException(UserErrorCode.DUPLICATED_USER);
    }
    User user = new User(
        requestDto.getUsername(),
        password,
        requestDto.getName(),
        requestDto.getEmail()
    );
    userRepository.save(user);
    return new UserResponseDto(user);
  }

  // 회원 탈퇴
  @Transactional
  public void withDraw(Long id, String password) {
    User user = findById(id);
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new MismatchException(UserErrorCode.PW_MISMATCH);
    }
    if (user.getStatus().equals(UserStatusEnum.NON_USER)) {
      throw new NotFoundException(UserErrorCode.WITHDRAW_USER);
    }
    user.withDraw();
  }


  public boolean login(LoginRequestDto requestDto) {
    User findUser = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
        () -> new NotFoundException(UserErrorCode.USER_NOT_FOUND)
    );
    if (!findUser.getPassword().equals(requestDto.getPassword())) {
      throw new NotFoundException(UserErrorCode.USER_NOT_FOUND);
    }
    return true;
  }

  @Transactional
  public boolean logout(Long id) {
    User user = findById(id);
    return user.logout();
  }

  private User findById(Long id) {
    return userRepository.findById(id).orElseThrow(
        () -> new NotFoundException(UserErrorCode.USER_NOT_FOUND)
    );
  }

  public void refreshTokenCheck(String username, String refreshToken) {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new NotFoundException(UserErrorCode.USER_NOT_FOUND)
    );
    if (!user.getRefreshToken().equals(refreshToken)) {
      throw new MismatchException(UserErrorCode.REFRESH_TOKEN_MISMATCH);
    }
  }
}
