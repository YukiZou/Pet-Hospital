package com.ecnu.controller;


import com.ecnu.common.TxtUtil;
import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.common.BaseResponse;
import com.ecnu.dto.QuestionDTO;
import com.ecnu.dto.QuestionDeleteDTO;
import com.ecnu.dto.QuestionUpdateDTO;
import com.ecnu.entity.Question;
import com.ecnu.service.QuestionService;
import com.ecnu.vo.QuestionCategoryListVO;
import com.ecnu.vo.QuestionVO;
import com.ecnu.vo.QuestionListVO;
import com.ecnu.vo.QuestionQueryVO;
import com.ecnu.dto.QuestionQueryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.ecnu.common.CheckInputStringUtil.containIllegalCharacter;


/**
 * @author asus
 */
@Controller
@RequestMapping("api/question")
public class QuestionController {
    private static Logger LOG = LoggerFactory.getLogger(QuestionController.class);
    private final static String TXT = "txt";

    @Autowired
    private QuestionService questionService;


    /**
     * 根据条件查询符合条件的试题
     * @param questionQueryDTO
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public QuestionListVO queryQuestions(@RequestBody QuestionQueryDTO questionQueryDTO) {
        LOG.info("query questions for filter {}", questionQueryDTO.toString());
        try {
            //将QuestionQueryDTO对象转化成Question对象
            Question question=toQuestion2(questionQueryDTO);

            List<Question> queryQuestions=questionService.queryQuestions(question);
            LOG.info("queryQuestions for filter : {} ",queryQuestions.toString());

            List<QuestionQueryVO> questionQueryVOList = new LinkedList<>();
            for (Question queryQuestion: queryQuestions) {
                QuestionQueryVO questionQueryVO = new QuestionQueryVO(queryQuestion);
                LOG.info("questionQueryVO for filter : {} ",questionQueryVO.toString());

                questionQueryVOList.add(questionQueryVO);
            }
            LOG.info("query questions for filter {} success!", questionQueryDTO.toString());
            LOG.info("questionQueryVOList for filter : {} ",questionQueryVOList.toString());
            return new QuestionListVO(ResponseStatusEnum.SUCCESS.getDesc(), questionQueryVOList);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("query question for filter {} failed", questionQueryDTO.toString());
            return new QuestionListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 新增试题
     * @param questionDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public QuestionVO addQuestion(@RequestBody QuestionDTO questionDTO) {
        try {
            LOG.info("questionDTO {}",questionDTO);
            //将QuestionDTO对象转化成Question对象
            Question question=toQuestion(questionDTO);
            Boolean res=false;
            res=questionService.addQuestion(question);
            if(res){
                //新增试题成功
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
     * 根据文件批量上传试题
     * @author zouyuanyuan on 2018.04.08
     * @param file
     * @return
     */
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse fileAddQuestion(@RequestParam("file") CommonsMultipartFile file) {
        try {
            //获得原始文件名
            String fileName = file.getOriginalFilename();
            LOG.info("start add questions by file {} ", fileName);

            if (!fileName.endsWith(TXT)) {
                LOG.error("the type of uploaded file is not txt");
                return new BaseResponse(ResponseStatusEnum.INVALID_INPUT_FAIL.getDesc());
            }
            //得到原始数据
            List<String[]> originList = TxtUtil.readTxt(file);
            int size = originList.size();

            List<Question> questionList = new ArrayList<>();
            for (int index = 0; index < size; index++) {
                String[] strings = originList.get(index);
                if (strings.length != 7) {
                    //每行必须是七个字段
                    continue;
                }

                String category = strings[0];
                String stem = strings[1];
                String A = strings[2];
                String B = strings[3];
                String C = strings[4];
                String D = strings[5];
                String answer = strings[6];

                //新增试题的题干及选项的长度限制
                Boolean isIllegal = category == null || category.equals("")
                        || stem == null || stem.equals("")
                        || A == null || A.equals("")
                        || B == null || B.equals("")
                        || C == null || C.equals("")
                        || D == null || D.equals("")
                        || answer == null || (!(answer.equals("A")||answer.equals("B")||answer.equals("C")||answer.equals("D")));
                if(isIllegal){
                    //不合法输入
                    LOG.error("invalid input.");
                    continue;
                }
                if (category.length() > 255 || containIllegalCharacter(category)
                        || stem.length() > 255 || containIllegalCharacter(stem)
                        || A.length() > 255 || containIllegalCharacter(A)
                        || B.length() > 255 || containIllegalCharacter(B)
                        || C.length() > 255 || containIllegalCharacter(C)
                        || D.length() > 255 || containIllegalCharacter(D)) {
                    LOG.error("invalid input.");
                    continue;
                }

                //判断新增的题干是否已经存在（数据库中不允许题干重复）
                Question question = new Question();
                question.setCategory(category);
                question.setStem(stem);
                question.setOptA(A);
                question.setOptB(B);
                question.setOptC(C);
                question.setOptD(D);
                question.setAnswer(answer);

                List<Question> queryQuestions = questionService.queryQuestionsByQues(question);
                if (queryQuestions != null && queryQuestions.size() > 0) {
                    LOG.error("this question already exist!");
                    continue;
                }
                questionList.add(question);
            }
            int res = questionService.addQuestions(questionList);
            if (res > 0) {
                LOG.info("add questions by file {} success!", fileName);
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.info("no question to be added by file {}!", fileName);
                return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("add questions by file failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 删除试题。
     * @param questionDeleteDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteQuestion(@RequestBody QuestionDeleteDTO questionDeleteDTO) {
        try{
            Boolean res;
            int deleteQuestionId=questionDeleteDTO.getId();
            res = questionService.deleteQuestion(deleteQuestionId);

            if (res) {
                LOG.info("delete question for question id {} success!", questionDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("delete question for question id {} failed!", questionDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("delete question failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }

    /**
     * 修改试题
     * @param questionUpdateDTO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateQuestion(@RequestBody QuestionUpdateDTO questionUpdateDTO) {
        try {
            LOG.info("questionUpdateDTO {}",questionUpdateDTO);
            //将QuestionUpdateDTO对象转化成Question对象
            Question question = toQuestion3(questionUpdateDTO);
            Boolean res;
            res = questionService.updateQuestion(question);
            if (res) {
                LOG.info("update question for question id {} success!", questionUpdateDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("update question for question id {} failed!", questionUpdateDTO.getId());
                return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            LOG.error("update question failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }

    /**
     * 返回所有试题种类
     * @return
     */
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseBody
    public QuestionCategoryListVO allQuestionCategory() {
        try {
            List<Question> queryQuestionCategory=questionService.getAllQuestionCategory();

            List<String> categoryList = new LinkedList<>();
            for (Question question: queryQuestionCategory) {
                String cate=question.getCategory();
                categoryList.add(cate);
            }

            LOG.info("list all question category success! {}",categoryList);
            return new QuestionCategoryListVO(ResponseStatusEnum.SUCCESS.getDesc(), categoryList);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("list all question category failed");
            return new QuestionCategoryListVO(ResponseStatusEnum.FAIL.getDesc());
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
        question.setOptA(questionDTO.getOptA());
        question.setOptB(questionDTO.getOptB());
        question.setOptC(questionDTO.getOptC());
        question.setOptD(questionDTO.getOptD());
        question.setAnswer(questionDTO.getAnswer());
        return question;
    }

    /**
     * 将QuestionQueryDTO对象转化成Question对象
     * @param questionQueryDTO
     * @return
     */
    private Question toQuestion2(QuestionQueryDTO questionQueryDTO) {
        //TODO:判断传回的数据是否为null或者""
        Question question=new Question();
        question.setId(questionQueryDTO.getId());
        question.setStem(questionQueryDTO.getKeyword());
        question.setOptA(questionQueryDTO.getKeyword());
        question.setOptB(questionQueryDTO.getKeyword());
        question.setOptC(questionQueryDTO.getKeyword());
        question.setOptD(questionQueryDTO.getKeyword());
        return question;
    }

    /**
     * 将QuestionUpdateDTO对象转化成Question对象
     * @param questionUpdateDTO
     * @return
     */
    private Question toQuestion3(QuestionUpdateDTO questionUpdateDTO) {
        //TODO:判断传回的数据是否为null或者""
        Question question=new Question();
        question.setId(questionUpdateDTO.getId());
        question.setCategory(questionUpdateDTO.getCategory());
        question.setStem(questionUpdateDTO.getStem());
        question.setOptA(questionUpdateDTO.getOptA());
        question.setOptB(questionUpdateDTO.getOptB());
        question.setOptC(questionUpdateDTO.getOptC());
        question.setOptD(questionUpdateDTO.getOptD());
        question.setAnswer(questionUpdateDTO.getAnswer());
        return question;
    }
}
