package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.PlacementReadiness;

import java.util.List;

public interface PlacementReadinessService {

    PlacementReadiness createPlacementReadiness(PlacementReadiness readiness);

    List<PlacementReadiness> getAllPlacementReadinessRecords();

    PlacementReadiness getPlacementReadinessById(Long id);

    PlacementReadiness getPlacementReadinessByStudentId(Long studentId);

    PlacementReadiness updatePlacementReadiness(
            Long id, PlacementReadiness readinessDetails);

    void deletePlacementReadiness(Long id);
}
