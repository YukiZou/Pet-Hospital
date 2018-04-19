package com.ecnu.service.impl;

import com.ecnu.dao.QuestionDao;
import com.ecnu.entity.Question;
import com.ecnu.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @author asus
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    private static Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);
    @Autowired
    private QuestionDao questionDao;

    @Override
    public List<Question> getAllQuestionCategory() {
        return questionDao.getAllQuestionCategory();
    }
    @Override
    public List<Question> queryQuestions(Question question) {
        return questionDao.queryQuestions(question);
    }

    @Override
    public List<Question> queryQuestionsByQues(Question question) {
        return questionDao.queryQuestionsByQues(question);
    }

    @Override
    public Boolean addQuestion(Question question) {
        String category=question.getCategory();
        String stem=question.getStem();
        String a=question.getOptA();
        String b=question.getOptB();
        String c=question.getOptC();
        String d=question.getOptD();
        String answer=question.getAnswer();

        //TODO：新增试题的题干及选项的长度限制
        Boolean isIllegal = stem == null || "".equals(stem) ||category == null || "".equals(category) || a == null || "".equals(a) ||
                b == null || "".equals(b) || c == null || "".equals(c) ||
                d == null || "".equals(d) || answer == null ||
                (!("A".equals(answer) || "B".equals(answer) || "C".equals(answer) || "D".equals(answer)));
        if(isIllegal){
            //不合法输入
            LOG.error("新增试题信息不符合规范，新增试题失败。");
            return false;
        } else{
            //判断新增试题的stem是否已在数据库中存在
            List<Question> q=questionDao.queryQuestionsByQues(question);
            if(q.size()>0){
                LOG.info("新增试题已存在，新增试题失败");
                return false;
            }

            questionDao.insertQuestion(question);
            if (question.getId() > 0) {
                //判断是否新增试题成功
                LOG.info("新增试题成功。");
                return true;
            } else {
                LOG.info("新增试题失败。");
                return false;
            }
        }
    }

    @Override
    public int addQuestions(List<Question> questions) {
        if (questions == null || questions.size() == 0) {
            return 0;}
        return questionDao.insertQuestions(questions);
    }

    @Override
    public Boolean deleteQuestion(int id) {
        Question question = new Question();
        if (id > 0) {
            question.setId(id);
            int res = questionDao.deleteQuestion(question);
            if (res > 0) {
                LOG.info("删除试题 {} 成功", id);
                return true;
            } else {
                LOG.error("删除试题失败");
                return false;
            }
        } else {
            LOG.error("用户id不合法");
            return false;
        }
    }

    @Override
    public Boolean updateQuestion(Question question) {
        if(question.getId()>0){
            LOG.info("question {}",question);

            Boolean isIllegal = question.getCategory()==null|| "".equals(question.getCategory()) ||
                    question.getStem()==null|| "".equals(question.getStem()) ||
                    question.getOptA()==null|| "".equals(question.getOptA()) ||
                    question.getOptB()==null|| "".equals(question.getOptB()) ||
                    question.getOptC()==null|| "".equals(question.getOptC()) ||
                    question.getOptD()==null|| "".equals(question.getOptD()) ||
                    question.getAnswer() == null ||
                    (!("A".equals(question.getAnswer()) || "B".equals(question.getAnswer()) || "C".equals(question.getAnswer()) || "D".equals(question.getAnswer())));
            if(isIllegal){
                LOG.error("信息缺失，修改试题失败");
                return false;
            } else{
                int res = questionDao.updateQuestion(question);
                if(res > 0){
                    LOG.info("修改试题成功");
                    return true;
                } else{
                    LOG.error("修改试题失败");
                    return false;
                }
            }
        }else{
            LOG.error("试题id不存在，修改试题失败");
            return false;
        }
    }
}
