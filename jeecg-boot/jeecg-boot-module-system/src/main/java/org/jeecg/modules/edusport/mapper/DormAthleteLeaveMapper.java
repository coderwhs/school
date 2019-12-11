package org.jeecg.modules.edusport.mapper;

import java.util.List;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 宿舍运动员请假表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
public interface DormAthleteLeaveMapper extends BaseMapper<DormAthleteLeave> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<DormAthleteLeave> selectByMainId(@Param("mainId") String mainId);
}
