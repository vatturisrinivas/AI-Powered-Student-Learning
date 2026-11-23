package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.LearningPath;

import java.util.List;

public interface LearningPathService {

    LearningPath createLearningPath(LearningPath learningPath);

    List<LearningPath> getAllLearningPaths();

    LearningPath getLearningPathById(Long id);

    List<LearningPath> getLearningPathsByStudentId(Long studentId);

    LearningPath updateLearningPath(Long id, LearningPath pathDetails);

    void deleteLearningPath(Long id);
}
