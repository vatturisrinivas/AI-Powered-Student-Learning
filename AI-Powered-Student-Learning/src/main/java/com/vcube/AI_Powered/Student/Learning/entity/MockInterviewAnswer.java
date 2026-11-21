package com.vcube.AI_Powered.Student.Learning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mock_interview_answers")
public class MockInterviewAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_interview_id", nullable = false)
    private MockInterview mockInterview;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "student_answer", columnDefinition = "LONGTEXT")
    private String studentAnswer;

    @Column(name = "ai_feedback", columnDefinition = "LONGTEXT")
    private String aiFeedback;

    @Column()
    private Double score;

    @Column(name = "position_index")
    private Integer position;

    public MockInterviewAnswer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MockInterview getMockInterview() {
        return mockInterview;
    }

    public void setMockInterview(MockInterview mockInterview) {
        this.mockInterview = mockInterview;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getAiFeedback() {
        return aiFeedback;
    }

    public void setAiFeedback(String aiFeedback) {
        this.aiFeedback = aiFeedback;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
