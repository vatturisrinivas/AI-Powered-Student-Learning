package com.vcube.AI_Powered.Student.Learning.controller;

import com.vcube.AI_Powered.Student.Learning.entity.Domain;
import com.vcube.AI_Powered.Student.Learning.service.DomainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/domains")
public class DomainController {

    private final DomainService service;

    public DomainController(DomainService service) {
        this.service = service;
    }

    @GetMapping
    public List<Domain> getAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) return List.of(service.getDomainByName(name));
        return service.getAllDomains();
    }

    @GetMapping("/{id}")
    public Domain getById(@PathVariable Long id) {
        return service.getDomainById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PostMapping
    public ResponseEntity<Domain> create(@RequestBody Domain body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createDomain(body));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PutMapping("/{id}")
    public Domain update(@PathVariable Long id, @RequestBody Domain body) {
        return service.updateDomain(id, body);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteDomain(id);
        return ResponseEntity.noContent().build();
    }
}
