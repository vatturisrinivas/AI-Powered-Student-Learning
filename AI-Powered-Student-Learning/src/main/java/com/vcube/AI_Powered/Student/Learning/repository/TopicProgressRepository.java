package com.vcube.AI_Powered.Student.Learning.repository;
import com.vcube.AI_Powered.Student.Learning.entity.TopicProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TopicProgressRepository extends JpaRepository<TopicProgress,Long>{
 List<TopicProgress> findByStudentId(Long id);
 boolean existsByStudentIdAndTopicId(Long studentId,Long topicId);
}
