package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.CareerRole;
import com.vcube.AI_Powered.Student.Learning.service.CareerRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/career-roles")
public class CareerRoleController {

    private final CareerRoleService service;

    public CareerRoleController(CareerRoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<CareerRole> getAll(@RequestParam(required = false) Long domainId) {
        if (domainId != null) return service.getCareerRolesByDomainId(domainId);
        return service.getAllCareerRoles();
    }

    @GetMapping("/{id}")
    public CareerRole getById(@PathVariable Long id) {
        return service.getCareerRoleById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PostMapping
    public ResponseEntity<CareerRole> create(@RequestBody CareerRole body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createCareerRole(body));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PutMapping("/{id}")
    public CareerRole update(@PathVariable Long id, @RequestBody CareerRole body) {
        return service.updateCareerRole(id, body);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCareerRole(id);
        return ResponseEntity.noContent().build();
    }
}
