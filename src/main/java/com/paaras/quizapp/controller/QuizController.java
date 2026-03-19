package com.paaras.quizapp.controller;

import com.paaras.quizapp.entity.QuestionWrapper;
import com.paaras.quizapp.entity.Quiz;
import com.paaras.quizapp.entity.QuizResponse;
import com.paaras.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @GetMapping("allQuiz")
    public ResponseEntity<List<Quiz>> getAllQuizes(){
        return quizService.getAllQuizes();
    }

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQs, @RequestParam String title){
            return quizService.createQuiz(category, numQs, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<QuizResponse> quizResponse){
        return quizService.getQuizScore(id, quizResponse);
    }
}
