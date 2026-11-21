
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, Long> {

    Optional<Domain> findByName(String name);
}
