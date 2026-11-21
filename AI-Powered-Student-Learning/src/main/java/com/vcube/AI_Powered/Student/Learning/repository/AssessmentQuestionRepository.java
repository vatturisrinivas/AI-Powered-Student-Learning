
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.AssessmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssessmentQuestionRepository
        extends JpaRepository<AssessmentQuestion, Long> {

    List<AssessmentQuestion> findByAssessmentIdOrderByPositionAsc(
            Long assessmentId);
}
