package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.LearningRoadmap;
import com.vcube.AI_Powered.Student.Learning.service.LearningRoadmapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/learning-roadmaps")
@PreAuthorize("hasRole('ADMIN')")
public class LearningRoadmapController {

    private final LearningRoadmapService service;

    public LearningRoadmapController(LearningRoadmapService service) {
        this.service = service;
    }

    @GetMapping
    public List<LearningRoadmap> getAll(@RequestParam(required = false) Long learningPathId) {
        if (learningPathId != null) return service.getRoadmapsByLearningPathId(learningPathId);
        return service.getAllLearningRoadmaps();
    }

    @GetMapping("/{id}")
    public LearningRoadmap getById(@PathVariable Long id) {
        return service.getLearningRoadmapById(id);
    }

    @PostMapping
    public ResponseEntity<LearningRoadmap> create(@RequestBody LearningRoadmap body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createLearningRoadmap(body));
    }

    @PutMapping("/{id}")
    public LearningRoadmap update(@PathVariable Long id, @RequestBody LearningRoadmap body) {
        return service.updateLearningRoadmap(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteLearningRoadmap(id);
        return ResponseEntity.noContent().build();
    }
}
