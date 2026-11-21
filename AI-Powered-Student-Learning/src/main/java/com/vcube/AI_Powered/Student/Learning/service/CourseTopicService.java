package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.CourseTopic;

import java.util.List;

public interface CourseTopicService {

    CourseTopic createCourseTopic(CourseTopic courseTopic);

    List<CourseTopic> getAllCourseTopics();

    CourseTopic getCourseTopicById(Long id);

    List<CourseTopic> getCourseTopicsByCourseId(Long courseId);

    CourseTopic updateCourseTopic(Long id, CourseTopic topicDetails);

    void deleteCourseTopic(Long id);
}
