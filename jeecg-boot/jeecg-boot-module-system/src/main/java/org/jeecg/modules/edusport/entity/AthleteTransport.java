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
 * @Description: 运动员输送表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_athlete_transport")
public class AthleteTransport implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**运动员学号*/
	@Excel(name = "运动员学号", width = 15)
    private java.lang.String athleteNo;
	/**运动项目代码*/
	@Excel(name = "运动项目代码", width = 15)
    private java.lang.String sportCode;
	/**运动员等级*/
	@Excel(name = "运动员等级", width = 15)
    private java.lang.String athleteTechGrade;
	/**输送单位*/
	@Excel(name = "输送单位", width = 15)
    private java.lang.String transportDepartment;
	/**输送教练员代码*/
	@Excel(name = "输送教练员代码", width = 15)
    private java.lang.String transportCoachNo;
	/**输送时间*/
	@Excel(name = "输送时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date transportDate;
	/**吸收单位类别*/
	@Excel(name = "吸收单位类别", width = 15)
    private java.lang.String acceptDepartmentType;
	/**吸收单位*/
	@Excel(name = "吸收单位", width = 15)
    private java.lang.String acceptDepartment;
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
