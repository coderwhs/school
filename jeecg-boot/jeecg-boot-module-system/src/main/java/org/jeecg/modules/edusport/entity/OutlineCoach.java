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
 * @Description: tb_edu_outline_coach
 * @Author: jeecg-boot
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_outline_coach")
public class OutlineCoach implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**教练员*/
	@Excel(name = "教练员", width = 15)
    private java.lang.String coachId;
	/**大纲*/
	@Excel(name = "大纲", width = 15)
    private java.lang.String outlineId;
	/**大纲组别*/
	@Excel(name = "大纲组别", width = 15)
    private java.lang.String groupId;
	/**运动项目*/
	@Excel(name = "运动项目", width = 15)
    private java.lang.String sportId;
	/**运动员*/
	@Excel(name = "运动员", width = 15)
    private java.lang.String althleteNos;
	/**测试项目*/
	@Excel(name = "测试项目", width = 15)
    private java.lang.String eventCodes;
	/**指标*/
	@Excel(name = "指标", width = 15)
    private java.lang.String indexCodes;
	/**测试日期*/
	@Excel(name = "测试日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date testDate;
	/**单据状态*/
	@Excel(name = "单据状态", width = 15)
    private java.lang.String state;
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
