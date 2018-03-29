package com.ecnu.dao;

import com.ecnu.entity.Question;
import com.ecnu.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDao {

    /**
     * 新增一个试题
     * @param question
     */
    void insertQuestion(Question question);
}
