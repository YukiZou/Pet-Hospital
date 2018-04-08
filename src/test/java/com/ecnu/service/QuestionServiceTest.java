package com.ecnu.service;

import com.ecnu.entity.Question;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionServiceTest extends BaseServiceTest {

    @Resource
    private QuestionService questionService;

    @Test
    public void getAllQuestionCategory() {
        assertTrue(questionService.getAllQuestionCategory().size() > 0);
    }

    @Test
    public void queryQuestions() {
        Question question = new Question();
        question.setCategory("传染病");
        question.setStem("testStem");
        question.setOptA("A");
        question.setOptB("B");
        question.setOptC("C");
        question.setOptD("D");
        question.setAnswer("A");
        assertTrue(questionService.addQuestion(question));
        int id=question.getId();
        Question queryQuestion = new Question();
        queryQuestion.setStem("testStem");
        List<Question> questionList = questionService.queryQuestions(question);
        for(Question q:questionList){
            assertTrue(q.getStem().contains("testStem"));
        }
        assertTrue(questionService.deleteQuestion(id));
    }

    @Test
    public void queryQuestionsByQues() {
        Question question = new Question();
        assertTrue(questionService.queryQuestionsByQues(question).size() > 0);
        question.setAnswer("D");
        question.setCategory("猫科");
        int res = questionService.queryQuestionsByQues(question).size();
        assertTrue(questionService.queryQuestionsByQues(question).size() == 1);
    }

    @Test
    public void addQuestion() {
        Question question = new Question();
        question.setCategory("传染病");
        question.setStem("testStem");
        question.setOptA("A");
        question.setOptB("B");
        question.setOptC("C");
        question.setOptD("D");
        assertFalse(questionService.addQuestion(question));
        question.setAnswer("A");
        assertTrue(questionService.addQuestion(question));
        int id=question.getId();

        //不能重复添加
        assertFalse(questionService.addQuestion(question));
        assertTrue(questionService.deleteQuestion(id));

    }

    @Test
    public void addQuestions() {
        List<Question> questionList = new ArrayList<>();
        assertEquals(questionService.addQuestions(questionList), 0);
        Question question = new Question();
        question.setCategory("传染病");
        question.setStem("testStem");
        question.setOptA("A");
        question.setOptB("B");
        question.setOptC("C");
        question.setOptD("D");
        question.setAnswer("A");
        questionList.add(question);
        assertEquals(questionService.addQuestions(questionList), 1);
        assertTrue(questionService.deleteQuestion(question.getId()));
    }

    @Test
    public void deleteQuestion() {
        Question question = new Question();
        question.setCategory("传染病");
        question.setStem("testStem");
        question.setOptA("A");
        question.setOptB("B");
        question.setOptC("C");
        question.setOptD("D");
        question.setAnswer("A");
        assertTrue(questionService.addQuestion(question));
        int id=question.getId();
        assertTrue(questionService.deleteQuestion(id));
        assertFalse(questionService.deleteQuestion(-1));
    }

    @Test
    public void updateQuestion() {
        Question question = new Question();
        question.setCategory("传染病");
        question.setStem("testStem");
        question.setOptA("A");
        question.setOptB("B");
        question.setOptC("C");
        question.setOptD("D");
        question.setAnswer("A");
        assertTrue(questionService.addQuestion(question));
        int id=question.getId();
        Question updateQuestion = new Question();
        updateQuestion.setId(-1);
        assertFalse(questionService.updateQuestion(updateQuestion));
        updateQuestion.setId(id);
        updateQuestion.setCategory("内科");
        updateQuestion.setStem("testStem");
        updateQuestion.setOptA("A");
        updateQuestion.setOptB("B");
        updateQuestion.setOptC("CCCC");
        assertFalse(questionService.updateQuestion(updateQuestion));
        updateQuestion.setOptD("D");
        updateQuestion.setAnswer("A");
        assertTrue(questionService.updateQuestion(updateQuestion));
        assertTrue(questionService.deleteQuestion(id));
    }
}