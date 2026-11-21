package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.Enrollment;
import com.vcube.AI_Powered.Student.Learning.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@PreAuthorize("hasRole('ADMIN')")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Enrollment> getAll(@RequestParam(required = false) Long studentId, @RequestParam(required = false) Long courseId) {
        if (studentId != null) return service.getEnrollmentsByStudentId(studentId);
        if (courseId != null) return service.getEnrollmentsByCourseId(courseId);
        return service.getAllEnrollments();
    }

    @GetMapping("/{id}")
    public Enrollment getById(@PathVariable Long id) {
        return service.getEnrollmentById(id);
    }

    @PostMapping
    public ResponseEntity<Enrollment> create(@RequestBody Enrollment body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createEnrollment(body));
    }

    @PutMapping("/{id}/status")
    public Enrollment updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateEnrollmentStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
