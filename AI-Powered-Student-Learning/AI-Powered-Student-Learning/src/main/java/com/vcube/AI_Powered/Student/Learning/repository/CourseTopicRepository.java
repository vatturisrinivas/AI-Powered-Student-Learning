
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.CourseTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseTopicRepository
        extends JpaRepository<CourseTopic, Long> {

    List<CourseTopic> findByCourseIdOrderByPositionAsc(
            Long courseId);
}

