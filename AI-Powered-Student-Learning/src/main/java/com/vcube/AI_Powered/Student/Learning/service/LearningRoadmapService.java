package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.LearningRoadmap;

import java.util.List;

public interface LearningRoadmapService {

    LearningRoadmap createLearningRoadmap(LearningRoadmap roadmap);

    List<LearningRoadmap> getAllLearningRoadmaps();

    LearningRoadmap getLearningRoadmapById(Long id);

    List<LearningRoadmap> getRoadmapsByLearningPathId(Long learningPathId);

    LearningRoadmap updateLearningRoadmap(Long id, LearningRoadmap roadmapDetails);

    void deleteLearningRoadmap(Long id);
}
