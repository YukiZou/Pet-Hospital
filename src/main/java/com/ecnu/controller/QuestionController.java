package com.ecnu.controller;


import com.ecnu.dto.QuestionDTO;
import com.ecnu.entity.Question;
import com.ecnu.service.QuestionService;
import com.ecnu.vo.QuestionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("api/question")
public class QuestionController {
    private static Logger LOG = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;


    /**
     * 新增试题
     * @param questionDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public QuestionVO addQuestion(@RequestBody QuestionDTO questionDTO) {
        try {
            //将QuestionDTO对象转化成Question对象
            Question question=toQuestion(questionDTO);
            Boolean res=false;
            res=questionService.addQuestion(question);
            if(res){//新增用户成功
                QuestionVO questionVO=new QuestionVO(question);
                LOG.info("add question : {} success", question.toString());
                questionVO.setStatus("success");
                return questionVO;
            }
            else{
                LOG.error("add question : {} failed", question.toString());
                return new QuestionVO("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("add question failed");
            return new QuestionVO("fail");
        }
    }

    /**
     * 将QuestionDTO对象转化成Question对象
     * @param questionDTO
     * @return
     */
    private Question toQuestion(QuestionDTO questionDTO) {
        //TODO:判断传回的数据是否为null或者""
        Question question=new Question();
        question.setCategory(questionDTO.getCategory());
        question.setStem(questionDTO.getStem());
        question.setA(questionDTO.getA());
        question.setB(questionDTO.getB());
        question.setC(questionDTO.getC());
        question.setD(questionDTO.getD());
        question.setAnswer(questionDTO.getAnswer());
        return question;
    }

}
