package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.Course;
import com.vcube.AI_Powered.Student.Learning.repository.CourseRepository;
import com.vcube.AI_Powered.Student.Learning.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Course not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesByDomainId(Long domainId) {
        return courseRepository.findByDomainId(domainId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesByTrainerId(Long trainerId) {
        return courseRepository.findByTrainerId(trainerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Course existingCourse = getCourseById(id);

        existingCourse.setDomain(courseDetails.getDomain());
        existingCourse.setTrainer(courseDetails.getTrainer());
        existingCourse.setTitle(courseDetails.getTitle());
        existingCourse.setDescription(courseDetails.getDescription());
        existingCourse.setLevel(courseDetails.getLevel());

        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        Course existingCourse = getCourseById(id);
        courseRepository.delete(existingCourse);
    }
}
