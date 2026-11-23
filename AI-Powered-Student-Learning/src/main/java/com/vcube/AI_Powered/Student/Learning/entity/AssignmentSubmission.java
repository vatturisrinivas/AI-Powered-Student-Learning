package com.vcube.AI_Powered.Student.Learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="assignment_submissions",uniqueConstraints=@UniqueConstraint(columnNames={"assignment_id","student_id"}))
public class AssignmentSubmission {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="assignment_id",nullable=false) private Assignment assignment;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_id",nullable=false) private User student;
 @Column(columnDefinition="TEXT",nullable=false) private String answerText;
 private Integer score;
 @Column(columnDefinition="TEXT") private String feedback;
 @Column(nullable=false,length=20) private String status="SUBMITTED";
 private LocalDateTime submittedAt=LocalDateTime.now();
 public Long getId(){return id;} public void setId(Long x){id=x;}
 public Assignment getAssignment(){return assignment;} public void setAssignment(Assignment x){assignment=x;}
 public User getStudent(){return student;} public void setStudent(User x){student=x;}
 public String getAnswerText(){return answerText;} public void setAnswerText(String x){answerText=x;}
 public Integer getScore(){return score;} public void setScore(Integer x){score=x;}
 public String getFeedback(){return feedback;} public void setFeedback(String x){feedback=x;}
 public String getStatus(){return status;} public void setStatus(String x){status=x;}
 public LocalDateTime getSubmittedAt(){return submittedAt;} public void setSubmittedAt(LocalDateTime x){submittedAt=x;}
}