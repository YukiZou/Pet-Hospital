package com.ecnu.service.impl;

import com.ecnu.dao.TextDao;
import com.ecnu.entity.Text;
import com.ecnu.service.TextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author asus
 */
@Service
public class TextServiceImpl implements TextService {

    private static Logger LOG = LoggerFactory.getLogger(TextServiceImpl.class);

    @Autowired
    private TextDao textDao;

    @Override
    public Boolean addText(Text text) {
            textDao.insertText(text);
            if (text.getId() > 0) {
                return true;
            } else {
                return false;
            }
    }

    @Override
    public Boolean deleteText(Text text) {
        int res = textDao.deleteText(text);
        if (res > 0) {
            LOG.info("删除text成功");
            return true;
        }
        else {
            LOG.error("删除text失败");
            return false;
        }
    }

    @Override
    public Text findTextById(int id) {
        return textDao.findTextById(id);
    }

    @Override
    public Text findTextByCIdS(Text text){return textDao.findTextByCIdS(text);}

    @Override
    public Boolean updateText(Text text) {
        if(text.getId()>0){
            if(text.getInfo()==null|| "".equals(text.getInfo())){
                LOG.error("信息缺失，修改text失败");
                return false;

            } else{
                int res = textDao.updateText(text);
                if(res > 0){
                    LOG.info("修改text成功");
                    return true;
                } else{
                    LOG.error("修改text失败");
                    return false;
                }
            }
        }
        else{
            LOG.error("text id不存在，修改text失败");
            return false;
        }
    }
}
