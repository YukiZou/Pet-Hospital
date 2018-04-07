package com.ecnu.service.impl;

import com.ecnu.dao.PictureDao;
import com.ecnu.entity.Picture;
import com.ecnu.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService{
    private static Logger LOG = LoggerFactory.getLogger(PictureServiceImpl.class);

    @Autowired
    private PictureDao pictureDao;
    /**
     * 新增与流程相关的图片
     * 新增流程的图片情况：参数 picture 的 procedureId 和 url 一定要有。
     * 调用dao层接口的前置条件：procedureId有意义且 url存在
     * 限制：确保 procedureId和 url 组的值唯一，避免重复图片的插入
     * @param picture
     * @return
     */
    @Override
    public int addProcedurePic(Picture picture) {
        //TODO:先检查输入的参数的正确性和意义再插入
        return pictureDao.insertProcedurePic(picture);
    }

    /**
     * 新增与病例相关的图片
     * @param picture
     * @return
     */
    @Override
    public Boolean addCasePic(Picture picture){
        pictureDao.insertCasePic(picture);
        if (picture.getId() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteCasePic(Picture picture) {
        int res = pictureDao.deleteCasePicture(picture);
        if (res > 0) {
            LOG.info("删除图片成功");
            return true;
        }
        else {
            LOG.error("删除图片失败");
            return false;
        }
    }

    @Override
    public Picture findPictureById(int id) {
        return pictureDao.findPictureById(id);
    }

    /**
     * 批量新增与流程相关的 picture 记录
     * 新增流程的图片情况：参数 picture 的 procedureId 和 url 一定要有。
     * 调用dao层接口的前置条件：procedureId有意义且 url存在
     * 限制：确保 procedureId和 url 组的值唯一，避免重复图片的插入
     * @param pictures
     * @return
     */
    @Override
    public int addProcedurePics(List<Picture> pictures) {
        //TODO:先检查输入的参数的正确性和意义再插入
        //根据前端的逻辑判断，可能会有size==0的参数传过来，判断一下
        if (pictures == null || pictures.size() == 0) {
            return 0;
        }
        return pictureDao.insertProcedurePics(pictures);
    }

    /**
     * 根据参数的指定字段查找符合条件的 picture list
     * 属性有值表示要匹配那个字段去查询,如果所有字段都为空，则是查询所有。
     * 限制：暂时无
     * 如果没有找到匹配的记录，返回：一个size=0的list []
     * @param picture
     * @return
     */
    @Override
    public List<Picture> queryPictures(Picture picture) {
        return pictureDao.queryPictures(picture);
    }

    @Override
    public List<Picture> queryPicturesByStepIds(List<Integer> procedureIds) {
        if (procedureIds == null || procedureIds.size() == 0) {
            return null;
        }
        return pictureDao.queryPicturesByStepIds(procedureIds);
    }

    /**
     * 批量删除
     * picture 所有条件都不匹配的话会清空整个表中的记录
     * @param pictures
     * @return
     */
    @Override
    public int deletePictures(List<Picture> pictures) {
        if (pictures == null || pictures.size() == 0) {
            return 0;
        }
        return pictureDao.deletePictures(pictures);
    }
}
