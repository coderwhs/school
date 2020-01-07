package org.jeecg.modules.edusport.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.AthleteSelectionIndexCat;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动员选材指标分类信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
public interface AthleteSelectionIndexCatMapper extends BaseMapper<AthleteSelectionIndexCat> {
	public AthleteSelectionIndexCat getIndexCatByName(@Param("indexCatName") String indexCatName);
}
