package com.paaras.quizapp.service;


import com.paaras.quizapp.dao.QuestionDao;
import com.paaras.quizapp.dao.QuizDao;
import com.paaras.quizapp.entity.Question;
import com.paaras.quizapp.entity.QuestionWrapper;
import com.paaras.quizapp.entity.Quiz;
import com.paaras.quizapp.entity.QuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQs, String title){
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQs);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Added quiz", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Quiz>> getAllQuizes() {
        List<Quiz> quizzes = quizDao.findAll();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> q = quizDao.findById(id);
        if(q.isPresent()){
            List<Question> questions = q.get().getQuestions();
            List<QuestionWrapper> res = new ArrayList<>();
            for(Question curr : questions){
                QuestionWrapper QW = new QuestionWrapper(curr.getId(), curr.getQuestionTitle(), curr.getOption1(), curr.getOption2(), curr.getOption3(), curr.getOption4());
                res.add(QW);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


//        return new ResponseEntity<>(quizDao.findById(id), HttpStatus.);
    }

    public ResponseEntity<Integer> getQuizScore(int id, List<QuizResponse> quizResponse) {
        Optional<Quiz> q = quizDao.findById(id);
        List<Question> questionWithAnswer = q.get().getQuestions();
        int score = 0;
        for(QuizResponse curr : quizResponse){
            Question que = questionDao.findById(curr.getId()).get();
            if(que.getId()==curr.getId() && que.getRightAnswer().equalsIgnoreCase(curr.getResponse())){
                score++;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
