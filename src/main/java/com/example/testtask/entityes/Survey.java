package com.example.testtask.entityes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "survey")
@Data
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date")
    private LocalDate start;
    @Column(name = "end_date")
    private LocalDate end;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "title")
    private String title;
    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "surveyslike",
    joinColumns = @JoinColumn(name = "survey_id"),
    inverseJoinColumns = @JoinColumn(name = "question_id"))
    @JsonManagedReference
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question){
        this.questions.add(question);

    }
}

