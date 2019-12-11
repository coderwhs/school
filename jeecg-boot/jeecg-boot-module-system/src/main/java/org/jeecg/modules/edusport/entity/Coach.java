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
 * @Description: 教练员信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_coach")
public class Coach implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**教练员代码*/
	@Excel(name = "教练员代码", width = 15)
    private java.lang.String coachNo;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    private java.lang.String coachName;
	/**性别*/
	@Excel(name = "性别", width = 15)
    private java.lang.String gender;
	/**民族*/
	@Excel(name = "民族", width = 15)
    private java.lang.String nation;
	/**出生日期*/
	@Excel(name = "出生日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date birthDate;
	/**身份证号*/
	@Excel(name = "身份证号", width = 15)
    private java.lang.String idNo;
	/**籍贯省*/
	@Excel(name = "籍贯省", width = 15)
    private java.lang.String nativeProvince;
	/**籍贯市*/
	@Excel(name = "籍贯市", width = 15)
    private java.lang.String nativeCity;
	/**政治面貌*/
	@Excel(name = "政治面貌", width = 15)
    private java.lang.String politicalStatus;
	/**家庭地址*/
	@Excel(name = "家庭地址", width = 15)
    private java.lang.String homeAddress;
	/**手机号码*/
	@Excel(name = "手机号码", width = 15)
    private java.lang.String mobile;
	/**学历*/
	@Excel(name = "学历", width = 15)
    private java.lang.String eduBackground;
	/**学位*/
	@Excel(name = "学位", width = 15)
    private java.lang.String eduDegree;
	/**编制类型*/
	@Excel(name = "编制类型", width = 15)
    private java.lang.String staffType;
	/**聘用日期*/
	@Excel(name = "聘用日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date hireDate;
	/**聘用年限*/
	@Excel(name = "聘用年限", width = 15)
    private java.lang.Integer hireYears;
	/**何年毕业于何学校专业*/
	@Excel(name = "何年毕业于何学校专业", width = 15)
    private java.lang.String marjorInfo;
	/**初任教练员时间*/
	@Excel(name = "初任教练员时间", width = 15)
    private java.lang.String coachStartDate;
	/**三级教练员聘任时间*/
	@Excel(name = "三级教练员聘任时间", width = 15)
    private java.lang.String l3AppointmentDate;
	/**二级教练员聘任时间*/
	@Excel(name = "二级教练员聘任时间", width = 15)
    private java.lang.String l2AppointmentDate;
	/**一级教练员聘任时间*/
	@Excel(name = "一级教练员聘任时间", width = 15)
    private java.lang.String l1AppointmentDate;
	/**取得高级教练员资格时间*/
	@Excel(name = "取得高级教练员资格时间", width = 15)
    private java.lang.String ladvCertDate;
	/**高级教练员聘任时间*/
	@Excel(name = "高级教练员聘任时间", width = 15)
    private java.lang.String ladvAppointmentDate;
	/**取得国家级教练员资格时间*/
	@Excel(name = "取得国家级教练员资格时间", width = 15)
    private java.lang.String lcnCertDate;
	/**国家级教练员聘任时间*/
	@Excel(name = "国家级教练员聘任时间", width = 15)
    private java.lang.String lcnAppointmentDate;
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
