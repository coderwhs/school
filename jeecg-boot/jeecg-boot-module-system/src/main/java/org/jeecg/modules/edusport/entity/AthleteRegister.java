package org.jeecg.modules.edusport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @description：运动员注册表
 * @author:whs
 * @date: 2020/7/25 16:30
 */
@Data
@TableName("tb_edu_athlete")
public class AthleteRegister implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键id*/
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;

    /**运动员名字*/
    @Excel(name = "运动员id", width = 15)
    private java.lang.String athleteName;

    /**性别*/
    @Excel(name = "性别", width = 15,dicCode="sex")
    @Dict(dicCode = "sex")
    private java.lang.String gender;

    /**民族*/
    @Excel(name = "民族", width = 15,dicCode = "nation")
    @Dict(dicCode = "nation")
    private java.lang.String nation;

    /**身份证号*/
    @Excel(name = "身份证号", width = 15)
    private java.lang.String idNo;

    /**带训教练员代码*/
    @Excel(name = "带训教练", width = 15)
    private java.lang.String coachId;

    /**专业项目代码*/
    @Excel(name = "专业项目", width = 15)
    private java.lang.String sportCode;

    /**家庭住址*/
    @Excel(name = "家庭住址", width = 15)
    private java.lang.String homeAddress;

    /**父亲姓名*/
    @Excel(name = "父亲姓名", width = 15)
    private java.lang.String father;

    /**父亲电话*/
    @Excel(name = "父亲电话", width = 15)
    private java.lang.String fatherMobile;

    /**母亲姓名*/
    @Excel(name = "母亲姓名", width = 15)
    private java.lang.String mother;

    /**母亲电话*/
    @Excel(name = "母亲电话", width = 15)
    private java.lang.String motherMobile;
//    /**单据类型*/
//    @Excel(name = "单据类型", width = 15)
//    private java.lang.String billType;
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
    /**流程id*/
    @Excel(name = "流程id", width = 15)
    private java.lang.String workflowId;
    /**流程状态*/
    @Excel(name = "流程状态", width = 15)
    private java.lang.String workflowStatus;
}
