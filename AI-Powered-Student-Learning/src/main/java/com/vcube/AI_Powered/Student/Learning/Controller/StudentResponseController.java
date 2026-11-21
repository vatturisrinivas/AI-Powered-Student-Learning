package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.StudentResponse;
import com.vcube.AI_Powered.Student.Learning.service.StudentResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/student-responses")
@PreAuthorize("hasRole('ADMIN')")
public class StudentResponseController {

    private final StudentResponseService service;

    public StudentResponseController(StudentResponseService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentResponse> getAll(@RequestParam(required = false) Long attemptId) {
        if (attemptId != null) return service.getResponsesByAttemptId(attemptId);
        return service.getAllStudentResponses();
    }

    @GetMapping("/{id}")
    public StudentResponse getById(@PathVariable Long id) {
        return service.getStudentResponseById(id);
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody StudentResponse body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createStudentResponse(body));
    }

    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id, @RequestBody StudentResponse body) {
        return service.updateStudentResponse(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteStudentResponse(id);
        return ResponseEntity.noContent().build();
    }
}
