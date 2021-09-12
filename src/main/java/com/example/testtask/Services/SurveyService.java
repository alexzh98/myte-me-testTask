package com.example.testtask.Services;


import com.example.testtask.entityes.Question;
import com.example.testtask.entityes.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SurveyService {


    ResponseEntity<?> saveSurvey(Survey surveyEntity);
    Page<Question> getQuestionsFromSurvey(Survey survey);
    ResponseEntity findAllByPage(Pageable pageable);
    ResponseEntity<?> update(Survey survey, Long id);
    Survey createSurvey(String title, String dateStart, String dateEnd, boolean isActive, List<Long> questions);
    boolean delete(Long id);
    Survey getOne(Long id);
}
