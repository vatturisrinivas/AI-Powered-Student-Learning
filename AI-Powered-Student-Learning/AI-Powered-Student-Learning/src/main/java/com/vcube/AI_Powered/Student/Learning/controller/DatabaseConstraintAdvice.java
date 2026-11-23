package com.vcube.AI_Powered.Student.Learning.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class DatabaseConstraintAdvice {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> conflict(DataIntegrityViolationException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
           "message", "This record is referenced by courses, enrollments, assessments, or results and cannot be deleted safely. Remove or reassign dependent records first."
        ));
    }
}
