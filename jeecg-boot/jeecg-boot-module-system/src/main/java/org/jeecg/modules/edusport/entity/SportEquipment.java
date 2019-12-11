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
 * @Description: 运动器材信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_sport_equipment")
public class SportEquipment implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**运动项目*/
	@Excel(name = "运动项目", width = 15)
    private java.lang.String sportCode;
	/**运动项目*/
	@Excel(name = "运动项目", width = 15)
    private java.lang.String sportName;
	/**器材分类*/
	@Excel(name = "器材分类", width = 15)
    private java.lang.String equipmentType;
	/**器材序列号*/
	@Excel(name = "器材序列号", width = 15)
    private java.lang.String equipmentSn;
	/**器材名称*/
	@Excel(name = "器材名称", width = 15)
    private java.lang.String equipmentName;
	/**器材价格*/
	@Excel(name = "器材价格", width = 15)
    private java.lang.String equipmentPrice;
	/**存放地点*/
	@Excel(name = "存放地点", width = 15)
    private java.lang.String storeLocation;
	/**库存数量*/
	@Excel(name = "库存数量", width = 15)
    private java.lang.Integer inventoryQty;
	/**可借数量*/
	@Excel(name = "可借数量", width = 15)
    private java.lang.String availableQty;
	/**借出数量*/
	@Excel(name = "借出数量", width = 15)
    private java.lang.String lendingQty;
	/**预定数量*/
	@Excel(name = "预定数量", width = 15)
    private java.lang.String bookQty;
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
