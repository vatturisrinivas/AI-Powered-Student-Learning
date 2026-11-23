package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.StudentResponse;

import java.util.List;

public interface StudentResponseService {

    StudentResponse createStudentResponse(StudentResponse response);

    List<StudentResponse> getAllStudentResponses();

    StudentResponse getStudentResponseById(Long id);

    List<StudentResponse> getResponsesByAttemptId(Long attemptId);

    StudentResponse updateStudentResponse(
            Long id, StudentResponse responseDetails);

    void deleteStudentResponse(Long id);
}
