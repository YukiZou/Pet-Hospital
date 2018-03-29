package com.ecnu.service;

import com.ecnu.entity.Question;

import java.util.List;

public interface QuestionService {

    /**
     * 根据参数question的数据来查找符合条件的question记录
     * @param question
     * @return
     */
    List<Question> queryQuestions(Question question);
    /**
     * 新增试题
     * @param question
     */
    Boolean addQuestion(Question question);

    /**
     * 删除试题
     * @param id
     * @return
     */
    Boolean deleteQuestion(int id);

}
