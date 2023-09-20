package kr.joberchip.auth.v1.service;

import kr.joberchip.auth.v1.dto.UserRequest;
import kr.joberchip.auth.v1.repository.UserRepository;
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

        newUser.encodePassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser.toEntity());
    }

    @Transactional
    public User login(UserRequest loginUser) {
        Optional<User> userOptional = userRepository.findByUsername(loginUser.getUsername());
        if (userOptional.isEmpty()) {
            //TODO userOP.isEmpty 일 경우 Exception
            return null;
        }
        User user = userOptional.get();
        return user;
    }
}