package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.Assessment;
import com.vcube.AI_Powered.Student.Learning.service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.vcube.AI_Powered.Student.Learning.repository.UserRepository;
import com.vcube.AI_Powered.Student.Learning.entity.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import java.util.Objects;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService service;
    private final UserRepository users;
    private final com.vcube.AI_Powered.Student.Learning.repository.CourseRepository courses;

    public AssessmentController(AssessmentService service, UserRepository users, com.vcube.AI_Powered.Student.Learning.repository.CourseRepository courses) {
        this.service = service; this.users=users; this.courses=courses;
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
    public ResponseEntity<Assessment> create(@RequestBody Assessment body, Authentication auth) {
        checkTrainer(auth,courses.findById(body.getCourse().getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found")).getTrainer().getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createAssessment(body));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PutMapping("/{id}")
    public Assessment update(@PathVariable Long id, @RequestBody Assessment body, Authentication auth) {
        checkTrainer(auth,service.getAssessmentById(id).getCourse().getTrainer().getId());
        checkTrainer(auth,courses.findById(body.getCourse().getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found")).getTrainer().getId());
        return service.updateAssessment(id, body);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        checkTrainer(auth,service.getAssessmentById(id).getCourse().getTrainer().getId());
        service.deleteAssessment(id);
        return ResponseEntity.noContent().build();
    }
    private void checkTrainer(Authentication auth,Long trainerId){
        var user=users.findByEmail(auth.getName()).orElseThrow();
        if(user.getRole()==UserRole.TRAINER && !Objects.equals(user.getId(),trainerId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not your course");
    }
}
