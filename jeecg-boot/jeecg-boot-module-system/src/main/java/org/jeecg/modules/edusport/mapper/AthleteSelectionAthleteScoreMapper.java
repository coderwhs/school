package org.jeecg.modules.edusport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * @Description: 运动员选材测试成绩表
 * @Author: jeecg-boot
 * @Date:   2019-12-19
 * @Version: V1.0
 */
public interface AthleteSelectionAthleteScoreMapper extends BaseMapper<AthleteSelectionAthleteScore> {
	int updateScoreAndRatingByWrapper(@Param(Constants.WRAPPER) Wrapper<AthleteSelectionAthleteScore> userWrapper,
			@Param("athleteSelectionAthleteScore") AthleteSelectionAthleteScore athleteSelectionAthleteScore);

}
