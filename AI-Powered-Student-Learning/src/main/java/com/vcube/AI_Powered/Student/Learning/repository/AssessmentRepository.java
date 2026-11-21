
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssessmentRepository
        extends JpaRepository<Assessment, Long> {

    List<Assessment> findByCourseId(Long courseId);
}
