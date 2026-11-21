package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.AssessmentAttempt;
import com.vcube.AI_Powered.Student.Learning.service.AssessmentAttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assessment-attempts")
@PreAuthorize("hasRole('ADMIN')")
public class AssessmentAttemptController {

    private final AssessmentAttemptService service;

    public AssessmentAttemptController(AssessmentAttemptService service) {
        this.service = service;
    }

    @GetMapping
    public List<AssessmentAttempt> getAll(@RequestParam(required = false) Long assessmentId, @RequestParam(required = false) Long studentId) {
        if (assessmentId != null) return service.getAttemptsByAssessmentId(assessmentId);
        if (studentId != null) return service.getAttemptsByStudentId(studentId);
        return service.getAllAttempts();
    }

    @GetMapping("/{id}")
    public AssessmentAttempt getById(@PathVariable Long id) {
        return service.getAttemptById(id);
    }

    @PostMapping
    public ResponseEntity<AssessmentAttempt> create(@RequestBody AssessmentAttempt body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createAttempt(body));
    }

    @PutMapping("/{id}")
    public AssessmentAttempt update(@PathVariable Long id, @RequestBody AssessmentAttempt body) {
        return service.updateAttempt(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAttempt(id);
        return ResponseEntity.noContent().build();
    }
}
