package org.jeecg.modules.edusport.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
 * @Description: 运动员选材测试教练表
 * @Author: jeecg-boot
 * @Date:   2020-02-20
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_athlete_selection_test_coach AS t")
public class AthleteSelectionTestCoach implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**所属测试*/
	@Excel(name = "所属测试", width = 15)
    private java.lang.String testId;
	/**教练员*/
	@Excel(name = "教练员", width = 15)
    private java.lang.String coachId;
	/**测试组别*/
	@Excel(name = "测试组别", width = 15)
    private java.lang.String groupId;
	/**测试项目*/
	@Excel(name = "测试项目", width = 15)
    private java.lang.String sportCode;
	/**测试小项*/
	@Excel(name = "测试小项", width = 15)
    private java.lang.String eventCodes;
	/**测试指标*/
	@Excel(name = "测试指标", width = 15)
    private java.lang.String indexCodes;
	/**测试日期*/
	@Excel(name = "测试日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date testDate;
	/**单据状态*/
	@Excel(name = "单据状态", width = 15)
    private java.lang.String status;
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
    private java.lang.String athleteIds;
	/**测试等级评定*/
	@TableField(exist = false)
    private java.lang.String testRatings;
	/**测试名称*/
	@TableField(exist = false)
	private java.lang.String testName;
	/**测试教练*/
	@TableField(exist = false)
	private java.lang.String coachName;
	/**测试项目*/
	@TableField(exist = false)
	private java.lang.String sportName;
	/**测试小项*/
	@TableField(exist = false)
	private java.lang.String eventName;
	/**测试指标*/
	@TableField(exist = false)
	private java.lang.String indexName;
	
}
