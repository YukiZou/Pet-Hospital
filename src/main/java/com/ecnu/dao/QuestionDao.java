package com.ecnu.dao;

import com.ecnu.entity.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author asus
 */
@Repository
public interface QuestionDao {

    /**
     * 根据参数question查询出所有符合查询条件的question
     * @param question
     * @return
     */
    List<Question> queryQuestions(Question question);

    /**
     * 根据参数question 的参数来匹配寻找Question 记录
     * @param question
     * @return
     */
    List<Question> queryQuestionsByQues(Question question);

    /**
     * 列出所有category
     * @param
     * @return
     */
    List<Question> getAllQuestionCategory();

    /**
     * 根据ID找到Question记录
     * @param id
     * @return
     */
    Question findQuestionById (int id);

    /**
     * 根据Stem找到Question记录
     * @param stem
     * @return
     */
    Question findQuestionByStem (String stem);

    /**
     * 新增一个试题
     * @param question
     */
    void insertQuestion(Question question);

    /**
     * 批量新增多个试题
     * @param questions
     * @return
     */
    int insertQuestions(List<Question> questions);

    /**
     * 根据参数question删除指定试题记录。
     * @param question
     * @return
     */
    int deleteQuestion(Question question);

    /**
     * 修改试题
     * @param question
     * @return
     */
    int updateQuestion(Question question);
}
