package com.paaras.quizapp.dao;

import com.paaras.quizapp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
