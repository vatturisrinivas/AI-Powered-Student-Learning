
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.LearningRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LearningRoadmapRepository
        extends JpaRepository<LearningRoadmap, Long> {

    List<LearningRoadmap> findByLearningPathId(Long learningPathId);
}
