package com.vcube.AI_Powered.Student.Learning.service;

import java.util.List;

import com.vcube.AI_Powered.Student.Learning.entity.Assessment;

public interface AssessmentService {

    Assessment createAssessment(Assessment assessment);

    Assessment getAssessmentById(Long id);

    List<Assessment> getAllAssessments();

    List<Assessment> getAssessmentsByCourseId(Long courseId);

    Assessment updateAssessment(Long id, Assessment assessment);

    void deleteAssessment(Long id);
}
