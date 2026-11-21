package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.CourseTopic;
import com.vcube.AI_Powered.Student.Learning.repository.CourseTopicRepository;
import com.vcube.AI_Powered.Student.Learning.service.CourseTopicService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseTopicServiceImpl implements CourseTopicService {

    private final CourseTopicRepository courseTopicRepository;

    public CourseTopicServiceImpl(CourseTopicRepository courseTopicRepository) {
        this.courseTopicRepository = courseTopicRepository;
    }

    @Override
    public CourseTopic createCourseTopic(CourseTopic courseTopic) {
        return courseTopicRepository.save(courseTopic);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseTopic> getAllCourseTopics() {
        return courseTopicRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CourseTopic getCourseTopicById(Long id) {
        return courseTopicRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Course topic not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseTopic> getCourseTopicsByCourseId(Long courseId) {
        return courseTopicRepository.findByCourseIdOrderByPositionAsc(courseId);
    }

    @Override
    public CourseTopic updateCourseTopic(Long id, CourseTopic topicDetails) {
        CourseTopic existingTopic = getCourseTopicById(id);

        existingTopic.setCourse(topicDetails.getCourse());
        existingTopic.setTitle(topicDetails.getTitle());
        existingTopic.setContent(topicDetails.getContent());
        existingTopic.setPosition(topicDetails.getPosition());

        return courseTopicRepository.save(existingTopic);
    }

    @Override
    public void deleteCourseTopic(Long id) {
        CourseTopic existingTopic = getCourseTopicById(id);
        courseTopicRepository.delete(existingTopic);
    }
}
