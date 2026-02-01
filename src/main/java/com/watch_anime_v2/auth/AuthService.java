package com.watch_anime_v2.auth;

import com.watch_anime_v2.auth.dto.AuthResponseDto;
import com.watch_anime_v2.auth.dto.AuthDto;
import com.watch_anime_v2.auth.dto.RegisterDto;
import com.watch_anime_v2.auth.security.JwtUtils;
import com.watch_anime_v2.user.Role;
import com.watch_anime_v2.user.User;
import com.watch_anime_v2.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional
    public AuthResponseDto register(RegisterDto dto) {
        // Проверяем, существует ли пользователь с таким email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email уже зарегистрирован");
        }

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username уже занят");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole());

        AuthResponseDto response = new AuthResponseDto(
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                token
        );

        return response;
    }


    public AuthResponseDto login(AuthDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверные учетные данные"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверные учетные данные");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole());

        return new AuthResponseDto(
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                token
        );
    }
}
