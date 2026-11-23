package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.LearningPath;
import com.vcube.AI_Powered.Student.Learning.service.LearningPathService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/learning-paths")
@PreAuthorize("hasRole('ADMIN')")
public class LearningPathController {

    private final LearningPathService service;

    public LearningPathController(LearningPathService service) {
        this.service = service;
    }

    @GetMapping
    public List<LearningPath> getAll(@RequestParam(required = false) Long studentId) {
        if (studentId != null) return service.getLearningPathsByStudentId(studentId);
        return service.getAllLearningPaths();
    }

    @GetMapping("/{id}")
    public LearningPath getById(@PathVariable Long id) {
        return service.getLearningPathById(id);
    }

    @PostMapping
    public ResponseEntity<LearningPath> create(@RequestBody LearningPath body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createLearningPath(body));
    }

    @PutMapping("/{id}")
    public LearningPath update(@PathVariable Long id, @RequestBody LearningPath body) {
        return service.updateLearningPath(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteLearningPath(id);
        return ResponseEntity.noContent().build();
    }
}
