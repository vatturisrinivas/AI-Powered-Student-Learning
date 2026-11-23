package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.Course;

import java.util.List;

public interface CourseService {

    Course createCourse(Course course);

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    List<Course> getCoursesByDomainId(Long domainId);

    List<Course> getCoursesByTrainerId(Long trainerId);

    List<Course> searchCoursesByTitle(String title);

    Course updateCourse(Long id, Course courseDetails);

    void deleteCourse(Long id);
}
