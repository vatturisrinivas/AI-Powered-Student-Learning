package com.vcube.AI_Powered.Student.Learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vcube.AI_Powered.Student.Learning.entity.Domain;

public interface DomainRepository extends JpaRepository<Domain, Long> {

    Optional<Domain> findByName(String name);

    boolean existsByName(String name);
}
