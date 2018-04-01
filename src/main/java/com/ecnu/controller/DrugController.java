package com.ecnu.controller;

import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.dto.DrugAddDTO;
import com.ecnu.dto.DrugQueryDTO;
import com.ecnu.entity.Drug;
import com.ecnu.service.DrugService;
import com.ecnu.vo.DrugAddVO;
import com.ecnu.vo.DrugListVO;
import com.ecnu.vo.DrugVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/drug")
public class DrugController {
    private static Logger LOG = LoggerFactory.getLogger(DrugController.class);

    @Autowired
    private DrugService drugService;

    /**
     * 新增药品的方法，比较完善，针对不同的错误返回了不同的错误类型
     * @param drugAddDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public DrugAddVO addDrug(@RequestBody DrugAddDTO drugAddDTO) {
        try {
            LOG.info("start add drug for drugAddDTO: {}", drugAddDTO);
            //将新增的drug信息转换成Drug对象
            Drug drug = toDrug(drugAddDTO);

            //如果将要新增的drug的name已经存在在表drug中，则新增不成功。
            Drug queryDrug = new Drug();
            queryDrug.setName(drug.getName());
            List<Drug> drugList = drugService.queryDrugs(queryDrug);
            if (drugList.size() > 0) {
                LOG.error("the drug name is already exist or name is null, add drug failed!");
                return new DrugAddVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }

            Boolean res = drugService.addDrug(drug);
            DrugAddVO drugAddVO = new DrugAddVO(drug);//将新增后得到的最新drug返回给前端

            if (res) {
                LOG.info("add drug success. Complete drug add.");
                drugAddVO.setStatus(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("sql execute error, add drug failed!");
                drugAddVO.setStatus(ResponseStatusEnum.SQL_FAIL.getDesc());
            }

            return drugAddVO;

        } catch (Exception e) {
            LOG.error("add drug failed!");
            e.printStackTrace();
            return new DrugAddVO(ResponseStatusEnum.FAIL.getDesc());
        }

    }


    /**
     * 查询数据库表drug中所有的药品记录
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public DrugListVO allDrugs() {
        try {
            LOG.info("start query all drugs!");
            List<Drug> drugList = drugService.queryDrugs(new Drug());
            List<DrugVO> drugVOList = new ArrayList<DrugVO>();
            //将格式转化
            for (Drug drug : drugList) {
                DrugVO drugVO = new DrugVO(drug);
                drugVOList.add(drugVO);
            }
            LOG.info("drugVO list: {}", drugVOList);
            LOG.info("query all drugs success!");
            return new DrugListVO(ResponseStatusEnum.SUCCESS.getDesc(), drugVOList);

        } catch (Exception e) {
            LOG.error("query all drugs failed!");
            e.printStackTrace();
            return new DrugListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }


    /**
     * 根据条件查询符合条件的药品，模糊查询
     * @param drugQueryDTO
     * @return
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public DrugListVO queryDrugs(@RequestBody DrugQueryDTO drugQueryDTO) {
        try {
            LOG.info("start query drugs for filter {} !", drugQueryDTO);
            //将DrugQueryDTO对象转换成Drug对象
            Drug drug = toDrug(drugQueryDTO);

            List<Drug> drugList = drugService.queryDrugs(drug);
            List<DrugVO> drugVOList = new ArrayList<DrugVO>();
            //将格式转化
            for (Drug d : drugList) {
                DrugVO drugVO = new DrugVO(d);
                drugVOList.add(drugVO);
            }
            LOG.info("drugVO list: {}", drugVOList);
            LOG.info("query drugs for filter {} success!", drugQueryDTO);
            return new DrugListVO(ResponseStatusEnum.SUCCESS.getDesc(), drugVOList);

        } catch (Exception e) {
            LOG.error("query drugs for filter {} failed!", drugQueryDTO);
            e.printStackTrace();
            return new DrugListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }


    /**
     * DrugAddDTO对象转化成Drug对象的类
     * @param drugAddDTO
     * @return
     */
    private Drug toDrug(DrugAddDTO drugAddDTO) {
        Drug drug = new Drug();
        drug.setName(drugAddDTO.getName());
        drug.setInfo(drugAddDTO.getInfo());
        drug.setPicture(drugAddDTO.getPicture());
        return drug;
    }

    private Drug toDrug(DrugQueryDTO drugQueryDTO) {
        Drug drug = new Drug();
        drug.setName(drugQueryDTO.getName());
        return drug;
    }

}
