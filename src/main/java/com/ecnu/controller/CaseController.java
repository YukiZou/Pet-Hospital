package com.ecnu.controller;

import com.ecnu.service.CaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 病例
 */

@Controller
@RequestMapping("api/case")
public class CaseController {

    private static Logger LOG = LoggerFactory.getLogger(CaseController.class);

    @Autowired
    private CaseService caseService;
}
