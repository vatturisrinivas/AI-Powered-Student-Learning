package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.MockInterviewAnswer;
import com.vcube.AI_Powered.Student.Learning.repository.MockInterviewAnswerRepository;
import com.vcube.AI_Powered.Student.Learning.service.MockInterviewAnswerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MockInterviewAnswerServiceImpl
        implements MockInterviewAnswerService {

    private final MockInterviewAnswerRepository answerRepository;

    public MockInterviewAnswerServiceImpl(
            MockInterviewAnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public MockInterviewAnswer createAnswer(MockInterviewAnswer answer) {
        return answerRepository.save(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockInterviewAnswer> getAllAnswers() {
        return answerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public MockInterviewAnswer getAnswerById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Mock interview answer not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockInterviewAnswer> getAnswersByMockInterviewId(
            Long mockInterviewId) {
        return answerRepository
                .findByMockInterviewIdOrderByPositionAsc(mockInterviewId);
    }

    @Override
    public MockInterviewAnswer updateAnswer(
            Long id, MockInterviewAnswer answerDetails) {
        MockInterviewAnswer existingAnswer = getAnswerById(id);

        existingAnswer.setMockInterview(answerDetails.getMockInterview());
        existingAnswer.setQuestionText(answerDetails.getQuestionText());
        existingAnswer.setStudentAnswer(answerDetails.getStudentAnswer());
        existingAnswer.setAiFeedback(answerDetails.getAiFeedback());
        existingAnswer.setScore(answerDetails.getScore());
        existingAnswer.setPosition(answerDetails.getPosition());

        return answerRepository.save(existingAnswer);
    }

    @Override
    public void deleteAnswer(Long id) {
        MockInterviewAnswer existingAnswer = getAnswerById(id);
        answerRepository.delete(existingAnswer);
    }
}
