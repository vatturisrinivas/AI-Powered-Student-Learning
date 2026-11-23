package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.CourseTopic;
import com.vcube.AI_Powered.Student.Learning.service.CourseTopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/course-topics")
public class CourseTopicController {

    private final CourseTopicService service;

    public CourseTopicController(CourseTopicService service) {
        this.service = service;
    }

    @GetMapping
    public List<CourseTopic> getAll(@RequestParam(required = false) Long courseId) {
        if (courseId != null) return service.getCourseTopicsByCourseId(courseId);
        return service.getAllCourseTopics();
    }

    @GetMapping("/{id}")
    public CourseTopic getById(@PathVariable Long id) {
        return service.getCourseTopicById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PostMapping
    public ResponseEntity<CourseTopic> create(@RequestBody CourseTopic body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createCourseTopic(body));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PutMapping("/{id}")
    public CourseTopic update(@PathVariable Long id, @RequestBody CourseTopic body) {
        return service.updateCourseTopic(id, body);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCourseTopic(id);
        return ResponseEntity.noContent().build();
    }
}
