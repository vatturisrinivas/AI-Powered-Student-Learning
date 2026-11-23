package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.MockInterview;
import com.vcube.AI_Powered.Student.Learning.repository.MockInterviewRepository;
import com.vcube.AI_Powered.Student.Learning.service.MockInterviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MockInterviewServiceImpl implements MockInterviewService {

    private final MockInterviewRepository mockInterviewRepository;

    public MockInterviewServiceImpl(
            MockInterviewRepository mockInterviewRepository) {
        this.mockInterviewRepository = mockInterviewRepository;
    }

    @Override
    public MockInterview createMockInterview(MockInterview mockInterview) {
        return mockInterviewRepository.save(mockInterview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockInterview> getAllMockInterviews() {
        return mockInterviewRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public MockInterview getMockInterviewById(Long id) {
        return mockInterviewRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Mock interview not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockInterview> getMockInterviewsByStudentId(Long studentId) {
        return mockInterviewRepository.findByStudentId(studentId);
    }

    @Override
    public MockInterview updateMockInterview(
            Long id, MockInterview interviewDetails) {
        MockInterview existingInterview = getMockInterviewById(id);

        existingInterview.setStudent(interviewDetails.getStudent());
        existingInterview.setCareerRole(interviewDetails.getCareerRole());
        existingInterview.setTopic(interviewDetails.getTopic());
        existingInterview.setStatus(interviewDetails.getStatus());
        existingInterview.setScore(interviewDetails.getScore());
        existingInterview.setOverallFeedback(
                interviewDetails.getOverallFeedback());

        return mockInterviewRepository.save(existingInterview);
    }

    @Override
    public void deleteMockInterview(Long id) {
        MockInterview existingInterview = getMockInterviewById(id);
        mockInterviewRepository.delete(existingInterview);
    }
}
