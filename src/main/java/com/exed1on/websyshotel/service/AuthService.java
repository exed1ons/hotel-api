package com.exed1on.websyshotel.service;

import com.exed1on.websyshotel.dto.request.AuthRequest;
import com.exed1on.websyshotel.dto.response.AuthResponse;
import com.exed1on.websyshotel.entity.User;
import com.exed1on.websyshotel.exception.InvalidBookingException;
import com.exed1on.websyshotel.repository.UserRepository;
import com.exed1on.websyshotel.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidBookingException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidBookingException("Invalid username or password");
        }

        if (!user.isEnabled()) {
            throw new InvalidBookingException("User account is disabled");
        }

        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), "Login successful");
    }

    public void registerUser(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new InvalidBookingException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setEnabled(true);
        userRepository.save(user);
    }
}
