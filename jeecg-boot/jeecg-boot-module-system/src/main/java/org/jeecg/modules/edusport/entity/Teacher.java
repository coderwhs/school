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
 * @Description: 教练员信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_teacher")
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**教师代码*/
	@Excel(name = "教师代码", width = 15)
    private java.lang.String teacherNo;
	/**教师姓名*/
	@Excel(name = "教师姓名", width = 15)
    private java.lang.String teacherName;
	/**性别*/
	@Excel(name = "性别", width = 15)
    private java.lang.String gender;
	/**出生日期*/
	@Excel(name = "出生日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date birthDate;
	/**民族*/
	@Excel(name = "民族", width = 15)
    private java.lang.String nation;
	/**籍贯省*/
	@Excel(name = "籍贯省", width = 15)
    private java.lang.String nativeProvince;
	/**籍贯市*/
	@Excel(name = "籍贯市", width = 15)
    private java.lang.String nativeCity;
	/**证件类型*/
	@Excel(name = "证件类型", width = 15)
    private java.lang.String idType;
	/**证件号码*/
	@Excel(name = "证件号码", width = 15)
    private java.lang.String idNo;
	/**手机号码*/
	@Excel(name = "手机号码", width = 15)
    private java.lang.String mobile;
	/**学院代码*/
	@Excel(name = "学院代码", width = 15)
    private java.lang.String collegeCode;
	/**专业代码*/
	@Excel(name = "专业代码", width = 15)
    private java.lang.String majorCode;
	/**职称*/
	@Excel(name = "职称", width = 15)
    private java.lang.String title;
	/**职务*/
	@Excel(name = "职务", width = 15)
    private java.lang.String duty;
	/**家庭电话*/
	@Excel(name = "家庭电话", width = 15)
    private java.lang.String homeTel;
	/**家庭地址*/
	@Excel(name = "家庭地址", width = 15)
    private java.lang.String homeAddress;
	/**照片类型*/
	@Excel(name = "照片类型", width = 15)
    private java.lang.String photoType;
	/**照片名称*/
	@Excel(name = "照片名称", width = 15)
    private java.lang.String photoName;
	/**照片路径*/
	@Excel(name = "照片路径", width = 15)
    private java.lang.String photoPath;
	/**照片上传时间*/
	@Excel(name = "照片上传时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date uploadDate;
	/**乐观锁*/
	@Excel(name = "乐观锁", width = 15)
    private java.lang.Integer revision;
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
	/**删除标志*/
	@Excel(name = "删除标志", width = 15)
    private java.lang.String delFlat;
}
