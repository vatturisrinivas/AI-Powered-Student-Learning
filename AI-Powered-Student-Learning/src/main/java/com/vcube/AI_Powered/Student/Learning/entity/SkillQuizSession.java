package com.vcube.AI_Powered.Student.Learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="skill_quiz_sessions")
public class SkillQuizSession {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="course_id",nullable=false) private Course course;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_id",nullable=false) private User student;
 @Column(columnDefinition="LONGTEXT",nullable=false) private String questionsJson;
 private Integer score;
 private Integer total;
 @Column(nullable=false) private boolean completed=false;
 private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public void setId(Long x){id=x;}
 public Course getCourse(){return course;} public void setCourse(Course x){course=x;}
 public User getStudent(){return student;} public void setStudent(User x){student=x;}
 public String getQuestionsJson(){return questionsJson;} public void setQuestionsJson(String x){questionsJson=x;}
 public Integer getScore(){return score;} public void setScore(Integer x){score=x;}
 public Integer getTotal(){return total;} public void setTotal(Integer x){total=x;}
 public boolean isCompleted(){return completed;} public void setCompleted(boolean x){completed=x;}
 public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime x){createdAt=x;}
}