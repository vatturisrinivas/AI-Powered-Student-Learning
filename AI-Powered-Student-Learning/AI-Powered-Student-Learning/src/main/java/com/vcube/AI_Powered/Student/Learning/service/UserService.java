package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.User;
import com.vcube.AI_Powered.Student.Learning.entity.UserRole;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getUsersByRole(UserRole role);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);
}
