package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.StudentPerformance;
import com.vcube.AI_Powered.Student.Learning.service.StudentPerformanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/student-performance")
@PreAuthorize("hasRole('ADMIN')")
public class StudentPerformanceController {

    private final StudentPerformanceService service;

    public StudentPerformanceController(StudentPerformanceService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentPerformance> getAll(@RequestParam(required = false) Long studentId, @RequestParam(required = false) Long attemptId) {
        if (studentId != null) return service.getPerformanceByStudentId(studentId);
        if (attemptId != null) return List.of(service.getPerformanceByAttemptId(attemptId));
        return service.getAllStudentPerformanceRecords();
    }

    @GetMapping("/{id}")
    public StudentPerformance getById(@PathVariable Long id) {
        return service.getStudentPerformanceById(id);
    }

    @PostMapping
    public ResponseEntity<StudentPerformance> create(@RequestBody StudentPerformance body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createStudentPerformance(body));
    }

    @PutMapping("/{id}")
    public StudentPerformance update(@PathVariable Long id, @RequestBody StudentPerformance body) {
        return service.updateStudentPerformance(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteStudentPerformance(id);
        return ResponseEntity.noContent().build();
    }
}
