package com.vcube.AI_Powered.Student.Learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vcube.AI_Powered.Student.Learning.entity.Assessment;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByCourse_Id(Long courseId);
}
