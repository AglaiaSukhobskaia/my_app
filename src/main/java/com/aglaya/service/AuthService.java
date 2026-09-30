package com.aglaya.service;

import com.aglaya.dto.request.LoginRq;
import com.aglaya.dto.request.RegistrationRq;
import com.aglaya.model.User;
import com.aglaya.repository.UserRepository;
import com.aglaya.security.JwtService;
import com.aglaya.security.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    JwtService jwtService;

    /**
     * Регистрация нового пользователя
     *
     * @param request объект запроса на регистрацию нового пользователя
     */
    public void register(RegistrationRq request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .build();

        userRepository.save(user);
    }

    /**
     * Авторизация пользователя
     *
     * @param request объект запроса на авторизацию пользователя
     * @return подписанный JWT-токен в строковом представлении
     */
    public String login(LoginRq request) {
        var user =  userRepository.findByUsername(request.username()).orElseThrow(
                () -> new RuntimeException("Username not found")
        );

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        var userDetails = new UserDetailsImpl(user);

        return jwtService.generateToken(userDetails);
    }
}
