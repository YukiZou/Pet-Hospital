package com.ecnu.controller;

import com.ecnu.common.BaseResponse;
import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.dto.CaseDTO;
import com.ecnu.dto.CaseUpdateDTO;
import com.ecnu.dto.DiseaseQueryDTO;
import com.ecnu.dto.CaseQueryDTO;
import com.ecnu.dto.CaseDeleteDTO;
import com.ecnu.entity.Case;
import com.ecnu.entity.Disease;
import com.ecnu.entity.Text;
import com.ecnu.entity.Picture;
import com.ecnu.entity.Video;
import com.ecnu.service.CaseService;
import com.ecnu.service.DiseaseService;
import com.ecnu.service.TextService;
import com.ecnu.service.PictureService;
import com.ecnu.service.VideoService;
import com.ecnu.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * 病例
 */

@Controller
@RequestMapping("api/case")
public class CaseController {

    private static Logger LOG = LoggerFactory.getLogger(CaseController.class);

    @Autowired
    private CaseService caseService;
    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private TextService textService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private VideoService videoService;


    /**
     * 删除指定Id的病例，并且把关联表中与此病例关联的文字、视频、图片全部删掉
     * @param caseDeleteDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public BaseResponse deleteCase(@RequestBody CaseDeleteDTO caseDeleteDTO) {
        try {
            int caseId = caseDeleteDTO.getId();
            LOG.info("start delete case for id {}", caseId);
            if (caseId <= 0) {
                LOG.error("invalid case id");
                return new BaseResponse(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

            Text text=new Text();
            text.setCaseId(caseId);
            textService.deleteText(text);

            Picture picture=new Picture();
            picture.setCaseId(caseId);
            pictureService.deleteCasePic(picture);

            Video video=new Video();
            video.setCaseId(caseId);
            videoService.deleteCaseVideo(video);

            Case c=new Case();
            c.setId(caseId);
            caseService.deleteCase(c);

            LOG.info("delete case for id {} success", caseId);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());

        } catch (Exception e) {
            LOG.error("delete case for id {} failed!", caseDeleteDTO.getId());
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }
    /**
     * 新增试题
     * @param caseDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CaseVO addQuestion(@RequestBody CaseDTO caseDTO) {
        try {
            LOG.info("caseDTO : {} ",caseDTO.toString());

            //将CaseDTO对象转化成Case对象
            Case c=toCase(caseDTO);
            LOG.info("case : {} ",c.toString());

            Boolean res=false;
            res=caseService.addCase(c);
            if(res){//新增试题成功
                CaseVO caseVO=new CaseVO(c);
                LOG.info("add case : {} success", c.toString());
                caseVO.setStatus("success");
                return caseVO;
            }
            else{
                LOG.error("add case : {} failed", c.toString());
                return new CaseVO("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("add case failed");
            return new CaseVO("fail");
        }
    }

    /**
     * 修改疾病
     * @param caseUpdateDTO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateCase(@RequestBody CaseUpdateDTO caseUpdateDTO) {
        try {
            //将QuestionUpdateDTO对象转化成Question对象
            Case c=toCase2(caseUpdateDTO);
            LOG.info("case : {} ",c.toString());

            Boolean res = false;
            res = caseService.updateCase(c);
            if (res) {
                LOG.info("update case for case id {} success!", caseUpdateDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("update case for case id {} failed!", caseUpdateDTO.getId());
                return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            LOG.error("update case failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }


    /**
     * 根据条件查询符合条件的疾病
     * @param caseQueryDTO
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public CaseListVO queryCases(@RequestBody CaseQueryDTO caseQueryDTO) {
        LOG.info("query cases for filter {}", caseQueryDTO.toString());
        try {
            //将QuestionQueryDTO对象转化成Question对象
            Case c=toCase3(caseQueryDTO);
            Disease disease=toDisease3(caseQueryDTO);

            LOG.info("case {}",c.toString());
            LOG.info("disease {}",disease.toString());

            List<Case> queryCases=caseService.queryCases(c);
            List<Disease> queryDiseases=diseaseService.queryDiseases(disease);

            List<CaseQueryVO> caseQueryVOList=new LinkedList<>();
            for(Case queryCase: queryCases){
                int did=queryCase.getDiseaseId();
                Disease disease1=diseaseService.findDiseaseById(did);
                CaseQueryVO caseQueryVO=new CaseQueryVO(queryCase,disease1);
                caseQueryVOList.add(caseQueryVO);
            }

            for (Disease queryDisease: queryDiseases) {
                int did=queryDisease.getId();
                List<Case> qcs=caseService.findCaseByDId(did);
                for(Case qc: qcs){
                    CaseQueryVO caseQueryVO=new CaseQueryVO(qc,queryDisease);
                    caseQueryVOList.add(caseQueryVO);
                }
            }

            HashSet proSet = new HashSet();
            for (CaseQueryVO cc : caseQueryVOList) {
                proSet.add(cc);
            }
            //将hashSet转化成list
            List<CaseQueryVO> caseQueryVOList1 = new ArrayList<>();
            caseQueryVOList1.addAll(proSet);

            return new CaseListVO(ResponseStatusEnum.SUCCESS.getDesc(), caseQueryVOList1);
        } catch (Exception e) {
            e.printStackTrace();
            return new CaseListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }
    /**
     * 将CaseDTO对象转化成Case对象
     * @param caseDTO
     * @return
     */
    private Case toCase(CaseDTO caseDTO) {
        //TODO:判断传回的数据是否为null或者""
        Case c=new Case();
        c.setDiseaseId(caseDTO.getDiseaseId());
        c.setName(caseDTO.getName());
        return c;
    }

    /**
     * 将CaseUpdateDTO对象转化成Case对象
     * @param caseUpdateDTO
     * @return
     */
    private Case toCase2(CaseUpdateDTO caseUpdateDTO) {
        //TODO:判断传回的数据是否为null或者""
        Case c=new Case();
        c.setId(caseUpdateDTO.getId());
        c.setDiseaseId(caseUpdateDTO.getDiseaseId());
        c.setName(caseUpdateDTO.getName());
        return c;
    }

    /**
     * 将CaseQueryDTO对象转化成Case对象
     * @param caseQueryDTO
     * @return
     */
    private Case toCase3(CaseQueryDTO caseQueryDTO) {
        //TODO:判断传回的数据是否为null或者""
        Case c=new Case();
        c.setName(caseQueryDTO.getKeyword());
        return c;
    }

    /**
     * 将CaseQueryDTO对象转化成Disease对象
     * @param diseaseQueryDTO
     * @return
     */
    private Disease toDisease3(CaseQueryDTO diseaseQueryDTO) {
        //TODO:判断传回的数据是否为null或者""
        Disease disease=new Disease();
        disease.setName(diseaseQueryDTO.getKeyword());
        disease.setCategory(diseaseQueryDTO.getKeyword());
        return disease;
    }
}
