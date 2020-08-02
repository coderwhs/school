package org.jeecg.modules.edusport.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 运动员信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_athlete")
public class Athlete implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**学号*/
	@Excel(name = "学号", width = 15)
    private java.lang.String athleteNo;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    private java.lang.String athleteName;
	/**性别*/
	@Excel(name = "性别", width = 15,dicCode="sex")
	@Dict(dicCode = "sex")
    private java.lang.String gender;
	/**民族*/
	@Excel(name = "民族", width = 15,dicCode = "nation")
	@Dict(dicCode = "nation")
    private java.lang.String nation;
	/**出生日期*/
	@Excel(name = "出生日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date birthDate;
	/**身高(cm)*/
	@Excel(name = "身高(cm)", width = 15)
    private java.lang.Integer athleteHeight;
//	/**密码*/
//	@Excel(name = "身高(cm)", width = 15)
//	private java.lang.String password;
	/**体重(kg)*/
	@Excel(name = "体重(kg)", width = 15)
    private java.math.BigDecimal athleteWeight;
	/**籍贯省*/
	@Excel(name = "籍贯省", width = 15)
    private java.lang.String nativeProvince;
	/**籍贯市*/
	@Excel(name = "籍贯市", width = 15)
    private java.lang.String nativeCity;
	/**身份证号*/
	@Excel(name = "身份证号", width = 15)
    private java.lang.String idNo;
	/**手机号码*/
	@Excel(name = "手机号码", width = 15)
    private java.lang.String mobile;
	/**家庭住址*/
	@Excel(name = "家庭住址", width = 15)
    private java.lang.String homeAddress;
	/**邮政编码*/
	@Excel(name = "邮政编码", width = 15)
    private java.lang.String zipcode;
	/**户籍所在派出所*/
	@Excel(name = "户籍所在派出所", width = 15)
    private java.lang.String policeStation;
	/**父亲姓名*/
	@Excel(name = "父亲姓名", width = 15)
    private java.lang.String father;
//	/**父亲身高*/
//	@Excel(name = "父亲身高", width = 15)
//    private java.lang.Integer fatherHeight;
	/**父亲电话*/
	@Excel(name = "父亲电话", width = 15)
    private java.lang.String fatherMobile;
	/**母亲姓名*/
	@Excel(name = "母亲姓名", width = 15)
    private java.lang.String mother;
//	/**母亲身高*/
//	@Excel(name = "母亲身高", width = 15)
//    private java.lang.Integer motherHeight;
	/**母亲电话*/
	@Excel(name = "母亲电话", width = 15)
    private java.lang.String motherMobile;
	/**输送单位区县*/
	@Excel(name = "输送单位区县", width = 15)
    private java.lang.String transportDepartmentCity;
	/**输送单位名称*/
	@Excel(name = "输送单位名称", width = 15)
    private java.lang.String transportDepartmentSchool;
	/**入学日期*/
	@Excel(name = "入学日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date admissionDate;
	/**就读年级*/
	@Excel(name = "就读年级", width = 15)
    private java.lang.String grade;
	/**班主任*/
	@Excel(name = "班主任", width = 15)
    private java.lang.String classTeacherId;
	/**入队时间*/
	@Excel(name = "入队时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date majorSportAttendDate;
	/**专业项目*/
	@Excel(name = "专业项目", width = 15)
    private java.lang.String sportCode;
	/**教练员*/
	@Excel(name = "带训教练", width = 15)
    private java.lang.String coachId;
	/**退役时间*/
	@Excel(name = "退役时间", width = 15)
    private java.lang.String retireDate;
	/**吸收单位类别*/
	@Excel(name = "吸收单位类别", width = 15)
    private java.lang.String acceptDepartmentType;
	/**吸收单位*/
	@Excel(name = "吸收单位", width = 15)
    private java.lang.String acceptDepartment;
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
	/**简介*/
	@Excel(name = "简介", width = 15)
    private java.lang.String resume;
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
    private java.lang.String delFlag;
	/**processsId*/
	@Excel(name = "删除标志", width = 15)
    private java.lang.String processId;
}
