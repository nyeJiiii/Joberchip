package kr.joberchip.auth.v1.user.service;

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
        //TODO username 중복검사, 중복일 경우 Exception
        Optional<User> isUser = userRepository.findByUsername(newUser.getUsername());
        if (isUser.isPresent()) {

        }

        newUser.encodePassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser.toEntity());
    }

    @Transactional
    public String login(UserRequest loginUser) {
        Optional<User> userOptional = userRepository.findByUsername(loginUser.getUsername());
        if (userOptional.isEmpty()) {
            //TODO userOP.isEmpty 일 경우 Exception
            return null;
        }
        User user = userOptional.get();
        return JwtTokenProvider.create(user);
    }
}