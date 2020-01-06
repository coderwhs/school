package org.jeecg.modules.edusport.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.Coach;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 教练员信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
public interface CoachMapper extends BaseMapper<Coach> {
	/**
	  * 根据单据Id查询单据中运动员的教练
	 * @param id
	 * @return
	 */
	public List<Coach> getCoachByBillId(@Param("id") String id);
	
	/**
	  * 根据运动员信息，查找教练
	 * @param athleteNo
	 * @return
	 */
	public List<HashMap> getCoachByAthleteId(@Param("athleteNo") String[] athleteNo);
	
}
