package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.StudentPerformance;
import com.vcube.AI_Powered.Student.Learning.repository.StudentPerformanceRepository;
import com.vcube.AI_Powered.Student.Learning.service.StudentPerformanceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentPerformanceServiceImpl
        implements StudentPerformanceService {

    private final StudentPerformanceRepository performanceRepository;

    public StudentPerformanceServiceImpl(
            StudentPerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    @Override
    public StudentPerformance createStudentPerformance(
            StudentPerformance performance) {
        Long attemptId = performance.getAttempt().getId();

        if (performanceRepository.findByAttemptId(attemptId).isPresent()) {
            throw new IllegalStateException(
                    "A performance record already exists for attempt id: "
                            + attemptId);
        }

        return performanceRepository.save(performance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPerformance> getAllStudentPerformanceRecords() {
        return performanceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentPerformance getStudentPerformanceById(Long id) {
        return performanceRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Student performance not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPerformance> getPerformanceByStudentId(Long studentId) {
        return performanceRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentPerformance getPerformanceByAttemptId(Long attemptId) {
        return performanceRepository.findByAttemptId(attemptId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Student performance not found for attempt id: "
                                        + attemptId));
    }

    @Override
    public StudentPerformance updateStudentPerformance(
            Long id, StudentPerformance performanceDetails) {
        StudentPerformance existingPerformance =
                getStudentPerformanceById(id);

        Long newAttemptId = performanceDetails.getAttempt().getId();

        performanceRepository.findByAttemptId(newAttemptId)
                .filter(record -> !record.getId().equals(id))
                .ifPresent(record -> {
                    throw new IllegalStateException(
                            "A performance record already exists for attempt id: "
                                    + newAttemptId);
                });

        existingPerformance.setStudent(performanceDetails.getStudent());
        existingPerformance.setAttempt(performanceDetails.getAttempt());
        existingPerformance.setPercentage(performanceDetails.getPercentage());
        existingPerformance.setSkillAnalysis(
                performanceDetails.getSkillAnalysis());

        return performanceRepository.save(existingPerformance);
    }

    @Override
    public void deleteStudentPerformance(Long id) {
        StudentPerformance existingPerformance =
                getStudentPerformanceById(id);
        performanceRepository.delete(existingPerformance);
    }
}
