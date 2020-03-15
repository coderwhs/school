package org.jeecg.modules.edusport.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 运动员选材测试运动员成绩表
 * @Author: jeecg-boot
 * @Date:   2020-02-20
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_athlete_selection_test_athlete_score")
public class AthleteSelectionTestAthleteScore implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**所属测试教练*/
	@Excel(name = "所属测试教练", width = 15)
    private java.lang.String testCoachId;
	/**运动员*/
	@Excel(name = "运动员", width = 15)
    private java.lang.String athleteId;
	/**性别*/
	@Excel(name = "性别", width = 15)
    private java.lang.String gender;
	/**年级*/
	@Excel(name = "年级", width = 15)
    private java.lang.String grade;
	/**出生年月*/
	@Excel(name = "出生年月", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date birthDate;
	/**年龄*/
	@Excel(name = "年龄", width = 15)
    private java.lang.Integer age;
	/**总成绩*/
	@Excel(name = "总成绩", width = 15)
    private java.lang.Integer score;
	/**测试等级评定*/
	@Excel(name = "测试等级评定", width = 15)
    private java.lang.String testRating;
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
	
	
	/**运动员*/
	@TableField(exist = false)
    private java.lang.String athleteName;
	/**性别*/
	@TableField(exist = false)
    private java.lang.String genderName;
	/**年级*/
	@TableField(exist = false)
    private java.lang.String gradeName;
	
	/**成绩明细*/
	@TableField(exist = false)
	ArrayList<AthleteSelectionTestAthleteScoreDetail> testAthleteScoreDetailList;
	
	
}
