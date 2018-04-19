package com.ecnu.service;

import com.ecnu.entity.Question;

import java.util.List;

/**
 * @author asus
 */
public interface QuestionService {
    /**
     * 拿到所有的问题种类
     * @return
     */
    List<Question> getAllQuestionCategory();

    /**
     * 根据参数question的数据来查找符合条件的question记录
     * @param question
     * @return
     */
    List<Question> queryQuestions(Question question);

    /**
     * 根据参数匹配查询所有的问题
     * @param question
     * @return
     */
    List<Question> queryQuestionsByQues(Question question);

    /**
     * 新增试题
     * @param question
     * @return
     */
    Boolean addQuestion(Question question);

    /**
     * 批量新增试题，参数List != null 且不为空
     * @param questions
     * @return
     */
    int addQuestions(List<Question> questions);

    /**
     * 删除试题
     * @param id
     * @return
     */
    Boolean deleteQuestion(int id);

    /**
     * 修改指定试题
     * @param question
     * @return
     */
    Boolean updateQuestion(Question question);
}
