package org.jeecg.modules.flowable.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description：运动员注册审批历史
 * @author:whs
 * @date: 2020/7/25 15:52
 */
public class AthleteRegisterHistoryVo {
    private static final long serialVersionUID = 1L;

    /** 流程Id */
    private String processId;

    /** 任务Id */
    private String taskId;

    /** 审批节点 */
    private String workflowNode;

    /** 审批人Id */
    private String approverId;

    /** 审批人 */
    private String approver;

    /** 审批时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd ")
    private Date approvalDate;

    /** 审批意见 */
    private String approvalComment;

    /** 审批结果 */
    private String approvalResult;
}
