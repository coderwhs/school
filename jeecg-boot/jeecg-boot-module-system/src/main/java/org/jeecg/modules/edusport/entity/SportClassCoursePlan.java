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
 * @Description: 课训练计划信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_sport_class_course_plan")
public class SportClassCoursePlan implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**训练队*/
	@Excel(name = "训练队", width = 15)
    private java.lang.String sportClassId;
	/**训练计划名称*/
	@Excel(name = "训练计划名称", width = 15)
    private java.lang.String planName;
	/**课训日期*/
	@Excel(name = "课训日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date courseDate;
	/**目的任务*/
	@Excel(name = "目的任务", width = 15)
    private java.lang.String taskGoal;
	/**准备训练量*/
	@Excel(name = "准备训练量", width = 15)
    private java.lang.String prepareWorkload;
	/**准备训练强度*/
	@Excel(name = "准备训练强度", width = 15)
    private java.lang.String prepareIntensity;
	/**准备训练时间*/
	@Excel(name = "准备训练时间", width = 15)
    private java.lang.String prepareTime;
	/**准备训练内容*/
	@Excel(name = "准备训练内容", width = 15)
    private java.lang.String prepareContent;
	/**基本训练量*/
	@Excel(name = "基本训练量", width = 15)
    private java.lang.String basicWorkload;
	/**基本训练强度*/
	@Excel(name = "基本训练强度", width = 15)
    private java.lang.String basicIntensity;
	/**基本训练时间*/
	@Excel(name = "基本训练时间", width = 15)
    private java.lang.String basicTime;
	/**基本训练内容*/
	@Excel(name = "基本训练内容", width = 15)
    private java.lang.String basicContent;
	/**结束训练量*/
	@Excel(name = "结束训练量", width = 15)
    private java.lang.String finishWorkload;
	/**结束训练强度*/
	@Excel(name = "结束训练强度", width = 15)
    private java.lang.String finishIntensity;
	/**结束训练时间*/
	@Excel(name = "结束训练时间", width = 15)
    private java.lang.String finishTime;
	/**结束训练内容*/
	@Excel(name = "结束训练内容", width = 15)
    private java.lang.String finishContent;
	/**课后小结*/
	@Excel(name = "课后小结", width = 15)
    private java.lang.String courseSummary;
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
