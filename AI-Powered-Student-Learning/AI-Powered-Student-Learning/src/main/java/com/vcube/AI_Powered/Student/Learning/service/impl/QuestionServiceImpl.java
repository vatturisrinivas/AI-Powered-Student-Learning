package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.Question;
import com.vcube.AI_Powered.Student.Learning.repository.QuestionRepository;
import com.vcube.AI_Powered.Student.Learning.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Question not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestionsBySkillTag(String skillTag) {
        return questionRepository.findBySkillTag(skillTag);
    }

    @Override
    public Question updateQuestion(Long id, Question questionDetails) {
        Question existingQuestion = getQuestionById(id);

        existingQuestion.setQuestionText(questionDetails.getQuestionText());
        existingQuestion.setOptionA(questionDetails.getOptionA());
        existingQuestion.setOptionB(questionDetails.getOptionB());
        existingQuestion.setOptionC(questionDetails.getOptionC());
        existingQuestion.setOptionD(questionDetails.getOptionD());
        existingQuestion.setCorrectOption(questionDetails.getCorrectOption());
        existingQuestion.setSkillTag(questionDetails.getSkillTag());
        existingQuestion.setExplanation(questionDetails.getExplanation());

        return questionRepository.save(existingQuestion);
    }

    @Override
    public void deleteQuestion(Long id) {
        Question existingQuestion = getQuestionById(id);
        questionRepository.delete(existingQuestion);
    }
}
