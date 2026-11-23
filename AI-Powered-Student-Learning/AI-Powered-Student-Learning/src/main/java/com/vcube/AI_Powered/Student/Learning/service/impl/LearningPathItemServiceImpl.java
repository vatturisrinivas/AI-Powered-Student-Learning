package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.LearningPathItem;
import com.vcube.AI_Powered.Student.Learning.repository.LearningPathItemRepository;
import com.vcube.AI_Powered.Student.Learning.service.LearningPathItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LearningPathItemServiceImpl implements LearningPathItemService {

    private final LearningPathItemRepository learningPathItemRepository;

    public LearningPathItemServiceImpl(
            LearningPathItemRepository learningPathItemRepository) {
        this.learningPathItemRepository = learningPathItemRepository;
    }

    @Override
    public LearningPathItem createLearningPathItem(LearningPathItem item) {
        return learningPathItemRepository.save(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningPathItem> getAllLearningPathItems() {
        return learningPathItemRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public LearningPathItem getLearningPathItemById(Long id) {
        return learningPathItemRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Learning path item not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LearningPathItem> getItemsByLearningPathId(Long learningPathId) {
        return learningPathItemRepository
                .findByLearningPathIdOrderByPositionAsc(learningPathId);
    }

    @Override
    public LearningPathItem updateLearningPathItem(
            Long id, LearningPathItem itemDetails) {
        LearningPathItem existingItem = getLearningPathItemById(id);

        existingItem.setLearningPath(itemDetails.getLearningPath());
        existingItem.setCourseTopic(itemDetails.getCourseTopic());
        existingItem.setTitle(itemDetails.getTitle());
        existingItem.setDescription(itemDetails.getDescription());
        existingItem.setPosition(itemDetails.getPosition());
        existingItem.setCompleted(itemDetails.getCompleted());

        return learningPathItemRepository.save(existingItem);
    }

    @Override
    public void deleteLearningPathItem(Long id) {
        LearningPathItem existingItem = getLearningPathItemById(id);
        learningPathItemRepository.delete(existingItem);
    }
}
