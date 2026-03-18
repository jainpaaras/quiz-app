package com.paaras.quizapp.service;

import com.paaras.quizapp.dao.QuestionDao;
import com.paaras.quizapp.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions(){
        try{
            return new ResponseEntity<>(questionDao.findAll(Sort.by(Sort.Direction.ASC, "id")), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public List<Question> getQuestionsByCategory(String category){
        return questionDao.findByCategory(category);
    }

    public String addQuestion(Question question){
        questionDao.save(question);
        return "success";
    }

    public String deleteQuestion(Integer id){
        Optional<Question> curr = questionDao.findById(id);
        if (curr.isPresent()){
            questionDao.delete(curr.get());
            return "deleted "+ curr.get().getCategory() + " question with id : " + id;
        }else{
            return "Question Not Found";
        }

    }

    public String updateQuestion(Question question){
        questionDao.save(question);
        return "updated";
    }
}
