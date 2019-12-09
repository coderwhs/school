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
 * @Description: 运动员训练班经历信息
 * @Author: jeecg-boot
 * @Date:   2019-11-26
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_athlete_sport_class")
public class AthleteSportClass implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**训练班*/
	@Excel(name = "训练班", width = 15)
    private java.lang.String sportClassId;
	/**教练员*/
	@Excel(name = "教练员", width = 15)
    private java.lang.String teacherName;
	/**运动员*/
	@Excel(name = "运动员", width = 15)
    private java.lang.String studentNo;
	/**参加日期*/
	@Excel(name = "参加日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date attendDate;
	/**比赛最好成绩*/
	@Excel(name = "比赛最好成绩", width = 15)
    private java.lang.String athleteBestScore;
	/**获得等级*/
	@Excel(name = "获得等级", width = 15)
    private java.lang.String athleteAwardTechGrade;
	/**破纪录状况*/
	@Excel(name = "破纪录状况", width = 15)
    private java.lang.String aboutBreakRecord;
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
