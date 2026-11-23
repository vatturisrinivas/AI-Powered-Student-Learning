
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.PlacementReadiness;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlacementReadinessRepository
        extends JpaRepository<PlacementReadiness, Long> {

    Optional<PlacementReadiness> findByStudentId(Long studentId);
}
