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
 * @Description: 周训练计划信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_sport_class_week_plan")
public class SportClassWeekPlan implements Serializable {
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
	/**周开始日期*/
	@Excel(name = "周开始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date startDate;
	/**周结束日期*/
	@Excel(name = "周结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date endTime;
	/**目的任务*/
	@Excel(name = "目的任务", width = 15)
    private java.lang.String taskGoal;
	/**周一训练量*/
	@Excel(name = "周一训练量", width = 15)
    private java.lang.String monWorkload;
	/**周一训练强度*/
	@Excel(name = "周一训练强度", width = 15)
    private java.lang.String monIntensity;
	/**周一训练时间*/
	@Excel(name = "周一训练时间", width = 15)
    private java.lang.String monTime;
	/**周一训练内容*/
	@Excel(name = "周一训练内容", width = 15)
    private java.lang.String monContent;
	/**周二训练量*/
	@Excel(name = "周二训练量", width = 15)
    private java.lang.String tueWorkload;
	/**周二训练强度*/
	@Excel(name = "周二训练强度", width = 15)
    private java.lang.String tueIntensity;
	/**周二训练时间*/
	@Excel(name = "周二训练时间", width = 15)
    private java.lang.String tueTime;
	/**周二训练内容*/
	@Excel(name = "周二训练内容", width = 15)
    private java.lang.String tueContent;
	/**周三训练量*/
	@Excel(name = "周三训练量", width = 15)
    private java.lang.String wedWorkload;
	/**周三训练强度*/
	@Excel(name = "周三训练强度", width = 15)
    private java.lang.String wedIntensity;
	/**周三训练时间*/
	@Excel(name = "周三训练时间", width = 15)
    private java.lang.String wedTime;
	/**周三训练内容*/
	@Excel(name = "周三训练内容", width = 15)
    private java.lang.String wedContent;
	/**周四训练量*/
	@Excel(name = "周四训练量", width = 15)
    private java.lang.String thuWorkload;
	/**周四训练强度*/
	@Excel(name = "周四训练强度", width = 15)
    private java.lang.String thuIntensity;
	/**周四训练时间*/
	@Excel(name = "周四训练时间", width = 15)
    private java.lang.String thuTime;
	/**周四训练内容*/
	@Excel(name = "周四训练内容", width = 15)
    private java.lang.String thuContent;
	/**周五训练量*/
	@Excel(name = "周五训练量", width = 15)
    private java.lang.String friWorkload;
	/**周五训练强度*/
	@Excel(name = "周五训练强度", width = 15)
    private java.lang.String friIntensity;
	/**周五训练时间*/
	@Excel(name = "周五训练时间", width = 15)
    private java.lang.String friTime;
	/**周五训练内容*/
	@Excel(name = "周五训练内容", width = 15)
    private java.lang.String friContent;
	/**周六训练量*/
	@Excel(name = "周六训练量", width = 15)
    private java.lang.String satWorkload;
	/**周六训练强度*/
	@Excel(name = "周六训练强度", width = 15)
    private java.lang.String satIntensity;
	/**周六训练时间*/
	@Excel(name = "周六训练时间", width = 15)
    private java.lang.String satTime;
	/**周六训练内容*/
	@Excel(name = "周六训练内容", width = 15)
    private java.lang.String satContent;
	/**周日训练量*/
	@Excel(name = "周日训练量", width = 15)
    private java.lang.String sunWorkload;
	/**周日训练强度*/
	@Excel(name = "周日训练强度", width = 15)
    private java.lang.String sunIntensity;
	/**周日训练时间*/
	@Excel(name = "周日训练时间", width = 15)
    private java.lang.String sunTime;
	/**周日训练内容*/
	@Excel(name = "周日训练内容", width = 15)
    private java.lang.String sunContent;
	/**本周完成训练天数*/
	@Excel(name = "本周完成训练天数", width = 15)
    private java.lang.String weekDays;
	/**本周完成训练课数*/
	@Excel(name = "本周完成训练课数", width = 15)
    private java.lang.String weekCourses;
	/**本周完成训练时数*/
	@Excel(name = "本周完成训练时数", width = 15)
    private java.lang.String weekHours;
	/**本周运动量完成情况*/
	@Excel(name = "本周运动量完成情况", width = 15)
    private java.lang.String weekWorkload;
	/**本周训练小结*/
	@Excel(name = "本周训练小结", width = 15)
    private java.lang.String weekSummary;
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
