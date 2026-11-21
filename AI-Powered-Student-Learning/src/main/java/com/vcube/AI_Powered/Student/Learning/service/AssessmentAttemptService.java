package com.vcube.AI_Powered.Student.Learning.service;

import java.util.List;

import com.vcube.AI_Powered.Student.Learning.entity.AssessmentAttempt;

public interface AssessmentAttemptService {

    AssessmentAttempt createAttempt(AssessmentAttempt attempt);

    AssessmentAttempt getAttemptById(Long id);

    List<AssessmentAttempt> getAllAttempts();

    List<AssessmentAttempt> getAttemptsByAssessmentId(Long assessmentId);

    List<AssessmentAttempt> getAttemptsByStudentId(Long studentId);

    AssessmentAttempt updateAttempt(Long id, AssessmentAttempt attempt);

    void deleteAttempt(Long id);
}
