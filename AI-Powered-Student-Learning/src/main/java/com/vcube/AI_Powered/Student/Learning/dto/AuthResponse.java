package com.vcube.AI_Powered.Student.Learning.dto;

import com.vcube.AI_Powered.Student.Learning.entity.UserRole;

public record AuthResponse(String token, String tokenType, Long userId, String name,
                           String email, UserRole role) {}
