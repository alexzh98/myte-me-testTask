package com.example.testtask.controllers;

import com.example.testtask.Services.QuestionService;
import com.example.testtask.Services.SurveyService;
import com.example.testtask.entityes.Survey;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "Survey Controller")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;
@Autowired
    private QuestionService questionService;

    @Operation(summary = "Создание нового опроса")
    @PostMapping("/surveys/create")
    public @ResponseBody
    ResponseEntity<?> createSurvey(@RequestParam("title") @Parameter(description = "Название опроса") String title,
                                   @RequestParam("start") @Parameter(description = "Дата начала опроса в формате yyyy-MM-dd)") String startDate,
                                   @RequestParam("end") @Parameter(description = "Дата окончания опроса в формате yyyy-MM-dd") String endDate,
                                   @RequestParam(name = "active", required = false, defaultValue = "true") @Parameter(description = "Активен или нет (true || false)") boolean isActive,
                                   @RequestParam("questionid") @Parameter(description = "Список Id вопросов, через запятую") List<Long> questionId) {

        try {
            LocalDate datachek = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate datachek1 = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return new ResponseEntity<>("Неверный формат даты!", HttpStatus.BAD_REQUEST);
        }



        Survey survey = surveyService.createSurvey(title, startDate, endDate, isActive, questionId);
        return new ResponseEntity<>(surveyService.saveSurvey(survey), HttpStatus.OK);
    }


    @Operation(summary = "Поиск опросов")
    @GetMapping("/surveys/search")
    public @ResponseBody
    ResponseEntity<?> getPageSurveys(
            @RequestParam(name = "by", required = false, defaultValue = "id") @Parameter(description = "Параметр, по которому будет отсортирован список" +
                    "(title, start, end, id)") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "asc") @Parameter(description = "Порядок сортировки (asc || desc)") String order,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Parameter(description = "Номер страницы с результатами") Integer page,
            @RequestParam(name = "pagesize", required = false, defaultValue = "10") @Parameter(description = "Количество результатов на странице") Integer pageSize
    ) {

        Set<String> availableParams = Set.of("title", "id", "start", "end");
        if (!availableParams.contains(sortBy)) {
            return new ResponseEntity<String>("Был выбран неверный параметр для сортировки. Доступные параметры :" +
                    "title,id,end,start", HttpStatus.BAD_REQUEST);
        }

        Sort sort;
        if (order.equals("desc")) sort = Sort.by(sortBy).descending();
        else sort = Sort.by(sortBy).ascending();
        int pageNumber = (page > 0) ? page - 1 : 0;
        PageRequest request = PageRequest.of(pageNumber, pageSize, sort);
        return surveyService.findAllByPage(request);
    }


    @Operation(summary = "Обновление опроса")
    @PostMapping("/surveys/update")
    public @ResponseBody
    ResponseEntity<?> updateSurveys(@RequestParam(name = "title", required = false) @Parameter(description = "Новое название опроса  ") String title,
                                    @RequestParam(name = "start", required = false) @Parameter(description = "Новая дата начала опроса в формате yyyy-MM-dd") String startDate,
                                    @RequestParam(name = "end", required = false) @Parameter(description = "Новая дата окончания опроса в формате yyyy-MM-dd") String endDate,
                                    @RequestParam(name = "active", required = false, defaultValue = "true") @Parameter(description = "Активность (false || true)") boolean isActive,
                                    @RequestParam(name = "questionid") @Parameter(description = "Новый список вопросов") List<Long> questionId,
                                    @RequestParam("id") @Parameter(description = "Id опроса, который необходимо обновить") Long id) {

        try {
            LocalDate datachek = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate datachek1 = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return new ResponseEntity<>("Неверный формат даты!", HttpStatus.BAD_REQUEST);
        }


        Survey survey = surveyService.createSurvey(title, startDate, endDate, isActive, questionId);
        return surveyService.update(survey, id);
    }


    @DeleteMapping("/surveys/delete")
    @Operation(summary = "Удаление опроса")
    public ResponseEntity<?> deleteSurvey(@RequestParam(name = "id", defaultValue = "-1") @Parameter(description = "id") Long id) {

        if (id < 0) return new ResponseEntity<String>("Не указан id", HttpStatus.BAD_REQUEST);

        if (surveyService.delete(id))
            return new ResponseEntity<>("Успешно", HttpStatus.OK);

        else return new ResponseEntity<>("При удалении произошла ошибка", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/surveys/{id}")
    @Operation(summary = "Поиск по Id")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") @Parameter(description = "ID") Long id) {
        return new ResponseEntity(surveyService.getOne(id), HttpStatus.OK);
    }
}
