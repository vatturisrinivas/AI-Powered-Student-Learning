package com.vcube.AI_Powered.Student.Learning.config;

import com.vcube.AI_Powered.Student.Learning.entity.User;
import com.vcube.AI_Powered.Student.Learning.entity.UserRole;
import com.vcube.AI_Powered.Student.Learning.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Locale;

/** Creates the initial admin in the database only if no admin exists and startup credentials are explicitly supplied. */
@Configuration
public class InitialAdminSetup {
    @Bean
    CommandLineRunner initialAdministrator(UserRepository users, PasswordEncoder passwords) {
        return args -> {
            String email = System.getenv("INITIAL_ADMIN_EMAIL");
            String password = System.getenv("INITIAL_ADMIN_PASSWORD");
            if (email == null || password == null || email.isBlank() || password.length() < 12) return;
            if (!users.findByRole(UserRole.ADMIN).isEmpty()) return;
            String canonicalEmail = email.trim().toLowerCase(Locale.ROOT);
            if (users.existsByEmail(canonicalEmail)) {
                System.err.println("Admin setup skipped: email exists; use a different initial admin email.");
                return;
            }
            User user = new User();
            user.setName("Platform Administrator");
            user.setEmail(canonicalEmail);
            user.setPassword(passwords.encode(password));
            user.setRole(UserRole.ADMIN);
            users.save(user);
            System.out.println("Initial ADMIN account created in MySQL. Remove INITIAL_ADMIN_* variables after setup.");
        };
    }
}
