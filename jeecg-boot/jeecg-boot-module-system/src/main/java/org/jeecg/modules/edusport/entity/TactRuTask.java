package org.jeecg.modules.edusport.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: tact_ru_task
 * @Author: jeecg-boot
 * @Date:   2019-12-16
 * @Version: V1.0
 */
@Data
@TableName("tact_ru_task")
public class TactRuTask implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**assignee*/
	@Excel(name = "assignee", width = 15)
    private java.lang.String assignee;
	/**category*/
	@Excel(name = "category", width = 15)
    private java.lang.String category;
	/**claimTime*/
	@Excel(name = "claimTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date claimTime;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
	/**createTime*/
	@Excel(name = "createTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;
	/**delegation*/
	@Excel(name = "delegation", width = 15)
    private java.lang.String delegation;
	/**description*/
	@Excel(name = "description", width = 15)
    private java.lang.String description;
	/**dueDate*/
	@Excel(name = "dueDate", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date dueDate;
	/**executionId*/
	@Excel(name = "executionId", width = 15)
    private java.lang.String executionId;
	/**formKey*/
	@Excel(name = "formKey", width = 15)
    private java.lang.String formKey;
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**idLinkCount*/
	@Excel(name = "idLinkCount", width = 15)
    private java.lang.Integer idLinkCount;
	/**isCountEnabled*/
	@Excel(name = "isCountEnabled", width = 15)
    private java.lang.Integer isCountEnabled;
	/**name*/
	@Excel(name = "name", width = 15)
    private java.lang.String name;
	/**owner*/
	@Excel(name = "owner", width = 15)
    private java.lang.String owner;
	/**parentTaskId*/
	@Excel(name = "parentTaskId", width = 15)
    private java.lang.String parentTaskId;
	/**priority*/
	@Excel(name = "priority", width = 15)
    private java.lang.Integer priority;
	/**procDefId*/
	@Excel(name = "procDefId", width = 15)
    private java.lang.String procDefId;
	/**procInstId*/
	@Excel(name = "procInstId", width = 15)
    private java.lang.String procInstId;
	/**rev*/
	@Excel(name = "rev", width = 15)
    private java.lang.Integer rev;
	/**scopeDefinitionId*/
	@Excel(name = "scopeDefinitionId", width = 15)
    private java.lang.String scopeDefinitionId;
	/**scopeId*/
	@Excel(name = "scopeId", width = 15)
    private java.lang.String scopeId;
	/**scopeType*/
	@Excel(name = "scopeType", width = 15)
    private java.lang.String scopeType;
	/**subScopeId*/
	@Excel(name = "subScopeId", width = 15)
    private java.lang.String subScopeId;
	/**subTaskCount*/
	@Excel(name = "subTaskCount", width = 15)
    private java.lang.Integer subTaskCount;
	/**suspensionState*/
	@Excel(name = "suspensionState", width = 15)
    private java.lang.Integer suspensionState;
	/**taskDefId*/
	@Excel(name = "taskDefId", width = 15)
    private java.lang.String taskDefId;
	/**taskDefKey*/
	@Excel(name = "taskDefKey", width = 15)
    private java.lang.String taskDefKey;
	/**tenantId*/
	@Excel(name = "tenantId", width = 15)
    private java.lang.String tenantId;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
	/**更新时间*/
	@Excel(name = "更新时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;
	/**varCount*/
	@Excel(name = "varCount", width = 15)
    private java.lang.Integer varCount;
	/**是否同意*/
	@Excel(name = "是否同意", width = 15)
    private java.lang.String isAgree;
}
