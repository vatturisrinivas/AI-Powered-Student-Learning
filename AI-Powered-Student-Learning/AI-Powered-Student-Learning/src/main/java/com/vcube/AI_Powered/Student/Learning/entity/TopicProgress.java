package com.vcube.AI_Powered.Student.Learning.entity;
import jakarta.persistence.*;
@Entity @Table(name="student_topic_progress",uniqueConstraints=@UniqueConstraint(columnNames={"student_id","topic_id"}))
public class TopicProgress {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_id",nullable=false) private User student;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="topic_id",nullable=false) private CourseTopic topic;
 public Long getId(){return id;} public User getStudent(){return student;} public void setStudent(User x){student=x;}
 public CourseTopic getTopic(){return topic;} public void setTopic(CourseTopic x){topic=x;}
}
