
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.MockInterviewAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MockInterviewAnswerRepository
        extends JpaRepository<MockInterviewAnswer, Long> {

    List<MockInterviewAnswer>
        findByMockInterviewIdOrderByPositionAsc(
            Long mockInterviewId);
}
