package com.vcube.AI_Powered.Student.Learning.repository;
import com.vcube.AI_Powered.Student.Learning.entity.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface InterviewSessionRepository extends JpaRepository<InterviewSession,Long>{List<InterviewSession> findByStudentId(Long id);}
