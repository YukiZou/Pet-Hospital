package com.ecnu.controller;

import com.ecnu.common.BaseResponse;
import com.ecnu.common.enums.ResponseStatusEnum;
import com.ecnu.dto.*;
import com.ecnu.entity.Picture;
import com.ecnu.entity.Procedure;
import com.ecnu.entity.Video;
import com.ecnu.service.PictureService;
import com.ecnu.service.ProcedureService;
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

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.ecnu.common.CheckInputStringUtil.containIllegalCharacter;

@Controller
@RequestMapping("api/procedure")
public class ProcedureController {
    private static Logger LOG = LoggerFactory.getLogger(ProcedureController.class);

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private VideoService videoService;

    /**
     * 新增流程（实际上是新增某个流程的某一步）[包括 picture 和 video]
     * procedure 的 domain stepName info 字段注意要判断特殊字符的插入(controller层判断)
     * 不为空的字段： roleId不为空且有意义（1 2 3），domain不能重名（roleId + domain + step 可以唯一标识） stepName info
     * stepName如果前端没给，则后端根据step值自动生成。
     * 新增时要判断确保连贯,判断前一个step一定存在才能新增当前step.
     * picture和 video url 的 String list可以为空。
     * @param procedureAddDTO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public ProcedureAddVO addProcedureStep(@RequestBody ProcedureAddDTO procedureAddDTO) {
        try {
            LOG.info("start add procedureStep for procedureAddDTO: {}", procedureAddDTO);
            int roleId = procedureAddDTO.getRoleId();
            String domain = procedureAddDTO.getDomain();
            int step = procedureAddDTO.getStep();
            String stepName = procedureAddDTO.getStepName();
            String info =procedureAddDTO.getInfo();
            List<String> pictures = procedureAddDTO.getPictures(); //如果前端传过来 null, 则 pictures=null; 如果前端传过来[], 则 pictures.size == 0
            List<String> videos = procedureAddDTO.getVideos();

            //判断String类型输入是否含有特殊非法字符
            if ((domain != null && containIllegalCharacter(domain)) ||
                    (stepName != null && containIllegalCharacter(stepName)) ||
                    (info != null && containIllegalCharacter(info))) {
                LOG.error("domain stepName or info are not invalid!");
                return new ProcedureAddVO(ResponseStatusEnum.INVALID_INPUT_FAIL.getDesc());
            }

            //roleId + domain + step 的唯一性判断：确保一个流程下某一步唯一
            Procedure queryProcedure = new Procedure();
            queryProcedure.setRoleId(roleId);
            queryProcedure.setDomain(domain);
            queryProcedure.setStep(step);
            List<Procedure> qProcedureList = procedureService.queryProcedureSteps(queryProcedure);
            if (qProcedureList.size() == 1) {//能进此分支表示剩下的step都是连贯的
                LOG.info("this procedure step already exist, so change the following steps");
                //首先找到此roleId + domain下的所有step
                queryProcedure.setStep(0);
                List<Procedure> procedureList = procedureService.queryProcedureSteps(queryProcedure);
                for (Procedure pro : procedureList) {
                    //将后面的step后移
                    int oldStep = pro.getStep();
                    if ( oldStep >= step) {
                        Procedure updateStep = new Procedure();
                        updateStep.setStep(oldStep + 1);
                        updateStep.setId(pro.getId());
                        procedureService.updateProcedureStep(updateStep);
                    }
                } //循环将重复step以及其后的step往后移一位
            } else {
                //step非1时，判断前一个step已经存在才能新增成功
                if (step != 1) {
                    queryProcedure.setStep(step - 1);
                    List<Procedure> proList = procedureService.queryProcedureSteps(queryProcedure);
                    //前一个step不存在，返回错误
                    if (proList.size() == 0) {
                        LOG.error("the previous procedure step does not exist, add current procedureStep failed!");
                        return new ProcedureAddVO(ResponseStatusEnum.STEP_FAIL.getDesc());
                    }
                }
            }

            Procedure addProcedure = toProcedure(procedureAddDTO);
            //stepName如果前端没给，则后端根据step值自动生成。
            if (stepName == null || stepName.equals("")) {
                stepName = "第" + step + "步";
                addProcedure.setStepName(stepName);
            }
            procedureService.addProcedureStep(addProcedure);

            ProcedureAddVO procedureAddVO = new ProcedureAddVO(addProcedure);
            procedureAddVO.setStatus(ResponseStatusEnum.SUCCESS.getDesc());
            LOG.info("add procedure step success!");

            int procedureId = addProcedure.getId();//拿到新增的procedureId
            //如果有picture,则增加 picture记录
            if(pictures != null && pictures.size() > 0) {
                List<Picture> pictureList = new ArrayList<>();
                for (String pic : pictures) {
                    if (pic.equals("")) {
                        continue;
                    }
                    Picture picture = new Picture();
                    picture.setProcedureId(procedureId);
                    picture.setUrl(pic);
                    pictureList.add(picture);
                }
                pictureService.addProcedurePics(pictureList);

                List<ProcedurePicVO> procedurePicVOList = new ArrayList<>();
                for (Picture pic : pictureList) {
                    ProcedurePicVO procedurePicVO = new ProcedurePicVO(pic);
                    procedurePicVOList.add(procedurePicVO);
                }
                procedureAddVO.setPictureList(procedurePicVOList);
            }
            if (videos != null && videos.size() > 0) {
                List<Video> videoList = new ArrayList<>();
                for (String vi : videos) {
                    if (vi.equals("")) {
                        continue;
                    }
                    Video video = new Video();
                    video.setProcedureId(procedureId);
                    video.setUrl(vi);
                    videoList.add(video);
                }
                videoService.addProcedureVideos(videoList);

                List<ProcedureVideoVO> procedureVideoVOList = new ArrayList<>();
                for (Video vi : videoList) {
                    ProcedureVideoVO procedureVideoVO = new ProcedureVideoVO(vi);
                    procedureVideoVOList.add(procedureVideoVO);
                }
                procedureAddVO.setVideoList(procedureVideoVOList);
            }

            return procedureAddVO;
        } catch (Exception e) {
            LOG.error("add procedureStep failed!");
            e.printStackTrace();
            return new ProcedureAddVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 根据roleId 和 domain 删除整个的流程（对应procedure表中的多条记录）
     * 同时删除和那些procedureId关联的picture 和 video
     * 前置条件： roleId + domain 是一定有意义的
     * @param procedureDeleteDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public BaseResponse deleteProcedure(@RequestBody ProcedureDeleteDTO procedureDeleteDTO) {
        try {
            LOG.info("start delete procedure for procedureDeleteDTO {}", procedureDeleteDTO);
            int roleId = procedureDeleteDTO.getRoleId();
            String domain = procedureDeleteDTO.getDomain();

            //先根据 roleId 和 domain 拿到所有的 procedureId : procedureStepId
            Procedure procedure = toProcedure(procedureDeleteDTO);
            List<Procedure> procedureList = procedureService.queryProcedureSteps(procedure);
            List<Integer> procedureStepId = new ArrayList<>();
            for (Procedure pro : procedureList) {
                procedureStepId.add(pro.getId());
            }

            //根据procedureStepId 去删除 picture video 表中的相关记录
            List<Picture> pictureList = new ArrayList<>();
            List<Video> videoList = new ArrayList<>();
            for (Integer procedureId : procedureStepId) {
                Picture picture = new Picture();
                picture.setProcedureId(procedureId);
                pictureList.add(picture);

                Video video = new Video();
                video.setProcedureId(procedureId);
                videoList.add(video);
            }
            pictureService.deletePictures(pictureList);
            videoService.deleteVideos(videoList);

            //最后删除procedure表中的相关记录
            procedureService.deleteProcedureSteps(procedure);
            LOG.info("delete procedure for roleId {} and domain {} success! (including pictures and videos)", roleId, domain);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            LOG.error("delete procedure failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 查询所有流程记录（显示 roleId + domain）
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ProcedureListVO findAllProcedures() {
        try {
            LOG.info("start query all procedures(roleId + domain)");
            //首先拿到所有的procedure记录
            List<Procedure> procedureList = procedureService.queryProcedureSteps(new Procedure());
            //从结果中筛选出唯一的 roleId + domain（用set筛选）
            HashSet proSet = new HashSet();
            for (Procedure pro : procedureList) {
                ProcedureVO procedureVO = new ProcedureVO(pro);
                proSet.add(procedureVO);
            }
            //将hashSet转化成list
            List<ProcedureVO> procedureVOList = new ArrayList<>();
            procedureVOList.addAll(proSet);
            LOG.info("query all procedures(roleId + domain) success!");
            return new ProcedureListVO(ResponseStatusEnum.SUCCESS.getDesc(), procedureVOList);

        } catch (Exception e) {
            LOG.error("query all procedures(roleId + domain) failed!");
            e.printStackTrace();
            return new ProcedureListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 查询指定流程 roleId + domain 下的所有step
     * 输入：roleId, domain  (都不为空且组合有意义)
     *status, stepList[id, roleId, domain, step, stepName, info，pictureList[picture对象], videoList[video对象]]
     * @param procedureVO
     * @return
     */
    @RequestMapping(value = "/step/all", method = RequestMethod.POST)
    @ResponseBody
    public ProcedureStepListVO findAllSteps(@RequestBody ProcedureVO procedureVO) {
        try {
            int roleId = procedureVO.getRoleId();
            String domain = procedureVO.getDomain();
            LOG.info("query all step for roleId {} and domain {}",roleId, domain);

            //首先根据 roleId + domain 拿到所有的 procedure记录
            Procedure procedure = new Procedure();
            procedure.setRoleId(roleId);
            procedure.setDomain(domain);
            List<Procedure> queryProcedureList = procedureService.queryProcedureSteps(procedure);
            //把 List<Procedure>对象转化成 List<ProcedureStepVO>对象
            List<ProcedureStepVO> procedureStepVOList = new ArrayList<>();
            for (Procedure pro : queryProcedureList) {
                ProcedureStepVO procedureStepVO = new ProcedureStepVO(pro);
                //拿到 procedureId，根据这些 procedureId 去找对应的pictureList
                int procedureId = pro.getId();
                Picture picture = new Picture();
                picture.setProcedureId(procedureId);
                List<Picture> queryPicList = pictureService.queryPictures(picture);
                // List<Picture> to List<ProcedurePicVO>
                List<ProcedurePicVO> procedurePicVOList = new ArrayList<>();
                for (Picture pic : queryPicList) {
                    ProcedurePicVO procedurePicVO = new ProcedurePicVO(pic);
                    procedurePicVOList.add(procedurePicVO);
                }
                procedureStepVO.setPictureList(procedurePicVOList);
                //对video记录同样操作
                Video video = new Video();
                video.setProcedureId(procedureId);
                List<Video> queryVideoList = videoService.queryVideos(video);
                List<ProcedureVideoVO> procedureVideoVOList = new ArrayList<>();
                for (Video vi : queryVideoList) {
                    ProcedureVideoVO procedureVideoVO = new ProcedureVideoVO(vi);
                    procedureVideoVOList.add(procedureVideoVO);
                }
                procedureStepVO.setVideoList(procedureVideoVOList);
                //一个procedureStepVO记录除了包含基本 procedure 信息，还包含了关联的 pictureList 和 video List

                procedureStepVOList.add(procedureStepVO);
            }
            //查询任务到此结束，接下来把 procedureStepVOList 赋值给 ProcedureStepListVO 对象返回
            LOG.info("query all step for roleId {} and domain {} success!",roleId, domain);
            return new ProcedureStepListVO(ResponseStatusEnum.SUCCESS.getDesc(), procedureStepVOList);
        } catch (Exception e) {
            LOG.error("add procedureStep failed!");
            e.printStackTrace();
            return new ProcedureStepListVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 根据前端传过来的 id 删除指定的procedure step 记录
     * 前置条件：id 存在且有意义。
     * @param stepDeleteDTO
     * @return
     */
    @RequestMapping(value = "/step/delete", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public BaseResponse deleteStep(@RequestBody ProcedureStepChangeDTO stepDeleteDTO) {
        try {
            int procedureId = stepDeleteDTO.getId();
            LOG.info("start delete procedure step for id {}", procedureId);
            //查询到当前要删的procedure 记录 deleteProce
            Procedure procedure = new Procedure();
            procedure.setId(procedureId);
            List<Procedure> procedureList = procedureService.queryProcedureSteps(procedure);
            Procedure deleteProce = procedureList.get(0);
            //首先删除其关联的 picture 和 video
            List<Picture> pictureList = new ArrayList<>();
            Picture picture = new Picture();
            picture.setProcedureId(procedureId);
            pictureList.add(picture);
            pictureService.deletePictures(pictureList);
            List<Video> videoList = new ArrayList<>();
            Video video = new Video();
            video.setProcedureId(procedureId);
            videoList.add(video);
            videoService.deleteVideos(videoList);
            //删除当前 procedure 记录
            procedureService.deleteProcedureSteps(deleteProce);

            //拿到删除的procedure的 step,把其后的step往前移一位，确保step连贯
            int step = deleteProce.getStep();
            int roleId = deleteProce.getRoleId();
            String domain = deleteProce.getDomain();
            Procedure queryProce = new Procedure();
            queryProce.setRoleId(roleId);
            queryProce.setDomain(domain);
            List<Procedure> procedures = procedureService.queryProcedureSteps(queryProce);
            for (Procedure pro : procedures) {
                int oldStep = pro.getStep();
                if ( oldStep > step) {
                    Procedure updateStep = new Procedure();
                    updateStep.setStep(oldStep - 1);
                    updateStep.setId(pro.getId());
                    procedureService.updateProcedureStep(updateStep);
                }
            }
            LOG.info("delete procedure step for id {} success!", procedureId);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            LOG.error("delete current procedure step failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 将当前指定的 procedure 记录的 step - 1， 即上移一个节点
     * 前端限制：step == 1 时不可 上移
     * @param stepUpDTO
     * @return
     */
    @RequestMapping(value = "/step/up", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public BaseResponse upStep(@RequestBody ProcedureStepChangeDTO stepUpDTO) {
        try {
            int procedureId = stepUpDTO.getId();
            LOG.info("start up step for procedure step id {}", procedureId);
            Procedure procedure = new Procedure();
            procedure.setId(procedureId);
            List<Procedure> originProcedures = procedureService.queryProcedureSteps(procedure);
            Procedure currentPro = originProcedures.get(0);
            int currentStep = currentPro.getStep();
            //找到当前想上移的那个节点的上一个 changePro
            Procedure upPro = new Procedure();
            upPro.setDomain(currentPro.getDomain());
            upPro.setRoleId(currentPro.getRoleId());
            upPro.setStep(currentStep - 1);
            List<Procedure> procedureList = procedureService.queryProcedureSteps(upPro);
            Procedure changePro = procedureList.get(0);
            currentPro.setStep(currentStep - 1);
            changePro.setStep(currentStep);
            procedureService.updateProcedureStep(currentPro);
            procedureService.updateProcedureStep(changePro);
            LOG.info("up step for procedure step id {} success!", procedureId);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            LOG.error("up  procedure step failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 将当前指定的 procedure 记录的 step + 1， 即下移一个节点
     * 前端限制：最后一个节点不可下移
     * @param stepDownDTO
     * @return
     */
    @RequestMapping(value = "/step/down", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public BaseResponse downStep(@RequestBody ProcedureStepChangeDTO stepDownDTO) {
        try {
            int procedureId = stepDownDTO.getId();
            LOG.info("start down step for procedure step id {}", procedureId);
            Procedure procedure = new Procedure();
            procedure.setId(procedureId);
            List<Procedure> originProcedures = procedureService.queryProcedureSteps(procedure);
            Procedure currentPro = originProcedures.get(0);
            int currentStep = currentPro.getStep();
            //找到当前想下移的那个节点的下一个 changePro
            Procedure upPro = new Procedure();
            upPro.setDomain(currentPro.getDomain());
            upPro.setRoleId(currentPro.getRoleId());
            upPro.setStep(currentStep + 1);
            List<Procedure> procedureList = procedureService.queryProcedureSteps(upPro);
            Procedure changePro = procedureList.get(0);
            currentPro.setStep(currentStep + 1);
            changePro.setStep(currentStep);
            procedureService.updateProcedureStep(currentPro);
            procedureService.updateProcedureStep(changePro);
            LOG.info("down step for procedure step id {} success!", procedureId);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());

        } catch (Exception e) {
            LOG.error("down  procedure step failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 修改流程的roleId 或者 domain 字段
     * @param procedureUpdateDTO
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public BaseResponse updateProcedure(@RequestBody ProcedureUpdateDTO procedureUpdateDTO) {
        try {
            LOG.info("start update procedure roleId or domain for {}", procedureUpdateDTO);
            int roleId = procedureUpdateDTO.getRoleId();
            int newRoleId = procedureUpdateDTO.getNewRoleId();
            String domain = procedureUpdateDTO.getDomain();
            String newDomain = procedureUpdateDTO.getNewDomain();
            if (newDomain != null && containIllegalCharacter(newDomain)) {
                LOG.error("domain is not invalid!");
                return new ProcedureAddVO(ResponseStatusEnum.INVALID_INPUT_FAIL.getDesc());
            }
            //找到和原数据匹配的 procedure 记录
            Procedure procedure = new Procedure();
            procedure.setRoleId(roleId);
            procedure.setDomain(domain);
            List<Procedure> procedureList = procedureService.queryProcedureSteps(procedure);
           //构建存储新值的 Procedure 对象
            Procedure updatePro = new Procedure();
            if (newRoleId > 0) {
                updatePro.setRoleId(newRoleId);
            }
            if (newDomain != null && !newDomain.equals("")) {
                updatePro.setDomain(newDomain);
            }
            //遍历更新
            for (Procedure pro : procedureList) {
                int id = pro.getId();
                updatePro.setId(id);
                procedureService.updateProcedureStep(updatePro);
            }
            LOG.info("update procedure roleId or domain for {} success", procedureUpdateDTO);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            LOG.error("update procedure roleId or domain failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 对 procedure step 的 stepName info 更新
     * 限制：id字段一定有且有意义；stepName info 至少有一个字段有新值
     * @param procedureStepDTO
     * @return
     */
    @RequestMapping(value = "/step/update", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public BaseResponse updateStep(@RequestBody ProcedureStepDTO procedureStepDTO) {
        try {
            LOG.info("start update procedure step info or stepName for {}", procedureStepDTO);
            int id = procedureStepDTO.getId();
            String stepName = procedureStepDTO.getStepName();
            String info = procedureStepDTO.getInfo();

            if ((stepName != null && containIllegalCharacter(stepName)) ||
                    (info != null && containIllegalCharacter(info))) {
                LOG.error("stepName or info are not invalid!");
                return new ProcedureAddVO(ResponseStatusEnum.INVALID_INPUT_FAIL.getDesc());
            }

            Procedure procedure = new Procedure();
            procedure.setId(id);
            List<Procedure> procedureList = procedureService.queryProcedureSteps(procedure);
            Procedure pro = procedureList.get(0);
            Procedure updatePro = new Procedure();
            updatePro.setId(pro.getId());
            if (stepName != null && !stepName.equals("")) {
                updatePro.setStepName(stepName);
            }
            if (info != null && !info.equals("")) {
                updatePro.setInfo(info);
            }
            procedureService.updateProcedureStep(updatePro);
            LOG.info("update procedure step stepName or info for {} success", procedureStepDTO);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            LOG.error("update procedure step info or stepName failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 根据 procedureId 和url 为指定step 节点添加一张 picture url
     * stepId 有意义。
     * 重复判断。
     * @param procedurePicAddDTO
     * @return
     */
    @RequestMapping(value = "/picture/add", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public ProcedurePicAddVO procedurePicAdd(@RequestBody ProcedurePicAddDTO procedurePicAddDTO) {
        try {
            LOG.info("start add procedure picture for {}", procedurePicAddDTO);
            int stepId = procedurePicAddDTO.getStepId();
            String url = procedurePicAddDTO.getUrl();
            Picture picture = new Picture();
            picture.setProcedureId(stepId);
            picture.setUrl(url);
            //重复性检查
            List<Picture> pictureList = pictureService.queryPictures(picture);
            if (pictureList.size() > 0) {
                LOG.error("picture with stepId {} and url {} already exist!", stepId, url);
                return new ProcedurePicAddVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }
            pictureService.addProcedurePic(picture);
            LOG.info("add procedure picture for {} success", procedurePicAddDTO);
            return new ProcedurePicAddVO(ResponseStatusEnum.SUCCESS.getDesc(), picture);
        } catch (Exception e) {
            LOG.error("add procedureStep failed!");
            e.printStackTrace();
            return new ProcedurePicAddVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 根据 picture 的 id 删除指定的 picture 记录
     * @param procedurePicDeleteDTO
     * @return
     */
    @RequestMapping(value = "/picture/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteProcedurePic(@RequestBody ProcedurePicDeleteDTO procedurePicDeleteDTO) {
        try {
            LOG.info("start delete procedure picture with pictureId {}", procedurePicDeleteDTO);
            List<Picture> pictures = new ArrayList<>();
            Picture picture = new Picture();
            picture.setId(procedurePicDeleteDTO.getId());
            pictures.add(picture);
            pictureService.deletePictures(pictures);
            LOG.info("delete procedure picture with pictureId {} success", procedurePicDeleteDTO);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            LOG.error("delete procedure picture failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }


    /**
     * 根据 procedureId 和url 为指定step 节点添加一张 video url
     * stepId 有意义。
     * 重复判断。
     * @param procedureVideoAddDTO
     * @return
     */
    @RequestMapping(value = "/video/add", method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    public ProcedureVideoAddVO procedurePicAdd(@RequestBody ProcedureVideoAddDTO procedureVideoAddDTO) {
        try {
            LOG.info("start add procedure video for {}", procedureVideoAddDTO);
            int stepId = procedureVideoAddDTO.getStepId();
            String url = procedureVideoAddDTO.getUrl();
            Video video = new Video();
            video.setProcedureId(stepId);
            video.setUrl(url);
            //重复性检查
            List<Video> videoList = videoService.queryVideos(video);
            if (videoList.size() > 0) {
                LOG.error("video with stepId {} and url {} already exist!", stepId, url);
                return new ProcedureVideoAddVO(ResponseStatusEnum.INPUT_FAIL.getDesc());
            }
            videoService.addProcedureVideo(video);
            LOG.info("add procedure video for {} success!", procedureVideoAddDTO);
            return new ProcedureVideoAddVO(ResponseStatusEnum.SUCCESS.getDesc(), video);
        } catch (Exception e) {
            LOG.error("add procedureStep failed!");
            e.printStackTrace();
            return new ProcedureVideoAddVO(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    /**
     * 删除 videoId指定的 video 记录
     * @param procedureVideoDeleteDTO
     * @return
     */
    @RequestMapping(value = "/video/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteProcedureVideo(@RequestBody ProcedureVideoDeleteDTO procedureVideoDeleteDTO) {
        try {
            LOG.info("start delete procedure video with pictureId {}", procedureVideoDeleteDTO);
            List<Video> videoList = new ArrayList<>();
            Video video = new Video();
            video.setId(procedureVideoDeleteDTO.getId());
            videoList.add(video);
            videoService.deleteVideos(videoList);
            LOG.info("delete procedure video with pictureId {} success", procedureVideoDeleteDTO);
            return new BaseResponse(ResponseStatusEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            LOG.error("delete procedure video failed!");
            e.printStackTrace();
            return new BaseResponse(ResponseStatusEnum.FAIL.getDesc());
        }
    }

    private Procedure toProcedure(ProcedureAddDTO procedureAddDTO) {
        Procedure procedure = new Procedure();
        procedure.setRoleId(procedureAddDTO.getRoleId());
        procedure.setDomain(procedureAddDTO.getDomain());
        procedure.setStep(procedureAddDTO.getStep());
        procedure.setStepName(procedureAddDTO.getStepName());
        procedure.setInfo(procedureAddDTO.getInfo());
        return procedure;
    }

    private Procedure toProcedure(ProcedureDeleteDTO procedureDeleteDTO) {
        Procedure procedure = new Procedure();
        procedure.setRoleId(procedureDeleteDTO.getRoleId());
        procedure.setDomain(procedureDeleteDTO.getDomain());
        return procedure;
    }

}
