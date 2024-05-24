package com.tfg.backend.jwt.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfg.backend.entities.Role;
import com.tfg.backend.entities.User;
import com.tfg.backend.jwt.reponse.AuthResponse;
import com.tfg.backend.jwt.requests.LoginRequest;
import com.tfg.backend.jwt.requests.RegisterRequest;
import com.tfg.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// Service for handling user authentication and registration
public class AuthService {

    private final UserRepository userRepository; // Repository for user data
    private final JwtService jwtService; // Service for handling JWT operations
    private final PasswordEncoder passwordEncoder; // Password encoder for encoding passwords
    private final AuthenticationManager authenticationManager; // Authentication manager for authenticating users

    // Authenticates a user and generates a JWT token
    public AuthResponse login(LoginRequest request) {
        // Authenticates user using Spring Security's AuthenticationManager
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        // Retrieves UserDetails for the authenticated user
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        // Generates JWT token for the authenticated user
        String token = jwtService.getToken(user);
        // Constructs and returns the authentication response containing the JWT token
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    // Registers a new user and generates a JWT token
    public AuthResponse register(RegisterRequest request) {
        // Creates a new user entity with the provided registration details
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .registrationDate(request.getRegistrationDate())
            .role(Role.USER)
            .build();

        // Saves the new user to the database
        userRepository.save(user);

        // Generates JWT token for the registered user
        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
    }
}
