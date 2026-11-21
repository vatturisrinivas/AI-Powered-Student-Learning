package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.LearningPath;
import com.vcube.AI_Powered.Student.Learning.repository.LearningPathRepository;
import com.vcube.AI_Powered.Student.Learning.service.LearningPathService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LearningPathServiceImpl implements LearningPathService {

    private final LearningPathRepository learningPathRepository;

    public LearningPathServiceImpl(LearningPathRepository learningPathRepository) {
        this.learningPathRepository = learningPathRepository;
    }

    @Override
    public LearningPath createLearningPath(LearningPath learningPath) {
        return learningPathRepository.save(learningPath);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningPath> getAllLearningPaths() {
        return learningPathRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public LearningPath getLearningPathById(Long id) {
        return learningPathRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Learning path not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningPath> getLearningPathsByStudentId(Long studentId) {
        return learningPathRepository.findByStudentId(studentId);
    }

    @Override
    public LearningPath updateLearningPath(Long id, LearningPath pathDetails) {
        LearningPath existingPath = getLearningPathById(id);

        existingPath.setStudent(pathDetails.getStudent());
        existingPath.setCareerRole(pathDetails.getCareerRole());
        existingPath.setTitle(pathDetails.getTitle());
        existingPath.setStatus(pathDetails.getStatus());

        return learningPathRepository.save(existingPath);
    }

    @Override
    public void deleteLearningPath(Long id) {
        LearningPath existingPath = getLearningPathById(id);
        learningPathRepository.delete(existingPath);
    }
}
