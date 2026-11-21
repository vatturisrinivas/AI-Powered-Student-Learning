
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.AssessmentAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssessmentAttemptRepository
        extends JpaRepository<AssessmentAttempt, Long> {

    List<AssessmentAttempt> findByStudentId(Long studentId);

    List<AssessmentAttempt> findByStudentIdAndAssessmentId(
            Long studentId, Long assessmentId);
}
