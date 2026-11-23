package com.vcube.AI_Powered.Student.Learning.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="interview_sessions")
public class InterviewSession {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_id",nullable=false) private User student;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="course_id",nullable=false) private Course course;
 @Lob @Column(columnDefinition="LONGTEXT") private String transcript;
 private int turns; private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public User getStudent(){return student;} public void setStudent(User x){student=x;}
 public Course getCourse(){return course;} public void setCourse(Course x){course=x;}
 public String getTranscript(){return transcript;} public void setTranscript(String x){transcript=x;}
 public int getTurns(){return turns;} public void setTurns(int x){turns=x;}
 public LocalDateTime getCreatedAt(){return createdAt;}
}
