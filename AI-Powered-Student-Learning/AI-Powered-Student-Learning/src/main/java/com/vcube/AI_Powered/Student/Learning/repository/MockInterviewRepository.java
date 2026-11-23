
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.MockInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MockInterviewRepository
        extends JpaRepository<MockInterview, Long> {

    List<MockInterview> findByStudentId(Long studentId);
}
