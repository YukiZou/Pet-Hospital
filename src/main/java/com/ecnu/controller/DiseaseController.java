package com.ecnu.controller;

import com.ecnu.common.BaseResponse;
import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.dto.DiseaseDTO;
import com.ecnu.dto.DiseaseUpdateDTO;
import com.ecnu.dto.DiseaseQueryDTO;
import com.ecnu.entity.Disease;
import com.ecnu.service.DiseaseService;
import com.ecnu.vo.DiseaseAddVO;
import com.ecnu.vo.DiseaseCategoryListVO;
import com.ecnu.vo.DiseaseListVO;
import com.ecnu.vo.DiseaseQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * 病种：category + 病种name
 */
@Controller
@RequestMapping("api/disease")
public class DiseaseController {

    private static Logger LOG = LoggerFactory.getLogger(DiseaseController.class);

    @Autowired
    private DiseaseService diseaseService;

    /**
     * 新增病种
     * @param diseaseDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public DiseaseAddVO addDisease(@RequestBody DiseaseDTO diseaseDTO) {
        try {
            LOG.info("diseaseDTO {}",diseaseDTO);
            //将DiseaseDTO对象转化成Disease对象
            Disease disease=toDisease(diseaseDTO);
            Boolean res=false;
            res=diseaseService.addDisease(disease);
            if(res){//新增病种成功
                DiseaseAddVO diseaseAddVO=new DiseaseAddVO(disease);
                LOG.info("add disease : {} success", disease.toString());
                diseaseAddVO.setStatus("success");
                return diseaseAddVO;
            }
            else{
                LOG.error("add disease : {} failed", disease.toString());
                return new DiseaseAddVO("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("add disease failed");
            return new DiseaseAddVO("fail");
        }

    }

    /**
     * 修改疾病
     * @param diseaseUpdateDTO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateDisease(@RequestBody DiseaseUpdateDTO diseaseUpdateDTO) {
        try {
            LOG.info("diseaseUpdateDTO {}",diseaseUpdateDTO);
            //将QuestionUpdateDTO对象转化成Question对象
            Disease disease=toDisease2(diseaseUpdateDTO);

            Boolean res = false;
            res = diseaseService.updateDisease(disease);
            if (res) {
                LOG.info("update disease for disease id {} success!", diseaseUpdateDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("update disease for disease id {} failed!", diseaseUpdateDTO.getId());
                return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            LOG.error("update disease failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }

    /**
     * 返回所有疾病种类（病种category）
     * @return
     */
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseBody
    public DiseaseCategoryListVO allDiseaseCategory() {
        try {
            List<Disease> queryCategory=diseaseService.getAllDiseaseCategory();

            List<String> categoryList = new LinkedList<>();
            for (Disease disease: queryCategory) {
                String cate=disease.getCategory();
                categoryList.add(cate);
            }

            LOG.info("list all disease category success! {}",categoryList);
            return new DiseaseCategoryListVO(ResponseStatusEnum.SUCCESS.getDesc(), categoryList);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("list all disease category failed");
            return new DiseaseCategoryListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 根据条件查询符合条件的疾病
     * @param diseaseQueryDTO
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public DiseaseListVO queryDiseases(@RequestBody DiseaseQueryDTO diseaseQueryDTO) {
        LOG.info("query questions for filter {}", diseaseQueryDTO.toString());
        try {
            //将QuestionQueryDTO对象转化成Question对象
            Disease disease=toDisease3(diseaseQueryDTO);

            List<Disease> queryDiseases=diseaseService.queryDiseases(disease);
            LOG.info("queryDiseases for filter : {} ",queryDiseases.toString());

            List<DiseaseQueryVO> diseaseQueryVOList = new LinkedList<>();
            for (Disease queryDisease: queryDiseases) {
                DiseaseQueryVO diseaseQueryVO = new DiseaseQueryVO(queryDisease);///////
                LOG.info("diseaseQueryVO for filter : {} ",diseaseQueryVO.toString());

                diseaseQueryVOList.add(diseaseQueryVO);
            }
            LOG.info("query diseases for filter {} success!", diseaseQueryDTO.toString());
            LOG.info("diseaseQueryVOList for filter : {} ",diseaseQueryVOList.toString());
            return new DiseaseListVO(ResponseStatusEnum.SUCCESS.getDesc(), diseaseQueryVOList);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("query diseases for filter {} failed", diseaseQueryDTO.toString());
            return new DiseaseListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 将DiseaseDTO对象转化成Disease对象
     * @param diseaseDTO
     * @return
     */
    private Disease toDisease(DiseaseDTO diseaseDTO) {
        //TODO:判断传回的数据是否为null或者""
        Disease disease=new Disease();
        disease.setName(diseaseDTO.getName());
        disease.setCategory(diseaseDTO.getCategory());
        return disease;
    }

    /**
     * 将DiseaseUpdateDTO对象转化成Disease对象
     * @param diseaseUpdateDTO
     * @return
     */
    private Disease toDisease2(DiseaseUpdateDTO diseaseUpdateDTO) {
        //TODO:判断传回的数据是否为null或者""
        Disease disease=new Disease();
        disease.setId(diseaseUpdateDTO.getId());
        disease.setName(diseaseUpdateDTO.getName());
        disease.setCategory(diseaseUpdateDTO.getCategory());
        return disease;
    }

    /**
     * 将DiseaseQueryDTO对象转化成Disease对象
     * @param diseaseQueryDTO
     * @return
     */
    private Disease toDisease3(DiseaseQueryDTO diseaseQueryDTO) {
        //TODO:判断传回的数据是否为null或者""
        Disease disease=new Disease();
        disease.setName(diseaseQueryDTO.getKeyword());
        disease.setCategory(diseaseQueryDTO.getKeyword());
        return disease;
    }
}
