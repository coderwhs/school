package org.jeecg.modules.edusport.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 宿舍信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
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
    private java.lang.String dormBuildingName;
	/**宿舍房间号*/
    private java.lang.String dormNo;
	/**床位数*/
    private java.lang.Integer bedNum;
	/**宿舍电话*/
    private java.lang.String dormTel;
	/**宿舍地址*/
    private java.lang.String dormAddress;
	/**宿舍管理员*/
    private java.lang.String dormAdmin;
	/**管理员电话*/
    private java.lang.String dormAdminTel;
	/**创建人*/
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;
	/**更新人*/
    private java.lang.String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;
}
