package com.vcube.AI_Powered.Student.Learning.dto;

import jakarta.validation.constraints.*;
import com.vcube.AI_Powered.Student.Learning.entity.UserRole;

public record RegisterRequest(
    @NotBlank String name,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8, max = 72) String password,
    UserRole role
) {}
