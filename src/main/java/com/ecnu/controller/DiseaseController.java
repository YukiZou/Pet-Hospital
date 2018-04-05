package com.ecnu.controller;

import com.ecnu.dto.DiseaseDTO;
import com.ecnu.entity.Disease;
import com.ecnu.service.DiseaseService;
import com.ecnu.vo.DiseaseAddVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
