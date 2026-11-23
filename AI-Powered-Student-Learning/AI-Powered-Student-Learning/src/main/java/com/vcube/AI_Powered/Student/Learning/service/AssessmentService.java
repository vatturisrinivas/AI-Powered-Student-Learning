package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.Assessment;

import java.util.List;

public interface AssessmentService {

    Assessment createAssessment(Assessment assessment);

    Assessment getAssessmentById(Long id);

    List<Assessment> getAllAssessments();

    List<Assessment> findByCourse_Id(Long courseId);

    Assessment updateAssessment(Long id, Assessment updatedAssessment);

    void deleteAssessment(Long id);

	List<Assessment> getAssessmentsByCourseId(Long courseId);
}
