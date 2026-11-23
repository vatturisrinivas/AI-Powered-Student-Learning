package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.Course;
import com.vcube.AI_Powered.Student.Learning.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import com.vcube.AI_Powered.Student.Learning.repository.UserRepository;
import com.vcube.AI_Powered.Student.Learning.entity.UserRole;
import java.util.Objects;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;
    private final UserRepository users;

    public CourseController(CourseService service, UserRepository users) {
        this.service = service; this.users = users;
    }

    @GetMapping
    public List<Course> getAll(@RequestParam(required = false) Long domainId, @RequestParam(required = false) Long trainerId, @RequestParam(required = false) String title) {
        if (domainId != null) return service.getCoursesByDomainId(domainId);
        if (trainerId != null) return service.getCoursesByTrainerId(trainerId);
        if (title != null && !title.isBlank()) return service.searchCoursesByTitle(title);
        return service.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return service.getCourseById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course body, Authentication auth) {
        var user=users.findByEmail(auth.getName()).orElseThrow();
        if(user.getRole()==UserRole.TRAINER) body.setTrainer(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createCourse(body));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course body, Authentication auth) {
        var user=users.findByEmail(auth.getName()).orElseThrow();
        var existing=service.getCourseById(id);
        if(user.getRole()==UserRole.TRAINER){
            if(!Objects.equals(existing.getTrainer().getId(),user.getId())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not your course");
            body.setTrainer(user);
        }
        return service.updateCourse(id, body);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        var user=users.findByEmail(auth.getName()).orElseThrow();
        if(user.getRole()==UserRole.TRAINER && !Objects.equals(service.getCourseById(id).getTrainer().getId(),user.getId())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not your course");
        service.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
