package org.jeecg.modules.edusport.vo;

import java.util.List;
import org.jeecg.modules.edusport.entity.Dorm;
import org.jeecg.modules.edusport.entity.DormAthleteLiving;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 宿舍信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Data
public class DormPage {
	
	/**主键id*/
	private java.lang.String id;
	/**宿舍楼名称*/
	@Excel(name = "宿舍楼名称", width = 15)
	private java.lang.String dormBuildingName;
	/**宿舍房间号*/
	@Excel(name = "宿舍房间号", width = 15)
	private java.lang.String dormNo;
	/**床位数*/
	@Excel(name = "床位数", width = 15)
	private java.lang.Integer bedNum;
	/**宿舍电话*/
	@Excel(name = "宿舍电话", width = 15)
	private java.lang.String dormTel;
	/**宿舍地址*/
	@Excel(name = "宿舍地址", width = 15)
	private java.lang.String dormAddress;
	/**宿舍管理员*/
	@Excel(name = "宿舍管理员", width = 15)
	private java.lang.String dormAdmin;
	/**管理员电话*/
	@Excel(name = "管理员电话", width = 15)
	private java.lang.String dormAdminTel;
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
	
	@ExcelCollection(name="宿舍运动员入住表")
	private List<DormAthleteLiving> dormAthleteLivingList;	
	@ExcelCollection(name="宿舍运动员请假表")
	private List<DormAthleteLeave> dormAthleteLeaveList;	
	
}
