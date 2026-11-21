package com.vcube.AI_Powered.Student.Learning.service;

import java.util.List;

import com.vcube.AI_Powered.Student.Learning.entity.AssessmentQuestion;

public interface AssessmentQuestionService {

    AssessmentQuestion createAssessmentQuestion(
            AssessmentQuestion assessmentQuestion);

    AssessmentQuestion getAssessmentQuestionById(Long id);

    List<AssessmentQuestion> getAllAssessmentQuestions();

    List<AssessmentQuestion> getAssessmentQuestionsByAssessmentId(
            Long assessmentId);

    AssessmentQuestion updateAssessmentQuestion(
            Long id, AssessmentQuestion assessmentQuestion);

    void deleteAssessmentQuestion(Long id);
}
