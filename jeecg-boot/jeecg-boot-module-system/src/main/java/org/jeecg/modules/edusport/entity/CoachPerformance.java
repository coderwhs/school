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
 * @Description: 教练员年度业务情况表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_coach_performance")
public class CoachPerformance implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**教练员代码*/
	@Excel(name = "教练员代码", width = 15)
    private java.lang.String coachNo;
	/**业务年度*/
	@Excel(name = "业务年度", width = 15)
    private java.lang.Integer performanceYear;
	/**现带训(人)*/
	@Excel(name = "现带训(人)", width = 15)
    private java.lang.Integer athleteNum;
	/**年度进队(人)*/
	@Excel(name = "年度进队(人)", width = 15)
    private java.lang.Integer attendNum;
	/**年度离队(人)*/
	@Excel(name = "年度离队(人)", width = 15)
    private java.lang.Integer leaveNum;
	/**市体校(人)*/
	@Excel(name = "市体校(人)", width = 15)
    private java.lang.Integer citySchoolNum;
	/**省体校(人)*/
	@Excel(name = "省体校(人)", width = 15)
    private java.lang.Integer provinceSchoolNum;
	/**省队集试训(人)*/
	@Excel(name = "省队集试训(人)", width = 15)
    private java.lang.Integer provinceTeamNum;
	/**八一集试训(人)*/
	@Excel(name = "八一集试训(人)", width = 15)
    private java.lang.Integer bayiTeamNum;
	/**输送国家队(人)*/
	@Excel(name = "输送国家队(人)", width = 15)
    private java.lang.Integer toNationalTeam;
	/**输送国青队(人)*/
	@Excel(name = "输送国青队(人)", width = 15)
    private java.lang.Integer toNationalYouthTeam;
	/**输送省队(人)*/
	@Excel(name = "输送省队(人)", width = 15)
    private java.lang.Integer toProvinceTeam;
	/**输送省体校(人)*/
	@Excel(name = "输送省体校(人)", width = 15)
    private java.lang.Integer toProvinceSchool;
	/**输送市体校(人)*/
	@Excel(name = "输送市体校(人)", width = 15)
    private java.lang.Integer toCitySchool;
	/**输送高校(人)*/
	@Excel(name = "输送高校(人)", width = 15)
    private java.lang.Integer toCollege;
	/**输送备注*/
	@Excel(name = "输送备注", width = 15)
    private java.lang.String toRemark;
	/**锦标赛第一(人)*/
	@Excel(name = "锦标赛第一(人)", width = 15)
    private java.lang.Integer contestFirst;
	/**锦标赛第二(人)*/
	@Excel(name = "锦标赛第二(人)", width = 15)
    private java.lang.Integer contestSecond;
	/**锦标赛第三(人)*/
	@Excel(name = "锦标赛第三(人)", width = 15)
    private java.lang.Integer contestThird;
	/**锦标赛达优(人)*/
	@Excel(name = "锦标赛达优(人)", width = 15)
    private java.lang.Integer contestExcellence;
	/**锦标赛达良(人)*/
	@Excel(name = "锦标赛达良(人)", width = 15)
    private java.lang.Integer contestGood;
	/**锦标赛总分*/
	@Excel(name = "锦标赛总分", width = 15)
    private java.lang.Integer contestTotalPoints;
	/**锦标赛备注*/
	@Excel(name = "锦标赛备注", width = 15)
    private java.lang.Integer contestRemark;
	/**是否年度少儿锦标赛精神文明运动队*/
	@Excel(name = "是否年度少儿锦标赛精神文明运动队", width = 15)
    private java.lang.String isScTeam;
	/**奖惩情况*/
	@Excel(name = "奖惩情况", width = 15)
    private java.lang.String rewardsPunishments;
	/**年度业务考核得分*/
	@Excel(name = "年度业务考核得分", width = 15)
    private java.lang.String appraisalScore;
	/**年度业务考核等级*/
	@Excel(name = "年度业务考核等级", width = 15)
    private java.lang.String appraisalGrade;
	/**学校训练部门审核意见*/
	@Excel(name = "学校训练部门审核意见", width = 15)
    private java.lang.String auditOpinion;
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
