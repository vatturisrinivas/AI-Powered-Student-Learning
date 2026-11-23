package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {

    Enrollment createEnrollment(Enrollment enrollment);

    List<Enrollment> getAllEnrollments();

    Enrollment getEnrollmentById(Long id);

    List<Enrollment> getEnrollmentsByStudentId(Long studentId);

    List<Enrollment> getEnrollmentsByCourseId(Long courseId);

    boolean isStudentEnrolled(Long studentId, Long courseId);

    Enrollment updateEnrollmentStatus(Long id, String status);

    void deleteEnrollment(Long id);
}
