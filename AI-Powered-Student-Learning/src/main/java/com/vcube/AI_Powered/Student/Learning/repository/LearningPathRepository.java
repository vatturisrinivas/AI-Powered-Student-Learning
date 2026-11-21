
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.LearningPath;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LearningPathRepository
        extends JpaRepository<LearningPath, Long> {

    List<LearningPath> findByStudentId(Long studentId);
}
