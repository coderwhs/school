package org.jeecg.modules.flowable.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import lombok.Data;

/**
 * @description：运动员注册单
 * @author:whs
 * @date: 2020/7/25 15:52
 */
public class AthleteRegisterTaskVo {
    private static final long serialVersionUID = 1L;

    /**申请Id：作为请假的主键*/
    private String registerId;

    /** 注册人 */
    private String username;
    /** 手机号码 */
    private String mobile;
    /** 民族 */
    private String nation;
    /** 性别 */
    private String sex;
    /** 身份证号码 */
    private String idNo;
    /** 带训教练员 */
    private String coachId;
    /** 专业项目 */
    private String sportCode;
    /** 家庭住址 */
    private String homeAddress;
    /** 父亲姓名 */
    private String father;
    /** 父亲电话 */
    private String fatherMobile;
    /** 母亲姓名 */
    private String mother;
    /** 母亲电话 */
    private String motherMobile;

    /** 流程Id */
    private String processId;

    /** 任务Id */
    private String taskId;

    /** 审批状态（待审批、结束） */
    private String workflowStatus;

    /** 审批状态（待审批、结束） */
    private String taskStatus;

    /** 审批节点 */
    private String workflowNode;

    /** 审批人Id */
    private String approverId;

    /** 审批人 */
    private String approver;

    /** 审批时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd ")
    private String approvalDate;

    /** 审批结果 */
    private String approvalResult;

    /** 审批意见 */
    private String approvalComment;

    private List<AthleteRegisterHistoryVo> approvalList;
}
