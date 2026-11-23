package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.Enrollment;
import com.vcube.AI_Powered.Student.Learning.repository.EnrollmentRepository;
import com.vcube.AI_Powered.Student.Learning.service.EnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Enrollment createEnrollment(Enrollment enrollment) {
        Long studentId = enrollment.getStudent().getId();
        Long courseId = enrollment.getCourse().getId();

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalStateException(
                    "This student is already enrolled in this course.");
        }

        return enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Enrollment not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isStudentEnrolled(Long studentId, Long courseId) {
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public Enrollment updateEnrollmentStatus(Long id, String status) {
        Enrollment enrollment = getEnrollmentById(id);
        enrollment.setStatus(status);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteEnrollment(Long id) {
        Enrollment enrollment = getEnrollmentById(id);
        enrollmentRepository.delete(enrollment);
    }
}
