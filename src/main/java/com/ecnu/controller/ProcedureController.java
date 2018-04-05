package com.ecnu.controller;

import com.ecnu.entity.Procedure;
import com.ecnu.service.ProcedureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/procedure")
public class ProcedureController {
    private static Logger LOG = LoggerFactory.getLogger(ProcedureController.class);

    @Autowired
    private ProcedureService procedureService;

    //只是测试用，还要改
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addProcedureStep(@RequestBody Procedure procedure) {
        LOG.info("procedure: {}", procedure);
        int res = procedureService.addProcedureStep(procedure);
        if (res > 0) {
            return procedure.toString();
        } else {
            return "fail";
        }
    }
}
