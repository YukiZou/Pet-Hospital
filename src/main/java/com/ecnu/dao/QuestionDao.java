package com.ecnu.dao;

import com.ecnu.entity.Question;
import com.ecnu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao {

    /**
     * 根据参数question查询出所有符合查询条件的question
     * @param question
     * @return
     */
    List<Question> queryQuestions(Question question);

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
     * 根据参数question删除指定试题记录。
     * @param question
     */
    int deleteQuestion(Question question);

    /**
     * 修改试题
     * @param question
     */
    int updateQuestion(Question question);
}
