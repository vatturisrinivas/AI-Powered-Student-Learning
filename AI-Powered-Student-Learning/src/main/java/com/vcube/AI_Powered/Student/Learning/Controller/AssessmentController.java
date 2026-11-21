package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.Assessment;
import com.vcube.AI_Powered.Student.Learning.service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService service;

    public AssessmentController(AssessmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Assessment> getAll(@RequestParam(required = false) Long courseId) {
        if (courseId != null) return service.getAssessmentsByCourseId(courseId);
        return service.getAllAssessments();
    }

    @GetMapping("/{id}")
    public Assessment getById(@PathVariable Long id) {
        return service.getAssessmentById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PostMapping
    public ResponseEntity<Assessment> create(@RequestBody Assessment body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createAssessment(body));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PutMapping("/{id}")
    public Assessment update(@PathVariable Long id, @RequestBody Assessment body) {
        return service.updateAssessment(id, body);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAssessment(id);
        return ResponseEntity.noContent().build();
    }
}
