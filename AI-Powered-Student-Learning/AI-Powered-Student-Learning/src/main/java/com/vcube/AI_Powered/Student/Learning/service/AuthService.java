package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.dto.*;
import com.vcube.AI_Powered.Student.Learning.entity.*;
import com.vcube.AI_Powered.Student.Learning.repository.UserRepository;
import com.vcube.AI_Powered.Student.Learning.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (users.existsByEmail(email))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        // Public registration is restricted to STUDENT. Trainer/admin accounts need approval.
        if (request.role() != null && request.role() != UserRole.STUDENT)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only student self-registration is allowed");
        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.STUDENT);
        try { user = users.saveAndFlush(user); }
        catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        return toResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        User user = users.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        return toResponse(user);
    }

    private AuthResponse toResponse(User user) {
        return new AuthResponse(jwtService.generateToken(user.getEmail()), "Bearer",
                user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    @Transactional(readOnly = true)
    public UserResponse me(String email) {
        User user = users.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
