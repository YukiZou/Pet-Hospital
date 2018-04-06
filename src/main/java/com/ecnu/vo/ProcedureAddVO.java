package com.ecnu.vo;

import com.ecnu.common.BaseResponse;
import com.ecnu.entity.Procedure;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProcedureAddVO extends BaseResponse implements Serializable {
    private int id;
    private int roleId;//角色id，与RoleEnum对应 1、2、3
    private String domain;//流程名
    private int step;//表示此节点属于流程的第几阶段
    private String stepName;//阶段名，类似于第一步，第二步这种（感觉没啥意义）
    private String info;//流程节点描述
    private List<ProcedurePicVO> pictureList;
    private List<ProcedureVideoVO> videoList;

    public ProcedureAddVO() {

    }

    public ProcedureAddVO(String status) {
        super.setStatus(status);
    }

    public ProcedureAddVO(Procedure procedure) {
        this.id = procedure.getId();
        this.roleId = procedure.getRoleId();
        this.domain = procedure.getDomain();
        this.step = procedure.getStep();
        this.stepName = procedure.getStepName();
        this.info = procedure.getInfo();
    }
}
