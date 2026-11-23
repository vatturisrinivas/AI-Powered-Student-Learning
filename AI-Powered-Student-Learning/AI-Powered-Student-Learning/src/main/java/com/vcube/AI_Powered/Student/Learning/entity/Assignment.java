package com.vcube.AI_Powered.Student.Learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="assignments")
public class Assignment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="course_id",nullable=false) private Course course;
 @Column(nullable=false,length=160) private String title;
 @Column(columnDefinition="TEXT") private String description;
 @Column(nullable=false) private Integer maxScore=100;
 private LocalDateTime dueAt;
 public Long getId(){return id;} public void setId(Long x){id=x;}
 public Course getCourse(){return course;} public void setCourse(Course x){course=x;}
 public String getTitle(){return title;} public void setTitle(String x){title=x;}
 public String getDescription(){return description;} public void setDescription(String x){description=x;}
 public Integer getMaxScore(){return maxScore;} public void setMaxScore(Integer x){maxScore=x;}
 public LocalDateTime getDueAt(){return dueAt;} public void setDueAt(LocalDateTime x){dueAt=x;}
}