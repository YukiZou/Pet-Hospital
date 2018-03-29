package com.ecnu.service.impl;

import com.ecnu.dao.QuestionDao;
import com.ecnu.entity.Question;
import com.ecnu.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
    private static Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);
    @Autowired
    private QuestionDao questionDao;

    @Override
    public Boolean addQuestion(Question question) {
        String category=question.getCategory();
        String stem=question.getStem();
        String A=question.getA();
        String B=question.getB();
        String C=question.getC();
        String D=question.getD();
        String answer=question.getAnswer();

        //TODO：新增试题的题干及选项的长度限制
        if(category == null || category.equals("") || A == null || A.equals("") ||
                B == null || B.equals("") || C == null || C.equals("") ||
                D == null || D.equals("") ||
                (!(answer.equals("A")||answer.equals("B")||answer.equals("C")||answer.equals("D")))){//不合法输入
            LOG.error("新增试题信息不符合规范，新增试题失败。");
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
}
