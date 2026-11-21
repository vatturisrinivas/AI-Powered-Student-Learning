package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.LearningPathItem;

import java.util.List;

public interface LearningPathItemService {

    LearningPathItem createLearningPathItem(LearningPathItem item);

    List<LearningPathItem> getAllLearningPathItems();

    LearningPathItem getLearningPathItemById(Long id);

    List<LearningPathItem> getItemsByLearningPathId(Long learningPathId);

    LearningPathItem updateLearningPathItem(Long id, LearningPathItem itemDetails);

    void deleteLearningPathItem(Long id);
}
