package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.Course;
import com.vcube.AI_Powered.Student.Learning.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
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
    public ResponseEntity<Course> create(@RequestBody Course body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createCourse(body));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course body) {
        return service.updateCourse(id, body);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
