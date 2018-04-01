package com.ecnu.service;

import com.ecnu.entity.Question;

import java.util.List;

public interface QuestionService {


    List<Question> getAllQuestionCategory();

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
     */
    Boolean deleteQuestion(int id);

    /**
     * 修改指定试题
     */
 //   Boolean changeQuestion(int id, String category, String stem, String optA, String optB, String optC, String optD, String answer);
    Boolean updateQuestion(Question question);
}
