package com.vcube.AI_Powered.Student.Learning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vcube.AI_Powered.Student.Learning.entity.AssessmentQuestion;
import com.vcube.AI_Powered.Student.Learning.repository.AssessmentQuestionRepository;
import com.vcube.AI_Powered.Student.Learning.service.AssessmentQuestionService;

@Service
public class AssessmentQuestionServiceImpl
        implements AssessmentQuestionService {

    private final AssessmentQuestionRepository assessmentQuestionRepository;

    public AssessmentQuestionServiceImpl(
            AssessmentQuestionRepository assessmentQuestionRepository) {
        this.assessmentQuestionRepository = assessmentQuestionRepository;
    }

    @Override
    public AssessmentQuestion createAssessmentQuestion(
            AssessmentQuestion assessmentQuestion) {
        return assessmentQuestionRepository.save(assessmentQuestion);
    }

    @Override
    public AssessmentQuestion getAssessmentQuestionById(Long id) {
        return assessmentQuestionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Assessment question not found"));
    }

    @Override
    public List<AssessmentQuestion> getAllAssessmentQuestions() {
        return assessmentQuestionRepository.findAll();
    }

    @Override
    public List<AssessmentQuestion> getAssessmentQuestionsByAssessmentId(
            Long assessmentId) {
        return assessmentQuestionRepository
                .findByAssessmentIdOrderByPositionAsc(assessmentId);
    }

    @Override
    public AssessmentQuestion updateAssessmentQuestion(
            Long id, AssessmentQuestion assessmentQuestion) {
        AssessmentQuestion existingQuestion =
                getAssessmentQuestionById(id);

        existingQuestion.setAssessment(assessmentQuestion.getAssessment());
        existingQuestion.setQuestion(assessmentQuestion.getQuestion());
        existingQuestion.setMarks(assessmentQuestion.getMarks());
        existingQuestion.setPosition(assessmentQuestion.getPosition());

        return assessmentQuestionRepository.save(existingQuestion);
    }

    @Override
    public void deleteAssessmentQuestion(Long id) {
        AssessmentQuestion existingQuestion =
                getAssessmentQuestionById(id);
        assessmentQuestionRepository.delete(existingQuestion);
    }
}
