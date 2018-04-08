package com.ecnu.service.impl;

import com.ecnu.dao.QuestionDao;
import com.ecnu.entity.Question;
import com.ecnu.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

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
        String A=question.getOptA();
        String B=question.getOptB();
        String C=question.getOptC();
        String D=question.getOptD();
        String answer=question.getAnswer();

        //TODO：新增试题的题干及选项的长度限制
        if(category == null || category.equals("") || A == null || A.equals("") ||
                B == null || B.equals("") || C == null || C.equals("") ||
                D == null || D.equals("") ||
                (!(answer.equals("A")||answer.equals("B")||answer.equals("C")||answer.equals("D")))){//不合法输入
            LOG.error("新增试题信息不符合规范，新增试题失败。");
            return false;
        }
        else if(questionDao.findQuestionByStem(stem)!=null){//判断新增试题的stem是否已在数据库中存在
            LOG.error("新增试题已存在，新增试题失败。");
            return false;
        }
        else{
            //TODO:判断新增试题的stem是否已在数据库中存在
            questionDao.insertQuestion(question);
            //判断是否新增试题成功
            if (question.getId() > 0) {
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
            return 0;
        }
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

            if(question.getCategory()==null||question.getCategory().equals("")||
                    question.getStem()==null||question.getStem().equals("")||
                    question.getOptA()==null||question.getOptA().equals("")||
                    question.getOptB()==null||question.getOptB().equals("")||
                    question.getOptC()==null||question.getOptC().equals("")||
                    question.getOptD()==null||question.getOptD().equals("")||
            (!(question.getAnswer().equals("A")||question.getAnswer().equals("B")||question.getAnswer().equals("C")||question.getAnswer().equals("D")))){

                LOG.error("信息缺失，修改试题失败");
                return false;

            }
            else{

                int res = questionDao.updateQuestion(question);
                if(res > 0){
                    LOG.info("修改试题成功");
                    return true;
                }
                else{
                    LOG.error("修改试题失败");
                    return false;
                }
            }
        }
        else{
            LOG.error("试题id不存在，修改试题失败");
            return false;
        }
    }
}
