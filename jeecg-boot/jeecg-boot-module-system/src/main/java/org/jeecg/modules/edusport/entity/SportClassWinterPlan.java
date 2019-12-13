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
 * @Description: 冬训阶段训练计划信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_sport_class_winter_plan")
public class SportClassWinterPlan implements Serializable {
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
	/**准备期开始日期*/
	@Excel(name = "准备期开始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date prepareStartDate;
	/**准备期结束日期*/
	@Excel(name = "准备期结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date prepareEndDate;
	/**准备期训练任务*/
	@Excel(name = "准备期训练任务", width = 15)
    private java.lang.String prepareGoal;
	/**准备期素质训练*/
	@Excel(name = "准备期素质训练", width = 15)
    private java.lang.String prepareQualityPercent;
	/**准备期技术训练*/
	@Excel(name = "准备期技术训练", width = 15)
    private java.lang.String prepareTechPercent;
	/**准备期战术训练*/
	@Excel(name = "准备期战术训练", width = 15)
    private java.lang.String prepareTacticalPercent;
	/**准备期心理训练*/
	@Excel(name = "准备期心理训练", width = 15)
    private java.lang.String prepareMentalPercent;
	/**准备期训练手段*/
	@Excel(name = "准备期训练手段", width = 15)
    private java.lang.String prepareMethod;
	/**准备期训练量*/
	@Excel(name = "准备期训练量", width = 15)
    private java.lang.String prepareWorkload;
	/**准备期训练强度*/
	@Excel(name = "准备期训练强度", width = 15)
    private java.lang.String prepareIntensity;
	/**基本期开始日期*/
	@Excel(name = "基本期开始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date basicStartDate;
	/**基本期结束日期*/
	@Excel(name = "基本期结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date basicEndDate;
	/**基本期训练任务*/
	@Excel(name = "基本期训练任务", width = 15)
    private java.lang.String basicGoal;
	/**基本期素质训练*/
	@Excel(name = "基本期素质训练", width = 15)
    private java.lang.String basicQualityPercent;
	/**基本期技术训练*/
	@Excel(name = "基本期技术训练", width = 15)
    private java.lang.String basicTechPercent;
	/**基本期战术训练*/
	@Excel(name = "基本期战术训练", width = 15)
    private java.lang.String basicTacticalPercent;
	/**基本期心理训练*/
	@Excel(name = "基本期心理训练", width = 15)
    private java.lang.String basicMentalPercent;
	/**基本期训练手段*/
	@Excel(name = "基本期训练手段", width = 15)
    private java.lang.String basicMethod;
	/**基本期训练量*/
	@Excel(name = "基本期训练量", width = 15)
    private java.lang.String basicWorkload;
	/**基本期训练强度*/
	@Excel(name = "基本期训练强度", width = 15)
    private java.lang.String basicIntensity;
	/**结束期开始日期*/
	@Excel(name = "结束期开始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date finishStartDate;
	/**结束期结束日期*/
	@Excel(name = "结束期结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date finishEndDate;
	/**结束期训练任务*/
	@Excel(name = "结束期训练任务", width = 15)
    private java.lang.String finishGoal;
	/**结束期素质训练*/
	@Excel(name = "结束期素质训练", width = 15)
    private java.lang.String finishQualityPercent;
	/**结束期技术训练*/
	@Excel(name = "结束期技术训练", width = 15)
    private java.lang.String finishTechPercent;
	/**结束期战术训练*/
	@Excel(name = "结束期战术训练", width = 15)
    private java.lang.String finishTacticalPercent;
	/**结束期心理训练*/
	@Excel(name = "结束期心理训练", width = 15)
    private java.lang.String finishMentalPercent;
	/**结束期训练手段*/
	@Excel(name = "结束期训练手段", width = 15)
    private java.lang.String finishMethod;
	/**结束期训练量*/
	@Excel(name = "结束期训练量", width = 15)
    private java.lang.String finishWorkload;
	/**结束期训练强度*/
	@Excel(name = "结束期训练强度", width = 15)
    private java.lang.String finishIntensity;
	/**竞赛科评价*/
	@Excel(name = "竞赛科评价", width = 15)
    private java.lang.String deptEvaluation;
	/**竞赛科评价日期*/
	@Excel(name = "竞赛科评价日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date deptEvaluationDate;
	/**校领导评价*/
	@Excel(name = "校领导评价", width = 15)
    private java.lang.String schoolEvaluation;
	/**校领导评价日期*/
	@Excel(name = "校领导评价日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date schoolEvaluationDate;
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
	/**竞赛科领导*/
	@Excel(name = "竞赛科领导", width = 15)
    private java.lang.String deptEvaluationer;
	/**校领导*/
	@Excel(name = "校领导", width = 15)
    private java.lang.String schoolEvaluationer;
}
