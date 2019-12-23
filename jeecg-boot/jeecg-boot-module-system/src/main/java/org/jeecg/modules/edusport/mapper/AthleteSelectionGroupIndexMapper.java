package org.jeecg.modules.edusport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupIndex;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupRating;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动员选材测试组别的指标明细表
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
public interface AthleteSelectionGroupIndexMapper extends BaseMapper<AthleteSelectionGroupIndex> {
	AthleteSelectionGroupIndex getIndexByGroupId(@Param("groupId") String groupId);
}
