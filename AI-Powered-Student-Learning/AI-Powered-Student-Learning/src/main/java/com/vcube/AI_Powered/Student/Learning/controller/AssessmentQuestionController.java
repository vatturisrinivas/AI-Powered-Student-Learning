package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.AssessmentQuestion;
import com.vcube.AI_Powered.Student.Learning.service.AssessmentQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assessment-questions")
@PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
public class AssessmentQuestionController {

    private final AssessmentQuestionService service;

    public AssessmentQuestionController(AssessmentQuestionService service) {
        this.service = service;
    }

    @GetMapping
    public List<AssessmentQuestion> getAll(@RequestParam(required = false) Long assessmentId) {
        if (assessmentId != null) return service.getAssessmentQuestionsByAssessmentId(assessmentId);
        return service.getAllAssessmentQuestions();
    }

    @GetMapping("/{id}")
    public AssessmentQuestion getById(@PathVariable Long id) {
        return service.getAssessmentQuestionById(id);
    }

    @PostMapping
    public ResponseEntity<AssessmentQuestion> create(@RequestBody AssessmentQuestion body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createAssessmentQuestion(body));
    }

    @PutMapping("/{id}")
    public AssessmentQuestion update(@PathVariable Long id, @RequestBody AssessmentQuestion body) {
        return service.updateAssessmentQuestion(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAssessmentQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
