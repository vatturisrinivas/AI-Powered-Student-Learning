
package com.vcube.AI_Powered.Student.Learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "learning_path_items")
public class LearningPathItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_path_id", nullable = false)
    private LearningPath learningPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_topic_id", nullable = true)
    private CourseTopic courseTopic;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "position_index")
    private Integer position;

    @Column(name = "is_completed")
    private Boolean completed;

    public LearningPathItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LearningPath getLearningPath() {
        return learningPath;
    }

    public void setLearningPath(LearningPath learningPath) {
        this.learningPath = learningPath;
    }

    public CourseTopic getCourseTopic() {
        return courseTopic;
    }

    public void setCourseTopic(CourseTopic courseTopic) {
        this.courseTopic = courseTopic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
