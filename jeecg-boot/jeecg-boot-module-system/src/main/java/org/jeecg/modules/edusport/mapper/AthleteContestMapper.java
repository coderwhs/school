package org.jeecg.modules.edusport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.AthleteContest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动员参赛信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
public interface AthleteContestMapper extends BaseMapper<AthleteContest> {
	public List<AthleteContest> getContestByAthleteId(@Param("athleteId") String athleteId);
}
