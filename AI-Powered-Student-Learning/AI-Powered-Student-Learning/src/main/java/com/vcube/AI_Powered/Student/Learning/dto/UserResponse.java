package com.vcube.AI_Powered.Student.Learning.dto;

import com.vcube.AI_Powered.Student.Learning.entity.UserRole;

public record UserResponse(Long id, String name, String email, UserRole role) {}
