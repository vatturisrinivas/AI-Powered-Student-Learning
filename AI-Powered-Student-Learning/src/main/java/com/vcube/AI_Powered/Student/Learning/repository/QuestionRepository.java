
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository
        extends JpaRepository<Question, Long> {

    List<Question> findBySkillTag(String skillTag);
}
