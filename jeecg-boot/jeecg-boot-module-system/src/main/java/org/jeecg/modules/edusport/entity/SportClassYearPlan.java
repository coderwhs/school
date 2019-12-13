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
 * @Description: 年度训练计划信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_sport_class_year_plan")
public class SportClassYearPlan implements Serializable {
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
	/**初始情况*/
	@Excel(name = "初始情况", width = 15)
    private java.lang.String teamSituation;
	/**全年任务*/
	@Excel(name = "全年任务", width = 15)
    private java.lang.String yearGoal;
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
	/**准备期周数*/
	@Excel(name = "准备期周数", width = 15)
    private java.lang.String prepareWeeks;
	/**准备期天数*/
	@Excel(name = "准备期天数", width = 15)
    private java.lang.String prepareDays;
	/**准备期训练任务*/
	@Excel(name = "准备期训练任务", width = 15)
    private java.lang.String prepareGoal;
	/**准备期身体训练*/
	@Excel(name = "准备期身体训练", width = 15)
    private java.lang.String prepareBodyPercent;
	/**准备期素质训练*/
	@Excel(name = "准备期素质训练", width = 15)
    private java.lang.String prepareQualityPercent;
	/**准备期技术训练*/
	@Excel(name = "准备期技术训练", width = 15)
    private java.lang.String prepareTechPercent;
	/**准备期其他训练*/
	@Excel(name = "准备期其他训练", width = 15)
    private java.lang.String prepareOtherPercent;
	/**准备期训练手段*/
	@Excel(name = "准备期训练手段", width = 15)
    private java.lang.String prepareMethod;
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
	/**比赛期周数*/
	@Excel(name = "比赛期周数", width = 15)
    private java.lang.String contestWeeks;
	/**比赛期天数*/
	@Excel(name = "比赛期天数", width = 15)
    private java.lang.String contestDays;
	/**比赛期训练任务*/
	@Excel(name = "比赛期训练任务", width = 15)
    private java.lang.String contestGoal;
	/**比赛期身体训练*/
	@Excel(name = "比赛期身体训练", width = 15)
    private java.lang.String contestBodyPercent;
	/**比赛期素质训练*/
	@Excel(name = "比赛期素质训练", width = 15)
    private java.lang.String contestQualityPercent;
	/**比赛期技术训练*/
	@Excel(name = "比赛期技术训练", width = 15)
    private java.lang.String contestTechPercent;
	/**比赛期其他训练*/
	@Excel(name = "比赛期其他训练", width = 15)
    private java.lang.String contestOtherPercent;
	/**比赛期训练手段*/
	@Excel(name = "比赛期训练手段", width = 15)
    private java.lang.String contestMethod;
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
	/**过渡期周数*/
	@Excel(name = "过渡期周数", width = 15)
    private java.lang.String transitionWeeks;
	/**过渡期天数*/
	@Excel(name = "过渡期天数", width = 15)
    private java.lang.String transitionDays;
	/**过渡期训练任务*/
	@Excel(name = "过渡期训练任务", width = 15)
    private java.lang.String transitionGoal;
	/**过渡期身体训练*/
	@Excel(name = "过渡期身体训练", width = 15)
    private java.lang.String transitionBodyPercent;
	/**过渡期素质训练*/
	@Excel(name = "过渡期素质训练", width = 15)
    private java.lang.String transitionQualityPercent;
	/**过渡期技术训练*/
	@Excel(name = "过渡期技术训练", width = 15)
    private java.lang.String transitionTechPercent;
	/**过渡期其他训练*/
	@Excel(name = "过渡期其他训练", width = 15)
    private java.lang.String transitionOtherPercent;
	/**过渡期训练手段*/
	@Excel(name = "过渡期训练手段", width = 15)
    private java.lang.String transitionMethod;
	/**1月训练量*/
	@Excel(name = "1月训练量", width = 15)
    private java.lang.Integer m1Workload;
	/**2月训练量*/
	@Excel(name = "2月训练量", width = 15)
    private java.lang.Integer m2Workload;
	/**3月训练量*/
	@Excel(name = "3月训练量", width = 15)
    private java.lang.Integer m3Workload;
	/**4月训练量*/
	@Excel(name = "4月训练量", width = 15)
    private java.lang.Integer m4Workload;
	/**5月训练量*/
	@Excel(name = "5月训练量", width = 15)
    private java.lang.Integer m5Workload;
	/**6月训练量*/
	@Excel(name = "6月训练量", width = 15)
    private java.lang.Integer m6Workload;
	/**7月训练量*/
	@Excel(name = "7月训练量", width = 15)
    private java.lang.Integer m7Workload;
	/**8月训练量*/
	@Excel(name = "8月训练量", width = 15)
    private java.lang.Integer m8Workload;
	/**9月训练量*/
	@Excel(name = "9月训练量", width = 15)
    private java.lang.Integer m9Workload;
	/**10月训练量*/
	@Excel(name = "10月训练量", width = 15)
    private java.lang.Integer m10Workload;
	/**11月训练量*/
	@Excel(name = "11月训练量", width = 15)
    private java.lang.Integer m11Workload;
	/**12月训练量*/
	@Excel(name = "12月训练量", width = 15)
    private java.lang.Integer m12Workload;
	/**1月训练强度*/
	@Excel(name = "1月训练强度", width = 15)
    private java.lang.Integer m1Intensity;
	/**2月训练强度*/
	@Excel(name = "2月训练强度", width = 15)
    private java.lang.Integer m2Intensity;
	/**3月训练强度*/
	@Excel(name = "3月训练强度", width = 15)
    private java.lang.Integer m3Intensity;
	/**4月训练强度*/
	@Excel(name = "4月训练强度", width = 15)
    private java.lang.Integer m4Intensity;
	/**5月训练强度*/
	@Excel(name = "5月训练强度", width = 15)
    private java.lang.Integer m5Intensity;
	/**6月训练强度*/
	@Excel(name = "6月训练强度", width = 15)
    private java.lang.Integer m6Intensity;
	/**7月训练强度*/
	@Excel(name = "7月训练强度", width = 15)
    private java.lang.Integer m7Intensity;
	/**8月训练强度*/
	@Excel(name = "8月训练强度", width = 15)
    private java.lang.Integer m8Intensity;
	/**9月训练强度*/
	@Excel(name = "9月训练强度", width = 15)
    private java.lang.Integer m9Intensity;
	/**10月训练强度*/
	@Excel(name = "10月训练强度", width = 15)
    private java.lang.Integer m10Intensity;
	/**11月训练强度*/
	@Excel(name = "11月训练强度", width = 15)
    private java.lang.Integer m11Intensity;
	/**12月训练强度*/
	@Excel(name = "12月训练强度", width = 15)
    private java.lang.Integer m12Intensity;
	/**考核安排*/
	@Excel(name = "考核安排", width = 15)
    private java.lang.String performanceCheck;
	/**指标检查标准*/
	@Excel(name = "指标检查标准", width = 15)
    private java.lang.String targetCheck;
	/**实施措施*/
	@Excel(name = "实施措施", width = 15)
    private java.lang.String actionSteps;
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
