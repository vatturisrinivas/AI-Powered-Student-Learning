package com.vcube.AI_Powered.Student.Learning.service.impl;

import com.vcube.AI_Powered.Student.Learning.entity.StudentResponse;
import com.vcube.AI_Powered.Student.Learning.repository.StudentResponseRepository;
import com.vcube.AI_Powered.Student.Learning.service.StudentResponseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentResponseServiceImpl implements StudentResponseService {

    private final StudentResponseRepository responseRepository;

    public StudentResponseServiceImpl(
            StudentResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public StudentResponse createStudentResponse(StudentResponse response) {
        return responseRepository.save(response);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudentResponses() {
        return responseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentResponseById(Long id) {
        return responseRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Student response not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getResponsesByAttemptId(Long attemptId) {
        return responseRepository.findByAttemptId(attemptId);
    }

    @Override
    public StudentResponse updateStudentResponse(
            Long id, StudentResponse responseDetails) {
        StudentResponse existingResponse = getStudentResponseById(id);

        existingResponse.setAttempt(responseDetails.getAttempt());
        existingResponse.setQuestion(responseDetails.getQuestion());
        existingResponse.setSelectedOption(responseDetails.getSelectedOption());
        existingResponse.setCorrect(responseDetails.getCorrect());
        existingResponse.setMarksAwarded(responseDetails.getMarksAwarded());

        return responseRepository.save(existingResponse);
    }

    @Override
    public void deleteStudentResponse(Long id) {
        StudentResponse existingResponse = getStudentResponseById(id);
        responseRepository.delete(existingResponse);
    }
}
