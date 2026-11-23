
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByDomainId(Long domainId);

    List<Course> findByTrainerId(Long trainerId);

    List<Course> findByTitleContainingIgnoreCase(String title);
}
