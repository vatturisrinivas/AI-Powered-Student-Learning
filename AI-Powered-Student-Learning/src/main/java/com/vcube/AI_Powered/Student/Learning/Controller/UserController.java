package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.User;
import com.vcube.AI_Powered.Student.Learning.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.vcube.AI_Powered.Student.Learning.entity.UserRole;
import com.vcube.AI_Powered.Student.Learning.dto.UserResponse;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponse> getAll(@RequestParam(required = false) String email, @RequestParam(required = false) UserRole role) {
        if (email != null && !email.isBlank()) return List.of(service.getUserByEmail(email)).stream().map(this::toResponse).toList();
        if (role != null) return service.getUsersByRole(role).stream().map(this::toResponse).toList();
        return service.getAllUsers().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return toResponse(service.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody User body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(service.createUser(body)));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody User body) {
        return toResponse(service.updateUser(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(),
                user.getEmail(), user.getRole());
    }
}
