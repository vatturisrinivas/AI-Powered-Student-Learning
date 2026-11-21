package com.vcube.AI_Powered.Student.Learning.repository;

<<<<<<< HEAD
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vcube.AI_Powered.Student.Learning.entity.Domain;

public interface DomainRepository extends JpaRepository<Domain, Long> {

    Optional<Domain> findByName(String name);

    boolean existsByName(String name);
=======
public class DomainRepository {

>>>>>>> d50750c1e384b0282ba271c569b67d1e2e5968ed
}
