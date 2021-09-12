package com.example.testtask.controllers;

import com.example.testtask.Services.QuestionService;
import com.example.testtask.Services.SurveyService;
import com.example.testtask.entityes.Question;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@Tag(name = "Question controller")
@RequestMapping("/questions")
@Tag(name = "Question controller")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    SurveyService surveyService;

    @Operation(summary = "Создание нового вопроса")
    @PostMapping("/create")
    public @ResponseBody
    ResponseEntity<?> createQuestion(@RequestParam(name = "body")@Parameter(description = "Тело вопроса") String body){
        Question question = questionService.createQuestions(body);
        questionService.save(question);
        return  new ResponseEntity<>(questionService.save(question), HttpStatus.OK);

    }
    @Operation(summary = "Поиск вопросов")
    @GetMapping("/search")
    public @ResponseBody
    ResponseEntity<?> getQuestionPage(
            @RequestParam(name = "by",required = false,defaultValue = "id")@Parameter(description = "Параметр, по которому будет отсортирован список, (id || body), по умолчанию - id") String sortBy,
            @RequestParam(name = "order",required = false, defaultValue = "asc")@Parameter(description = "Порядок сортировки") String order,
            @RequestParam(name = "page",required = false,defaultValue = "1")@Parameter(description = "Номер страницы с результатами") Integer page,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10")@Parameter(description = "Количество результатов на странице") Integer pageSize
    ){
        if(!(sortBy.equalsIgnoreCase("id")||sortBy.equalsIgnoreCase("body"))){
            return  new ResponseEntity<String>("Выбран неверный параметр для сортировки. Доступны: id, body",HttpStatus.BAD_REQUEST);
        }
        Sort sort;
            if (order.equals("desc")) sort = Sort.by(sortBy.toLowerCase(Locale.ROOT)).descending();
            else sort = Sort.by(sortBy.toLowerCase()).ascending();
        int pageNumber = (page>0)? page-1 : 0;
        PageRequest request = PageRequest.of(pageNumber,pageSize,sort);
        return new ResponseEntity<>(questionService.findAllByPage(request),HttpStatus.OK);

    }


    @PutMapping("/update")
    @Operation(summary = "Обновление вопроса")
    public @ResponseBody
    ResponseEntity<?> update(@RequestParam (name = "body")@Parameter(description = "Тело вопроса") String body,
                             @RequestParam(name = "id")   @Parameter(description = "Id вопроса, который необходимо обновить") Long id){
        Question forUpdate = questionService.createQuestions(body);

        return questionService.update(forUpdate,id);
    }
    @Operation(summary = "Удаление вопроса`")
    @DeleteMapping("/delete")
    public @ResponseBody
    ResponseEntity<?> delete(@RequestParam(name = "id") @Parameter(description = "Id вопроса, который нужно удалить")Long id){
    return questionService.deleteById(id);
    }


    @Operation(summary = "Поиск по ID")
    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<?> getOne(@Parameter(description = "ID")@PathVariable("id") Long id){
        return  new ResponseEntity<>(questionService.findById(id),HttpStatus.OK);

    }


}
