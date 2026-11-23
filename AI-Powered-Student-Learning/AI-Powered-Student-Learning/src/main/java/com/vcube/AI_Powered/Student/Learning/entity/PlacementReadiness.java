
package com.vcube.AI_Powered.Student.Learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "placement_readiness")
public class PlacementReadiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_role_id", nullable = true)
    private CareerRole careerRole;

    @Column(name = "assessment_score")
    private Double assessmentScore;

    @Column(name = "interview_score")
    private Double interviewScore;

    @Column(name = "readiness_score")
    private Double readinessScore;

    @Column(columnDefinition = "LONGTEXT")
    private String recommendations;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public PlacementReadiness() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public CareerRole getCareerRole() {
        return careerRole;
    }

    public void setCareerRole(CareerRole careerRole) {
        this.careerRole = careerRole;
    }

    public Double getAssessmentScore() {
        return assessmentScore;
    }

    public void setAssessmentScore(Double assessmentScore) {
        this.assessmentScore = assessmentScore;
    }

    public Double getInterviewScore() {
        return interviewScore;
    }

    public void setInterviewScore(Double interviewScore) {
        this.interviewScore = interviewScore;
    }

    public Double getReadinessScore() {
        return readinessScore;
    }

    public void setReadinessScore(Double readinessScore) {
        this.readinessScore = readinessScore;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
