package org.jeecg.modules.edusport.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.AthleteSelectionIndex;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动员选材指标信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
public interface AthleteSelectionIndexMapper extends BaseMapper<AthleteSelectionIndex> {
	AthleteSelectionIndex getIndexByName(@Param("cnName") String cnName,@Param("indexCatName") String indexCatName);
	HashMap<?, ?> getIndexCatByIndexCode(@Param("indexCode") String indexCode);
}
