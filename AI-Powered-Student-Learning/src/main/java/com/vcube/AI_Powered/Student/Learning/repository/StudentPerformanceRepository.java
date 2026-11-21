
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.StudentPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentPerformanceRepository
        extends JpaRepository<StudentPerformance, Long> {

    List<StudentPerformance> findByStudentId(Long studentId);

    Optional<StudentPerformance> findByAttemptId(Long attemptId);
}
