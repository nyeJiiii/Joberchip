package kr.joberchip.auth.v1.user.service;

import kr.joberchip.auth.v1.errors.ErrorMessage;
import kr.joberchip.auth.v1.errors.exception.DuplicatedUsernameException;
import kr.joberchip.auth.v1.errors.exception.UserNotFoundException;
import kr.joberchip.auth.v1.user.dto.UserRequest;
import kr.joberchip.auth.v1.user.repository.UserRepository;
import kr.joberchip.auth.v1.security.JwtTokenProvider;
import kr.joberchip.core.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<User> userOptional = findUser(loginUser);
        User user = userOptional.get();
        return JwtTokenProvider.create(user);
    }

    private Optional<User> findUser(UserRequest loginUser) {
        Optional<User> userOptional = userRepository.findByUsername(loginUser.getUsername());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }
        return userOptional;
    }
}