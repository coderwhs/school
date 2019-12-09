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
 * @Description: 运动员信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_student")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**学号*/
	@Excel(name = "学号", width = 15)
    private java.lang.String studentNo;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    private java.lang.String studentName;
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
	/**入学日期*/
	@Excel(name = "入学日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date admissionDate;
	/**入学成绩*/
	@Excel(name = "入学成绩", width = 15)
    private java.math.BigDecimal admissionScore;
	/**学籍状态*/
	@Excel(name = "学籍状态", width = 15)
    private java.lang.String studentStatus;
	/**政治面貌*/
	@Excel(name = "政治面貌", width = 15)
    private java.lang.String politicalStatus;
	/**宿舍主键id*/
	@Excel(name = "宿舍主键id", width = 15)
    private java.lang.String dromId;
	/**职务*/
	@Excel(name = "职务", width = 15)
    private java.lang.String duty;
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
	/**骨龄*/
	@Excel(name = "骨龄", width = 15)
    private java.lang.Integer boneAge;
	/**身高*/
	@Excel(name = "身高", width = 15)
    private java.lang.Integer studentHeight;
	/**体重*/
	@Excel(name = "体重", width = 15)
    private java.math.BigDecimal studentWeight;
	/**父亲姓名*/
	@Excel(name = "父亲姓名", width = 15)
    private java.lang.String father;
	/**父亲身高*/
	@Excel(name = "父亲身高", width = 15)
    private java.lang.Integer fatherHeight;
	/**父亲体重*/
	@Excel(name = "父亲体重", width = 15)
    private java.math.BigDecimal fatherWeight;
	/**父亲电话*/
	@Excel(name = "父亲电话", width = 15)
    private java.lang.String fatherMobile;
	/**父亲工作单位*/
	@Excel(name = "父亲工作单位", width = 15)
    private java.lang.String fatherJob;
	/**母亲姓名*/
	@Excel(name = "母亲姓名", width = 15)
    private java.lang.String mother;
	/**母亲身高*/
	@Excel(name = "母亲身高", width = 15)
    private java.lang.Integer motherHeight;
	/**母亲体重*/
	@Excel(name = "母亲体重", width = 15)
    private java.math.BigDecimal motherWeight;
	/**母亲电话*/
	@Excel(name = "母亲电话", width = 15)
    private java.lang.String motherMobile;
	/**母亲工作单位*/
	@Excel(name = "母亲工作单位", width = 15)
    private java.lang.String motherJob;
	/**家庭电话*/
	@Excel(name = "家庭电话", width = 15)
    private java.lang.String homeTel;
	/**家庭住址*/
	@Excel(name = "家庭住址", width = 15)
    private java.lang.String homeAddress;
	/**户口所在派出所*/
	@Excel(name = "户口所在派出所", width = 15)
    private java.lang.String policeStation;
	/**简介*/
	@Excel(name = "简介", width = 15)
    private java.lang.String resume;
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
    private java.lang.String delFlag;
}
