package com.vcube.AI_Powered.Student.Learning.service;

import com.vcube.AI_Powered.Student.Learning.entity.Question;

import java.util.List;

public interface QuestionService {

    Question createQuestion(Question question);

    List<Question> getAllQuestions();

    Question getQuestionById(Long id);

    List<Question> getQuestionsBySkillTag(String skillTag);

    Question updateQuestion(Long id, Question questionDetails);

    void deleteQuestion(Long id);
}
