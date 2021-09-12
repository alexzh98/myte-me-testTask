package com.example.testtask.Repository;

import com.example.testtask.entityes.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface QuestionRepository extends PagingAndSortingRepository<Question,Long> {

    @Override
    Optional<Question> findById(Long aLong);
}
