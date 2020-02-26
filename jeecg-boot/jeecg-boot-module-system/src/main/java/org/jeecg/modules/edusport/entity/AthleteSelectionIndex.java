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
 * @Description: 运动员选材指标信息表
 * @Author: jeecg-boot
 * @Date:   2020-02-18
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_athlete_selection_index")
public class AthleteSelectionIndex implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**指标类别*/
	@Excel(name = "指标类别", width = 15)
    private java.lang.String indexCatCode;
	/**上级指标代码*/
	@Excel(name = "上级指标代码", width = 15)
    private java.lang.String parentCode;
	/**指标代码*/
	@Excel(name = "指标代码", width = 15)
    private java.lang.String indexCode;
	/**是否叶节点*/
	@Excel(name = "是否叶节点", width = 15)
    private java.lang.String isLeaf;
	/**中文名称*/
	@Excel(name = "中文名称", width = 15)
    private java.lang.String cnName;
	/**英文名称*/
	@Excel(name = "英文名称", width = 15)
    private java.lang.String enName;
	/**英文简称*/
	@Excel(name = "英文简称", width = 15)
    private java.lang.String enShortName;
	/**单位*/
	@Excel(name = "单位", width = 15)
    private java.lang.String unit;
	/**启用状态*/
	@Excel(name = "启用状态", width = 15)
    private java.lang.String enableStatus;
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
