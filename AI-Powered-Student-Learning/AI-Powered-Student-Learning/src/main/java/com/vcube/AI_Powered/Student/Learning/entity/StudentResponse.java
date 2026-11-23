package com.vcube.AI_Powered.Student.Learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_responses", uniqueConstraints = @UniqueConstraint(columnNames = {"attempt_id", "question_id"}))
public class StudentResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private AssessmentAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "selected_option", length = 1)
    private String selectedOption;

    @Column(name = "is_correct")
    private Boolean correct;

    @Column(name = "marks_awarded")
    private Integer marksAwarded;

    public StudentResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssessmentAttempt getAttempt() {
        return attempt;
    }

    public void setAttempt(AssessmentAttempt attempt) {
        this.attempt = attempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Integer getMarksAwarded() {
        return marksAwarded;
    }

    public void setMarksAwarded(Integer marksAwarded) {
        this.marksAwarded = marksAwarded;
    }
}