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
 * @Description: 宿舍信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-12
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_dorm")
public class Dorm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**宿舍楼名称*/
	@Excel(name = "宿舍楼名称", width = 15)
    private java.lang.String dormBuildingName;
	/**房间号*/
	@Excel(name = "房间号", width = 15)
    private java.lang.String dormNo;
	/**床位数*/
	@Excel(name = "床位数", width = 15)
    private java.lang.Integer bedNum;
	/**宿舍电话*/
	@Excel(name = "宿舍电话", width = 15)
    private java.lang.String dormTel;
	/**管理员*/
	@Excel(name = "管理员", width = 15)
    private java.lang.String dormAdmin;
	/**管理员电话*/
	@Excel(name = "管理员电话", width = 15)
    private java.lang.String dormAdminTel;
	/**地址*/
	@Excel(name = "地址", width = 15)
    private java.lang.String dormAddress;
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
