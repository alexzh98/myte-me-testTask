package com.example.testtask.Services;

import com.example.testtask.Repository.QuestionRepository;
import com.example.testtask.entityes.Question;
import com.example.testtask.entityes.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository repository;

    @Override
    public Question createQuestions(String body) {
        Question question = new Question();
        question.setBody(body);
        return question;

    }

    @Override
    public ResponseEntity<?> update(Question questionForUpdate,Long id) {

    Optional<Question> opt  = repository.findById(id);
    if (opt.isEmpty()){return new ResponseEntity<String>("По указанному Id не нашлось вопросов!",HttpStatus.BAD_REQUEST);}
    Question question = opt.get();
    if (questionForUpdate.getBody()!=null) question.setBody(questionForUpdate.getBody());
    return new ResponseEntity<>(repository.save(question),HttpStatus.OK);
    }


    @Override
    public Question findById(Long id) {
        Question question = null;
        Optional<Question> opt = repository.findById(id);
        if(opt.isPresent())question=opt.get();
       return question;

    }

    @Override
    public Page<Question> findAllByPage(Pageable pageble) {
        return repository.findAll(pageble);
    }

    @Override
    public Question save(Question question) {
        return  repository.save(question);
    }

    @Override
    public ResponseEntity deleteById(Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity("Успешно",HttpStatus.OK);
        }catch (Exception e){return new ResponseEntity("При удалении произошла ошибка",HttpStatus.BAD_REQUEST);}
    }

}
