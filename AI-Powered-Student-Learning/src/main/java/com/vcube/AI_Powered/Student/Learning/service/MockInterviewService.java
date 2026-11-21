package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.MockInterview;

import java.util.List;

public interface MockInterviewService {

    MockInterview createMockInterview(MockInterview mockInterview);

    List<MockInterview> getAllMockInterviews();

    MockInterview getMockInterviewById(Long id);

    List<MockInterview> getMockInterviewsByStudentId(Long studentId);

    MockInterview updateMockInterview(Long id, MockInterview interviewDetails);

    void deleteMockInterview(Long id);
}
