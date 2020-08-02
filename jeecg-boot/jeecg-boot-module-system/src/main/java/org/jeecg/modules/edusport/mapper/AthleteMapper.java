package org.jeecg.modules.edusport.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.Athlete;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动员信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
public interface AthleteMapper extends BaseMapper<Athlete> {
	Map getAthleteAgeById(@Param("id") String id);
	Athlete getAthleteByNo(@Param("athleteNo") String athleteNo);
//	Athlete getCoachById(@Param("coachId") String coachId);
}
