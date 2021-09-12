package com.example.testtask.entityes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "body")
    private String body;
    @ManyToMany(mappedBy = "questions")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private List<Survey> surveyEntities = new ArrayList<>();

}
