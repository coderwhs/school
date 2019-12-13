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
 * @Description: 夏训阶段训练计划信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_sport_class_summer_plan")
public class SportClassSummerPlan implements Serializable {
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
	/**比赛期开始日期*/
	@Excel(name = "比赛期开始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date contestStartDate;
	/**比赛期结束日期*/
	@Excel(name = "比赛期结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date contestEndDate;
	/**比赛期训练任务*/
	@Excel(name = "比赛期训练任务", width = 15)
    private java.lang.String contestGoal;
	/**比赛期素质训练*/
	@Excel(name = "比赛期素质训练", width = 15)
    private java.lang.String contestQualityPercent;
	/**比赛期技术训练*/
	@Excel(name = "比赛期技术训练", width = 15)
    private java.lang.String contestTechPercent;
	/**比赛期战术训练*/
	@Excel(name = "比赛期战术训练", width = 15)
    private java.lang.String contestTacticalPercent;
	/**比赛期心理训练*/
	@Excel(name = "比赛期心理训练", width = 15)
    private java.lang.String contestMentalPercent;
	/**比赛期训练手段*/
	@Excel(name = "比赛期训练手段", width = 15)
    private java.lang.String contestMethod;
	/**比赛期训练量*/
	@Excel(name = "比赛期训练量", width = 15)
    private java.lang.String contestWorkload;
	/**比赛期训练强度*/
	@Excel(name = "比赛期训练强度", width = 15)
    private java.lang.String contestIntensity;
	/**过渡期开始日期*/
	@Excel(name = "过渡期开始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date transitionStartDate;
	/**过渡期结束日期*/
	@Excel(name = "过渡期结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date transitionEndDate;
	/**过渡期训练任务*/
	@Excel(name = "过渡期训练任务", width = 15)
    private java.lang.String transitionGoal;
	/**过渡期素质训练*/
	@Excel(name = "过渡期素质训练", width = 15)
    private java.lang.String transitionQualityPercent;
	/**过渡期技术训练*/
	@Excel(name = "过渡期技术训练", width = 15)
    private java.lang.String transitionTechPercent;
	/**过渡期战术训练*/
	@Excel(name = "过渡期战术训练", width = 15)
    private java.lang.String transitionTacticalPercent;
	/**过渡期心理训练*/
	@Excel(name = "过渡期心理训练", width = 15)
    private java.lang.String transitionMentalPercent;
	/**过渡期训练手段*/
	@Excel(name = "过渡期训练手段", width = 15)
    private java.lang.String transitionMethod;
	/**过渡期训练量*/
	@Excel(name = "过渡期训练量", width = 15)
    private java.lang.String transitionWorkload;
	/**过渡期训练强度*/
	@Excel(name = "过渡期训练强度", width = 15)
    private java.lang.String transitionIntensity;
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
