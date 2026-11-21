
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.LearningPathItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LearningPathItemRepository
        extends JpaRepository<LearningPathItem, Long> {

    List<LearningPathItem> findByLearningPathIdOrderByPositionAsc(
            Long learningPathId);
}
