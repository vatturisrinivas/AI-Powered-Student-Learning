package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.StudentPerformance;

import java.util.List;

public interface StudentPerformanceService {

    StudentPerformance createStudentPerformance(StudentPerformance performance);

    List<StudentPerformance> getAllStudentPerformanceRecords();

    StudentPerformance getStudentPerformanceById(Long id);

    List<StudentPerformance> getPerformanceByStudentId(Long studentId);

    StudentPerformance getPerformanceByAttemptId(Long attemptId);

    StudentPerformance updateStudentPerformance(
            Long id, StudentPerformance performanceDetails);

    void deleteStudentPerformance(Long id);
}
