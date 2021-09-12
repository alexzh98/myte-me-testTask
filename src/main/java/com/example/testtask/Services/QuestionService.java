package com.example.testtask.Services;

import com.example.testtask.entityes.Question;
import com.example.testtask.entityes.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {


    Question createQuestions(String body);
    Question findById(Long id);
    Page<Question> findAllByPage(Pageable pageble);
    ResponseEntity<?> update(Question question,Long id);
    Question save(Question question);
    ResponseEntity deleteById(Long id);

}
