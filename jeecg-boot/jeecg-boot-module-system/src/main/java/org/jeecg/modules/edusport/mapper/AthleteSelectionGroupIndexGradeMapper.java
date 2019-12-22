package org.jeecg.modules.edusport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupIndexGrade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动员选材测试指标评分标准列表
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
public interface AthleteSelectionGroupIndexGradeMapper extends BaseMapper<AthleteSelectionGroupIndexGrade> {
	Integer getAthleteScoreByTestValue(
			@Param("groupId") String groupId,
			@Param("indexCode") String indexCode, 
			@Param("gender") String gender,
			@Param("age") Integer age,
			@Param("testValue") double testValue);
}
