package com.vcube.AI_Powered.Student.Learning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcube.AI_Powered.Student.Learning.entity.Assessment;
import com.vcube.AI_Powered.Student.Learning.repository.AssessmentRepository;
import com.vcube.AI_Powered.Student.Learning.service.AssessmentService;

@Service
public class AssessmentServiceImpl implements AssessmentService {

	@Autowired
    private final AssessmentRepository assessmentRepository;

    public AssessmentServiceImpl(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    @Override
    public Assessment createAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    @Override
    public Assessment getAssessmentById(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
    }

    @Override
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    @Override
    public List<Assessment> getAssessmentsByCourseId(Long courseId) {
        return assessmentRepository.findByCourseId(courseId);
    }

    @Override
    public Assessment updateAssessment(Long id, Assessment assessment) {
        Assessment existingAssessment = getAssessmentById(id);

        existingAssessment.setCourse(assessment.getCourse());
        existingAssessment.setTitle(assessment.getTitle());
        existingAssessment.setTopic(assessment.getTopic());
        existingAssessment.setTotalMarks(assessment.getTotalMarks());

        return assessmentRepository.save(existingAssessment);
    }

    @Override
    public void deleteAssessment(Long id) {
        Assessment existingAssessment = getAssessmentById(id);
        assessmentRepository.delete(existingAssessment);
    }

	@Override
	public List<Assessment> findByCourse_Id(Long courseId) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
