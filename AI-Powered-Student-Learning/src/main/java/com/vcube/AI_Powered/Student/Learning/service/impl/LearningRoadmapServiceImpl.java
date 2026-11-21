package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.LearningRoadmap;
import com.vcube.AI_Powered.Student.Learning.repository.LearningRoadmapRepository;
import com.vcube.AI_Powered.Student.Learning.service.LearningRoadmapService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LearningRoadmapServiceImpl implements LearningRoadmapService {

    private final LearningRoadmapRepository learningRoadmapRepository;

    public LearningRoadmapServiceImpl(
            LearningRoadmapRepository learningRoadmapRepository) {
        this.learningRoadmapRepository = learningRoadmapRepository;
    }

    @Override
    public LearningRoadmap createLearningRoadmap(LearningRoadmap roadmap) {
        return learningRoadmapRepository.save(roadmap);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningRoadmap> getAllLearningRoadmaps() {
        return learningRoadmapRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public LearningRoadmap getLearningRoadmapById(Long id) {
        return learningRoadmapRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Learning roadmap not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningRoadmap> getRoadmapsByLearningPathId(Long learningPathId) {
        return learningRoadmapRepository.findByLearningPathId(learningPathId);
    }

    @Override
    public LearningRoadmap updateLearningRoadmap(
            Long id, LearningRoadmap roadmapDetails) {
        LearningRoadmap existingRoadmap = getLearningRoadmapById(id);

        existingRoadmap.setLearningPath(roadmapDetails.getLearningPath());
        existingRoadmap.setWeakSkills(roadmapDetails.getWeakSkills());
        existingRoadmap.setRoadmapContent(roadmapDetails.getRoadmapContent());

        return learningRoadmapRepository.save(existingRoadmap);
    }

    @Override
    public void deleteLearningRoadmap(Long id) {
        LearningRoadmap existingRoadmap = getLearningRoadmapById(id);
        learningRoadmapRepository.delete(existingRoadmap);
    }
}
