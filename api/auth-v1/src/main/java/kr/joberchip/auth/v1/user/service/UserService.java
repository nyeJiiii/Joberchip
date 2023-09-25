package kr.joberchip.auth.v1.user.service;

import kr.joberchip.auth.v1.errors.ErrorMessage;
import kr.joberchip.auth.v1.errors.exception.DuplicatedUsernameException;
import kr.joberchip.auth.v1.errors.exception.UserNotFoundException;
import kr.joberchip.auth.v1.user.dto.UserRequest;
import kr.joberchip.auth.v1.user.repository.UserRepository;
import kr.joberchip.auth.v1.security.JwtTokenProvider;
import kr.joberchip.core.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        return JwtTokenProvider.create(user);
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
        if(!passwordEncoder.matches(loginUser.getPassword(), userOptional.get().getPassword())) {
            log.info("비밀번호가 일치하지 않음");
            throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        } else {
            return userOptional;
        }
    }
}