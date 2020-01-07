package org.jeecg.modules.edusport.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScore;
import org.jeecg.modules.edusport.entity.AthleteSelectionTest;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * @Description: 运动员选材测试表
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
public interface AthleteSelectionTestMapper extends BaseMapper<AthleteSelectionTest> {
	public void insertGetId(@Param("athleteSelectionTest") AthleteSelectionTest athleteSelectionTest);

}
