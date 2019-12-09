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
 * @Description: 运动项目训练班训练计划信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_sport_class_task")
public class SportClassTask implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**训练班*/
	@Excel(name = "训练班", width = 15)
    private java.lang.String sportClassId;
	/**训练任务名称*/
	@Excel(name = "训练任务名称", width = 15)
    private java.lang.String taskName;
	/**发布教练*/
	@Excel(name = "发布教练", width = 15)
    private java.lang.String teacherNo;
	/**开始日期*/
	@Excel(name = "开始日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date taskStartDate;
	/**结束日期*/
	@Excel(name = "结束日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date taskEndDate;
	/**训练内容*/
	@Excel(name = "训练内容", width = 15)
    private java.lang.String taskContent;
	/**训练手段*/
	@Excel(name = "训练手段", width = 15)
    private java.lang.String taskMethod;
	/**持续时间(分钟)*/
	@Excel(name = "持续时间(分钟)", width = 15)
    private java.lang.Integer taskDurationTime;
	/**训练量*/
	@Excel(name = "训练量", width = 15)
    private java.lang.String taskWorkload;
	/**训练强度*/
	@Excel(name = "训练强度", width = 15)
    private java.lang.String taskIntensity;
	/**恢复手段*/
	@Excel(name = "恢复手段", width = 15)
    private java.lang.String taskRecoveryMethod;
	/**备注*/
	@Excel(name = "备注", width = 15)
    private java.lang.String remark;
	/**乐观锁*/
	@Excel(name = "乐观锁", width = 15)
    private java.lang.Integer revision;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
	/**更新时间*/
	@Excel(name = "更新时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;
}
