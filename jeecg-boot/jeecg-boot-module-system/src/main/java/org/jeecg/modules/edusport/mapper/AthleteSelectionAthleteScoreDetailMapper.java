package org.jeecg.modules.edusport.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScoreDetail;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动员选材测试成绩明细表
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
public interface AthleteSelectionAthleteScoreDetailMapper extends BaseMapper<AthleteSelectionAthleteScoreDetail> {

	Integer getAthleteScoreById(@Param("id") String id);
	void deleteIndexByIndexId(@Param("list") java.util.List list);
}
