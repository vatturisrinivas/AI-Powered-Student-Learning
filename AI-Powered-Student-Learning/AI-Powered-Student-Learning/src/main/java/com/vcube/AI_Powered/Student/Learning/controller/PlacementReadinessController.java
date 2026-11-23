package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.PlacementReadiness;
import com.vcube.AI_Powered.Student.Learning.service.PlacementReadinessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/placement-readiness")
@PreAuthorize("hasRole('ADMIN')")
public class PlacementReadinessController {

    private final PlacementReadinessService service;

    public PlacementReadinessController(PlacementReadinessService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlacementReadiness> getAll(@RequestParam(required = false) Long studentId) {
        if (studentId != null) return List.of(service.getPlacementReadinessByStudentId(studentId));
        return service.getAllPlacementReadinessRecords();
    }

    @GetMapping("/{id}")
    public PlacementReadiness getById(@PathVariable Long id) {
        return service.getPlacementReadinessById(id);
    }

    @PostMapping
    public ResponseEntity<PlacementReadiness> create(@RequestBody PlacementReadiness body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createPlacementReadiness(body));
    }

    @PutMapping("/{id}")
    public PlacementReadiness update(@PathVariable Long id, @RequestBody PlacementReadiness body) {
        return service.updatePlacementReadiness(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePlacementReadiness(id);
        return ResponseEntity.noContent().build();
    }
}
