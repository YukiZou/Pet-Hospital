package com.ecnu.dao;

import com.ecnu.entity.Question;
import com.ecnu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao {

    /**
     * 根据参数usr查询出所有符合查询条件的user用户,可以实现模糊查找
     * @param question
     * @return
     */
    List<Question> queryQuestions(Question question);

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
}
