package org.jeecg.modules.edusport.mapper;

import java.util.List;
import org.jeecg.modules.edusport.entity.DormAthleteLiving;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 宿舍运动员入住表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
public interface DormAthleteLivingMapper extends BaseMapper<DormAthleteLiving> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<DormAthleteLiving> selectByMainId(@Param("mainId") String mainId);
}
