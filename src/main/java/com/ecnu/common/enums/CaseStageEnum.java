package com.ecnu.common.enums;


/**
 * @author zou yuanyuan
 */

public enum CaseStageEnum {
    RECEIVE_TREATMENT(1, "接诊"),
    CASE_EXAMINATION(2, "病例检查"),
    DIAGNOSTIC_RESULTS(3, "诊断结果"),
    TREATMENT_PLAN(4, "治疗方案");

    private int value;
    private String desc;

    CaseStageEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
