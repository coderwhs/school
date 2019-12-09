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
 * @Description: 运动员参赛信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_athlete_contest")
public class AthleteContest implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**运动员*/
	@Excel(name = "运动员", width = 15)
    private java.lang.String studentNo;
	/**参赛名称*/
	@Excel(name = "参赛名称", width = 15)
    private java.lang.String contestName;
	/**参赛日期*/
	@Excel(name = "参赛日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date contestDate;
	/**参赛地点*/
	@Excel(name = "参赛地点", width = 15)
    private java.lang.String contestAddress;
	/**参赛项目*/
	@Excel(name = "参赛项目", width = 15)
    private java.lang.String contestSport;
	/**参赛小项*/
	@Excel(name = "参赛小项", width = 15)
    private java.lang.String contestEvent;
	/**参赛成绩*/
	@Excel(name = "参赛成绩", width = 15)
    private java.lang.String contestScore;
	/**参赛名次*/
	@Excel(name = "参赛名次", width = 15)
    private java.lang.String contestResult;
	/**所在单位*/
	@Excel(name = "所在单位", width = 15)
    private java.lang.String studentDepartment;
	/**授予技术等级*/
	@Excel(name = "授予技术等级", width = 15)
    private java.lang.String awardedTechGrade;
	/**授予单位*/
	@Excel(name = "授予单位", width = 15)
    private java.lang.String awardedDepartment;
	/**授予日期*/
	@Excel(name = "授予日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date awardedDate;
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
