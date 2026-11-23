package com.vcube.AI_Powered.Student.Learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vcube.AI_Powered.Student.Learning.entity.AssessmentAttempt;

public interface AssessmentAttemptRepository
        extends JpaRepository<AssessmentAttempt, Long> {

    List<AssessmentAttempt> findByAssessment_Id(Long assessmentId);

    List<AssessmentAttempt> findByStudent_Id(Long studentId);
}
