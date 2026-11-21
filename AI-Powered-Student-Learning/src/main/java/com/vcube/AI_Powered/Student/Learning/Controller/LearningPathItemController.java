package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.LearningPathItem;
import com.vcube.AI_Powered.Student.Learning.service.LearningPathItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/learning-path-items")
@PreAuthorize("hasRole('ADMIN')")
public class LearningPathItemController {

    private final LearningPathItemService service;

    public LearningPathItemController(LearningPathItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<LearningPathItem> getAll(@RequestParam(required = false) Long learningPathId) {
        if (learningPathId != null) return service.getItemsByLearningPathId(learningPathId);
        return service.getAllLearningPathItems();
    }

    @GetMapping("/{id}")
    public LearningPathItem getById(@PathVariable Long id) {
        return service.getLearningPathItemById(id);
    }

    @PostMapping
    public ResponseEntity<LearningPathItem> create(@RequestBody LearningPathItem body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createLearningPathItem(body));
    }

    @PutMapping("/{id}")
    public LearningPathItem update(@PathVariable Long id, @RequestBody LearningPathItem body) {
        return service.updateLearningPathItem(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteLearningPathItem(id);
        return ResponseEntity.noContent().build();
    }
}
