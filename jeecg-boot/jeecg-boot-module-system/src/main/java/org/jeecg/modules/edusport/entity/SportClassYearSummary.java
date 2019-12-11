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
 * @Description: 年度训练工作总结信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_sport_class_year_summary")
public class SportClassYearSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**训练班*/
	@Excel(name = "训练班", width = 15)
    private java.lang.String sportClassId;
	/**训练计划名称*/
	@Excel(name = "训练计划名称", width = 15)
    private java.lang.String taskName;
	/**发布人*/
	@Excel(name = "发布人", width = 15)
    private java.lang.String coachNo;
	/**执行情况*/
	@Excel(name = "执行情况", width = 15)
    private java.lang.String yearPerformance;
	/**全年任务*/
	@Excel(name = "全年任务", width = 15)
    private java.lang.String yearWeakness;
	/**改进方向*/
	@Excel(name = "改进方向", width = 15)
    private java.lang.String improvement;
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
}
