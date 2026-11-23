package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long>{ java.util.List<AssignmentSubmission> findByStudentId(Long id); java.util.List<AssignmentSubmission> findByAssignmentId(Long id); java.util.Optional<AssignmentSubmission> findByAssignmentIdAndStudentId(Long a,Long s); }