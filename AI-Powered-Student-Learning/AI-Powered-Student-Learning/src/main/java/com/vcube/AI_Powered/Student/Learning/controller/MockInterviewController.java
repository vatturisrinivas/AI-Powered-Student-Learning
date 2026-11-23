package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.MockInterview;
import com.vcube.AI_Powered.Student.Learning.service.MockInterviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mock-interviews")
@PreAuthorize("hasRole('ADMIN')")
public class MockInterviewController {

    private final MockInterviewService service;

    public MockInterviewController(MockInterviewService service) {
        this.service = service;
    }

    @GetMapping
    public List<MockInterview> getAll(@RequestParam(required = false) Long studentId) {
        if (studentId != null) return service.getMockInterviewsByStudentId(studentId);
        return service.getAllMockInterviews();
    }

    @GetMapping("/{id}")
    public MockInterview getById(@PathVariable Long id) {
        return service.getMockInterviewById(id);
    }

    @PostMapping
    public ResponseEntity<MockInterview> create(@RequestBody MockInterview body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createMockInterview(body));
    }

    @PutMapping("/{id}")
    public MockInterview update(@PathVariable Long id, @RequestBody MockInterview body) {
        return service.updateMockInterview(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteMockInterview(id);
        return ResponseEntity.noContent().build();
    }
}
