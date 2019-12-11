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
 * @Description: 运动员文化课成绩信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_athlete_literacy_score")
public class AthleteLiteracyScore implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**运动员学号*/
	@Excel(name = "运动员学号", width = 15)
    private java.lang.String athleteNo;
	/**年级*/
	@Excel(name = "年级", width = 15)
    private java.lang.String grade;
	/**教学年度*/
	@Excel(name = "教学年度", width = 15)
    private java.lang.String academicYear;
	/**学期*/
	@Excel(name = "学期", width = 15)
    private java.lang.String semester;
	/**语文*/
	@Excel(name = "语文", width = 15)
    private java.math.BigDecimal chineseScore;
	/**数学*/
	@Excel(name = "数学", width = 15)
    private java.math.BigDecimal mathsScore;
	/**英语*/
	@Excel(name = "英语", width = 15)
    private java.math.BigDecimal englishScore;
	/**政治*/
	@Excel(name = "政治", width = 15)
    private java.math.BigDecimal politicsScore;
	/**物理*/
	@Excel(name = "物理", width = 15)
    private java.math.BigDecimal physicsScore;
	/**化学*/
	@Excel(name = "化学", width = 15)
    private java.math.BigDecimal chemistryScore;
	/**历史*/
	@Excel(name = "历史", width = 15)
    private java.math.BigDecimal historyScore;
	/**地理*/
	@Excel(name = "地理", width = 15)
    private java.math.BigDecimal geographyScore;
	/**生物*/
	@Excel(name = "生物", width = 15)
    private java.math.BigDecimal biologyScore;
	/**音乐*/
	@Excel(name = "音乐", width = 15)
    private java.math.BigDecimal musicScore;
	/**美术*/
	@Excel(name = "美术", width = 15)
    private java.math.BigDecimal artScore;
	/**体育与健康*/
	@Excel(name = "体育与健康", width = 15)
    private java.math.BigDecimal peScore;
	/**信息技术*/
	@Excel(name = "信息技术", width = 15)
    private java.math.BigDecimal itScore;
	/**通用技术*/
	@Excel(name = "通用技术", width = 15)
    private java.math.BigDecimal gtScore;
	/**历史与社会*/
	@Excel(name = "历史与社会", width = 15)
    private java.math.BigDecimal societyScore;
	/**科学*/
	@Excel(name = "科学", width = 15)
    private java.math.BigDecimal scienceScore;
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
