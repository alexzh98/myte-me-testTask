package com.example.testtask.Services;

import com.example.testtask.Repository.SurveyRepository;
import com.example.testtask.entityes.Question;
import com.example.testtask.entityes.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SurveyServiceImpl implements SurveyService{
    @Autowired
    private SurveyRepository repository;
    @Autowired
    private QuestionService service;



    @Override
    public ResponseEntity<?> saveSurvey(Survey surveyEntity) {
        LocalDate start = surveyEntity.getStart();
        LocalDate end = surveyEntity.getEnd();
        if(end.isBefore(start)){return  new ResponseEntity<String>("Ошибка в указании даты. Конечная дата ранее начальной",HttpStatus.BAD_REQUEST);}
       return new ResponseEntity<>(repository.save(surveyEntity),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findAllByPage(Pageable pageable) {
        return new ResponseEntity<>(repository.findAll(pageable),HttpStatus.OK);
    }

    @Override
    public Survey createSurvey(String title, String dateStart, String dateEnd, boolean isActive, List<Long>questions) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Survey survey  = new Survey();
        survey.setActive(isActive);
        survey.setTitle(title);
            survey.setStart(LocalDate.parse(dateStart, df));
            survey.setEnd(LocalDate.parse(dateEnd, df));

        List<Question> list = new ArrayList<>();
            questions.forEach(o->{
                list.add(service.findById(o));
            });
            survey.setQuestions(list);
           return survey;
    }

    @Override
    public Page<Question> getQuestionsFromSurvey(Survey survey) {
        return null;
    }


    @Override
    public ResponseEntity<?> update(Survey forUpdate, Long id) {
        Optional<Survey> opt = repository.findById(id);
        if(opt.isEmpty())return new ResponseEntity<>("По указанному ID нечего обновлять!",HttpStatus.BAD_REQUEST);
        Survey survey = opt.get();
        survey.setActive(forUpdate.isActive());
        survey.setQuestions(forUpdate.getQuestions());
        survey.setTitle(forUpdate.getTitle());
        survey.setStart(forUpdate.getStart());
        survey.setEnd(forUpdate.getEnd());
        repository.save(survey);
        return new ResponseEntity<>("Обновление прошло успешно!",HttpStatus.OK);
    }

    @Override
    public boolean delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (Exception e){return  false;}

        return true;
    }


    public Survey getOne(Long id){
    if(repository.findById(id).isPresent())
        return  repository.findById(id).get();
    return null;
    }


}
