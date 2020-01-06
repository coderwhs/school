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
 * @Description: 运动员选材测试表
 * @Author: jeecg-boot
 * @Date:   2020-01-05
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_athlete_selection_test")
public class AthleteSelectionTest implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**编码*/
	@Excel(name = "编码", width = 15)
    private java.lang.String testCode;
	/**名称*/
	@Excel(name = "名称", width = 15)
    private java.lang.String testName;
	/**大项*/
	@Excel(name = "大项", width = 15)
    private java.lang.String sportCode;
	/**组别*/
	@Excel(name = "组别", width = 15)
    private java.lang.String groupId;
	/**运动员*/
	@Excel(name = "运动员", width = 15)
    private java.lang.String athleteNos;
	/**发布日期*/
	@Excel(name = "发布日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date publishDate;
	/**状态*/
	@Excel(name = "状态", width = 15)
    private java.lang.String billState;
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
