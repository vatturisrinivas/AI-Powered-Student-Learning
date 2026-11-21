package com.vcube.AI_Powered.Student.Learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "learning_roadmaps")
public class LearningRoadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_path_id", nullable = false)
    private LearningPath learningPath;

    @Column(name = "weak_skills", columnDefinition = "TEXT")
    private String weakSkills;

    @Column(name = "roadmap_content", columnDefinition = "LONGTEXT")
    private String roadmapContent;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt = LocalDateTime.now();

    public LearningRoadmap() {
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

    public String getWeakSkills() {
        return weakSkills;
    }

    public void setWeakSkills(String weakSkills) {
        this.weakSkills = weakSkills;
    }

    public String getRoadmapContent() {
        return roadmapContent;
    }

    public void setRoadmapContent(String roadmapContent) {
        this.roadmapContent = roadmapContent;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}
