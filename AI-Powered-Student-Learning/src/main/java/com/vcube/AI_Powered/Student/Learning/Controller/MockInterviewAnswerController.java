package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.MockInterviewAnswer;
import com.vcube.AI_Powered.Student.Learning.service.MockInterviewAnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mock-interview-answers")
@PreAuthorize("hasRole('ADMIN')")
public class MockInterviewAnswerController {

    private final MockInterviewAnswerService service;

    public MockInterviewAnswerController(MockInterviewAnswerService service) {
        this.service = service;
    }

    @GetMapping
    public List<MockInterviewAnswer> getAll(@RequestParam(required = false) Long mockInterviewId) {
        if (mockInterviewId != null) return service.getAnswersByMockInterviewId(mockInterviewId);
        return service.getAllAnswers();
    }

    @GetMapping("/{id}")
    public MockInterviewAnswer getById(@PathVariable Long id) {
        return service.getAnswerById(id);
    }

    @PostMapping
    public ResponseEntity<MockInterviewAnswer> create(@RequestBody MockInterviewAnswer body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createAnswer(body));
    }

    @PutMapping("/{id}")
    public MockInterviewAnswer update(@PathVariable Long id, @RequestBody MockInterviewAnswer body) {
        return service.updateAnswer(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
