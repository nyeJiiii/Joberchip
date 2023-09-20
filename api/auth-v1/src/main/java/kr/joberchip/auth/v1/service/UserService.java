package kr.joberchip.auth.v1.service;

import kr.joberchip.auth.v1.dto.UserRequest;
import kr.joberchip.auth.v1.repository.UserRepository;
import kr.joberchip.core.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User join(UserRequest newUser) {

        newUser.encodePassword(passwordEncoder.encode(newUser.getPassword()));
        User user = userRepository.save(newUser.toEntity());
        return user;

    }
}