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
 * @Description: 训练队表
 * @Author: jeecg-boot
 * @Date:   2019-12-12
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_sport_class")
public class SportClass implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**训练队名称*/
	@Excel(name = "训练队名称", width = 15)
    private java.lang.String className;
	/**教练员*/
	@Excel(name = "教练员", width = 15)
    private java.lang.String coachNo;
	/**运动项目*/
	@Excel(name = "运动项目", width = 15)
    private java.lang.String sportCode;
	/**训练阶段*/
	@Excel(name = "训练阶段", width = 15)
    private java.lang.String trainingStage;
	/**训练形式*/
	@Excel(name = "训练形式", width = 15)
    private java.lang.String trainingType;
	/**训练年度*/
	@Excel(name = "训练年度", width = 15)
    private java.lang.Integer trainingYear;
	/**开始日期*/
	@Excel(name = "开始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date startDate;
	/**结束日期*/
	@Excel(name = "结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date endDate;
	/**训练地点*/
	@Excel(name = "训练地点", width = 15)
    private java.lang.String trainingAddress;
	/**训练任务*/
	@Excel(name = "训练任务", width = 15)
    private java.lang.String trainingContent;
	/**备注*/
	@Excel(name = "备注", width = 15)
    private java.lang.String remark;
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
