package com.vcube.AI_Powered.Student.Learning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vcube.AI_Powered.Student.Learning.entity.AssessmentAttempt;
import com.vcube.AI_Powered.Student.Learning.repository.AssessmentAttemptRepository;
import com.vcube.AI_Powered.Student.Learning.service.AssessmentAttemptService;

@Service
public class AssessmentAttemptServiceImpl implements AssessmentAttemptService {

    private final AssessmentAttemptRepository assessmentAttemptRepository;

    public AssessmentAttemptServiceImpl(
            AssessmentAttemptRepository assessmentAttemptRepository) {
        this.assessmentAttemptRepository = assessmentAttemptRepository;
    }

    @Override
    public AssessmentAttempt createAttempt(AssessmentAttempt attempt) {
        return assessmentAttemptRepository.save(attempt);
    }

    @Override
    public AssessmentAttempt getAttemptById(Long id) {
        return assessmentAttemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment attempt not found"));
    }

    @Override
    public List<AssessmentAttempt> getAllAttempts() {
        return assessmentAttemptRepository.findAll();
    }

    @Override
    public List<AssessmentAttempt> getAttemptsByAssessmentId(Long assessmentId) {
        return assessmentAttemptRepository.findByAssessment_Id(assessmentId);
    }

    @Override
    public List<AssessmentAttempt> getAttemptsByStudentId(Long studentId) {
        return assessmentAttemptRepository.findByStudent_Id(studentId);
    }

    @Override
    public AssessmentAttempt updateAttempt(Long id, AssessmentAttempt attempt) {
        AssessmentAttempt existingAttempt = getAttemptById(id);

        existingAttempt.setAssessment(attempt.getAssessment());
        existingAttempt.setStudent(attempt.getStudent());
        existingAttempt.setMarksObtained(attempt.getMarksObtained());
        existingAttempt.setTotalMarks(attempt.getTotalMarks());
        existingAttempt.setStatus(attempt.getStatus());
        existingAttempt.setStartedAt(attempt.getStartedAt());
        existingAttempt.setSubmittedAt(attempt.getSubmittedAt());

        return assessmentAttemptRepository.save(existingAttempt);
    }

    @Override
    public void deleteAttempt(Long id) {
        AssessmentAttempt existingAttempt = getAttemptById(id);
        assessmentAttemptRepository.delete(existingAttempt);
    }
}
