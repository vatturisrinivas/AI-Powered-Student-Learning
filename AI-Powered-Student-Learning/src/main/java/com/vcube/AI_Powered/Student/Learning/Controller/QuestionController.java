package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.Question;
import com.vcube.AI_Powered.Student.Learning.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Question> getAll(@RequestParam(required = false) String skillTag) {
        if (skillTag != null && !skillTag.isBlank()) return service.getQuestionsBySkillTag(skillTag);
        return service.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Question getById(@PathVariable Long id) {
        return service.getQuestionById(id);
    }

    @PostMapping
    public ResponseEntity<Question> create(@RequestBody Question body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createQuestion(body));
    }

    @PutMapping("/{id}")
    public Question update(@PathVariable Long id, @RequestBody Question body) {
        return service.updateQuestion(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
