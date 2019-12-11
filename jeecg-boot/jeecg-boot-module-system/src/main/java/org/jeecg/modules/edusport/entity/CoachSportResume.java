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
 * @Description: 教练员运动经历信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_coach_sport_resume")
public class CoachSportResume implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**教练员代码*/
	@Excel(name = "教练员代码", width = 15)
    private java.lang.String coachNo;
	/**运动项目*/
	@Excel(name = "运动项目", width = 15)
    private java.lang.String sportName;
	/**参加时间*/
	@Excel(name = "参加时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date attendDate;
	/**获得最高等级*/
	@Excel(name = "获得最高等级", width = 15)
    private java.lang.String awardedHighestGrade;
	/**获得年度(年)*/
	@Excel(name = "获得年度(年)", width = 15)
    private java.lang.String awardedDate;
	/**最高训练单位*/
	@Excel(name = "最高训练单位", width = 15)
    private java.lang.String highestTrainingUnit;
	/**训练年度(年)*/
	@Excel(name = "训练年度(年)", width = 15)
    private java.lang.String trainingDate;
	/**比赛小项*/
	@Excel(name = "比赛小项", width = 15)
    private java.lang.String contestEvent;
	/**最好成绩*/
	@Excel(name = "最好成绩", width = 15)
    private java.lang.String contestScore;
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
