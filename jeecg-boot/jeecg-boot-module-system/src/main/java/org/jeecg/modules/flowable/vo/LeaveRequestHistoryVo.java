package org.jeecg.modules.flowable.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Description: 请假申请单审批历史
 * @Author: andypmp4u
 * @Date:   2020-04-24
 * @Version: V1.0
 */
@Data
public class LeaveRequestHistoryVo implements Serializable {
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
