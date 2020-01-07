package org.jeecg.modules.edusport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.Coach;
import org.jeecg.modules.edusport.entity.Sport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动项目表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
public interface SportMapper extends BaseMapper<Sport> {
	public Sport getSportByName(@Param("sportName") String sportName);
}
