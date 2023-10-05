package kr.joberchip.server.v1.user.service;

import java.util.Optional;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._config.security.JwtProvider;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.DuplicatedUsernameException;
import kr.joberchip.server.v1._errors.exceptions.UserNotFoundException;
import kr.joberchip.server.v1.storage.service.S3StorageService;
import kr.joberchip.server.v1.user.controller.dto.UpdateUserResponseDTO;
import kr.joberchip.server.v1.user.controller.dto.UpdateUserRequestDTO;
import kr.joberchip.server.v1.user.controller.dto.UserRequest;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final S3StorageService s3StorageService;

  @Transactional
  public void join(UserRequest newUser) {
    checkUsername(newUser);
    newUser.encodePassword(passwordEncoder.encode(newUser.getPassword()));
    userRepository.save(newUser.toEntity());
  }

  private void checkUsername(UserRequest newUser) {
    Optional<User> isUser = userRepository.findByUsername(newUser.getUsername());
    if (isUser.isPresent()) {
      throw new DuplicatedUsernameException(ErrorMessage.DUPLICATED_USERNAME);
    }
  }

  @Transactional
  public String login(UserRequest loginUser) {
    Optional<User> userOptional = checkUser(loginUser);
    User user = userOptional.get();
    return JwtProvider.create(user);
  }

  private Optional<User> checkUser(UserRequest loginUser) {
    Optional<User> userOptional = userRepository.findByUsername(loginUser.getUsername());
    if (userOptional.isEmpty()) {
      log.info("ID를 찾을 수 없음");
      throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
    }

    return checkPassword(loginUser, userOptional);
  }

  private Optional<User> checkPassword(UserRequest loginUser, Optional<User> userOptional) {
    if (!passwordEncoder.matches(loginUser.getPassword(), userOptional.get().getPassword())) {
      log.info("비밀번호가 일치하지 않음");
      throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
    } else {
      return userOptional;
    }
  }

  @Transactional
  public UpdateUserResponseDTO updateUserInfo(CustomUserDetails loginUser, UpdateUserRequestDTO updateUserRequestDTO) {
    User user = userRepository.findByUsername(loginUser.getUsername())
            .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_ENTITY_NOT_FOUND));

    if(updateUserRequestDTO.nickname() != null) user.setNickname(updateUserRequestDTO.nickname());
    if(updateUserRequestDTO.profileImageLink() != null) {
      s3StorageService.delete(user.getProfileImageLink());
      user.setProfileImageLink(s3StorageService.store(updateUserRequestDTO.profileImageLink()));
    }

    userRepository.save(user);
    return UpdateUserResponseDTO.fromEntity(user);
  }
}
