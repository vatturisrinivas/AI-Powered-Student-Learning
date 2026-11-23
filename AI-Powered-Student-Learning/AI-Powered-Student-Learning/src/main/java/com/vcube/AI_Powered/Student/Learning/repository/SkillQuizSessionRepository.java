package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.SkillQuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SkillQuizSessionRepository extends JpaRepository<SkillQuizSession,Long>{ java.util.List<SkillQuizSession> findByStudentId(Long id); }