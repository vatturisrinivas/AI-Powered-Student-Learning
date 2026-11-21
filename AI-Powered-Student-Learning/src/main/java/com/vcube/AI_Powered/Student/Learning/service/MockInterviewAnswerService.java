package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.MockInterviewAnswer;

import java.util.List;

public interface MockInterviewAnswerService {

    MockInterviewAnswer createAnswer(MockInterviewAnswer answer);

    List<MockInterviewAnswer> getAllAnswers();

    MockInterviewAnswer getAnswerById(Long id);

    List<MockInterviewAnswer> getAnswersByMockInterviewId(Long mockInterviewId);

    MockInterviewAnswer updateAnswer(Long id, MockInterviewAnswer answerDetails);

    void deleteAnswer(Long id);
}
