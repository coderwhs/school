package org.jeecg.modules.edusport.mapper;

import java.util.List;
import org.jeecg.modules.edusport.entity.DormStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 宿舍运动员入住信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
public interface DormStudentMapper extends BaseMapper<DormStudent> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<DormStudent> selectByMainId(@Param("mainId") String mainId);
}
