package com.ecnu.service.impl;

import com.ecnu.dao.CaseDao;
import com.ecnu.dto.CaseDTO;
import com.ecnu.entity.Case;
import com.ecnu.service.CaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseServiceImpl implements CaseService{
    private static Logger LOG = LoggerFactory.getLogger(CaseServiceImpl.class);

    @Autowired
    private CaseDao caseDao;

    @Override
    public List<Case> findCaseByDId(int diseaseId){ return caseDao.findCasesByDId(diseaseId);}
    @Override
    public List<Case> queryCases(Case c) {
        return caseDao.queryCases(c);
    }

    @Override
    public Boolean deleteCase(Case c){
        int res = caseDao.deleteCase(c);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Boolean addCase(Case c) {
        int diseaseId=c.getDiseaseId();
        String name=c.getName();

        LOG.info("case service : {} ",c.toString());

        //TODO：新增试题的题干及选项的长度限制
        if(name == null || name.equals("")){//不合法输入
            LOG.error("新增病例信息不符合规范，新增病例失败。");
            return false;
        }
        else if(caseDao.findCaseByName(name)!=null){//判断新增病例是否已在数据库中存在
            LOG.error("新增病例已存在，新增病例失败。");
            return false;
        }
        else{
            caseDao.insertCase(c);
            //判断是否新增试题成功
            if (c.getId() > 0) {
                LOG.info("新增病例成功。");
                return true;
            } else {
                LOG.info("新增病例失败。");
                return false;
            }
        }
    }

    @Override
    public Boolean updateCase(Case c) {
        int res = caseDao.updateCase(c);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }
}
