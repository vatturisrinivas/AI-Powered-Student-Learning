package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.PlacementReadiness;
import com.vcube.AI_Powered.Student.Learning.repository.PlacementReadinessRepository;
import com.vcube.AI_Powered.Student.Learning.service.PlacementReadinessService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PlacementReadinessServiceImpl
        implements PlacementReadinessService {

    private final PlacementReadinessRepository readinessRepository;

    public PlacementReadinessServiceImpl(
            PlacementReadinessRepository readinessRepository) {
        this.readinessRepository = readinessRepository;
    }

    @Override
    public PlacementReadiness createPlacementReadiness(
            PlacementReadiness readiness) {
        Long studentId = readiness.getStudent().getId();

        if (readinessRepository.findByStudentId(studentId).isPresent()) {
            throw new IllegalStateException(
                    "Placement readiness already exists for student id: "
                            + studentId);
        }

        return readinessRepository.save(readiness);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlacementReadiness> getAllPlacementReadinessRecords() {
        return readinessRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PlacementReadiness getPlacementReadinessById(Long id) {
        return readinessRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Placement readiness not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public PlacementReadiness getPlacementReadinessByStudentId(Long studentId) {
        return readinessRepository.findByStudentId(studentId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Placement readiness not found for student id: "
                                        + studentId));
    }

    @Override
    public PlacementReadiness updatePlacementReadiness(
            Long id, PlacementReadiness readinessDetails) {
        PlacementReadiness existingReadiness = getPlacementReadinessById(id);

        existingReadiness.setStudent(readinessDetails.getStudent());
        existingReadiness.setCareerRole(readinessDetails.getCareerRole());
        existingReadiness.setAssessmentScore(
                readinessDetails.getAssessmentScore());
        existingReadiness.setInterviewScore(
                readinessDetails.getInterviewScore());
        existingReadiness.setReadinessScore(
                readinessDetails.getReadinessScore());
        existingReadiness.setRecommendations(
                readinessDetails.getRecommendations());
        existingReadiness.setUpdatedAt(LocalDateTime.now());

        return readinessRepository.save(existingReadiness);
    }

    @Override
    public void deletePlacementReadiness(Long id) {
        PlacementReadiness existingReadiness = getPlacementReadinessById(id);
        readinessRepository.delete(existingReadiness);
    }
}
