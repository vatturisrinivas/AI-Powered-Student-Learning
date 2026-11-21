
package com.vcube.AI_Powered.Student.Learning.service;

import java.util.List;

import com.vcube.AI_Powered.Student.Learning.entity.User;
import com.vcube.AI_Powered.Student.Learning.entity.UserRole;

public interface UserService {

    User registerUser(User user);

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    void deleteUser(Long id);

}
