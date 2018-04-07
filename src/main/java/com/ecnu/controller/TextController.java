package com.ecnu.controller;

import com.ecnu.common.BaseResponse;
import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.dto.TextAllDTO;
import com.ecnu.dto.TextUpdateDTO;
import com.ecnu.dto.TextDeleteDTO;
import com.ecnu.dto.TextDTO;
import com.ecnu.entity.Text;
import com.ecnu.service.TextService;
import com.ecnu.vo.TextAllVO;
import com.ecnu.vo.TextVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/case/text")
public class TextController {

    private static Logger LOG = LoggerFactory.getLogger(TextController.class);

    @Autowired
    private TextService textService;

    /**
     * 新增text
     * @param textDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public TextVO addText(@RequestBody TextDTO textDTO) {
        try {
            //将TextDTO对象转化成Text对象
            Text text=toText(textDTO);
            Boolean res=false;
            res=textService.addText(text);
            if(res){//新增text成功
                TextVO textVO=new TextVO(text);
                LOG.info("add text : {} success", text.toString());
                textVO.setStatus("success");
                return textVO;
            }
            else{
                LOG.error("add text : {} failed", text.toString());
                return new TextVO("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("add text failed");
            return new TextVO("fail");
        }

    }

    /**
     * 删除text。
     * @param textDeleteDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteText(@RequestBody TextDeleteDTO textDeleteDTO) {
        try{
            Boolean res=false;
            int deleteTextId=textDeleteDTO.getId();
            Text text=textService.findTextById(deleteTextId);
            res = textService.deleteText(text);

            if (res) {
                LOG.info("delete text for text id {} success!", textDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("delete text for text id {} failed!", textDeleteDTO.getId());
                return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("delete text failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public TextAllVO allText(@RequestBody TextAllDTO textAllDTO) {
        try {
            Text text=toText3(textAllDTO);
            Text resText=textService.findTextByCIdS(text);
            if(resText.getId()>0){
                TextAllVO textAllVO=new TextAllVO(resText);
                textAllVO.setStatus("success");
                return textAllVO;
            }
            else {
                LOG.error("all text : {} failed", text.toString());
                return new TextAllVO("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("all text failed");
            return new TextAllVO("fail");
        }
    }

    /**
     * 修改试题
     * @param textUpdateDTO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateText(@RequestBody TextUpdateDTO textUpdateDTO) {
        try {
            Text text=toText2(textUpdateDTO);
            Boolean res = false;
            res = textService.updateText(text);
            if (res) {
                LOG.info("update text for text id {} success!", textUpdateDTO.getId());
                return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
            } else {
                LOG.error("update text for text id {} failed!", textUpdateDTO.getId());
                return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            LOG.error("update text failed");
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }

    }

    /**
     * 将TextDTO对象转化成Text对象
     * @param
     * @return
     */
    private Text toText(TextDTO textDTO) {
        //TODO:判断传回的数据是否为null或者""
        Text text=new Text();
        text.setCaseId(textDTO.getCaseId());
        text.setStage(textDTO.getStage());
        text.setInfo(textDTO.getInfo());
        return text;
    }

    /**
     * 将TextUpdateDTO对象转化成Text对象
     * @param
     * @return
     */
    private Text toText2(TextUpdateDTO textUpdateDTO) {
        //TODO:判断传回的数据是否为null或者""
        Text text=new Text();
        text.setId(textUpdateDTO.getId());
        text.setInfo(textUpdateDTO.getInfo());
        return text;
    }

    private Text toText3(TextAllDTO textAllDTO){
        Text text=new Text();
        text.setCaseId(textAllDTO.getCaseId());
        text.setStage(textAllDTO.getStage());
        return text;
    }
}
