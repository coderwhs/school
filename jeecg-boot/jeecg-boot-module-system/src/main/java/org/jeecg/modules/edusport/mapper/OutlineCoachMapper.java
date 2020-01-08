package org.jeecg.modules.edusport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.OutlineCoach;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: tb_edu_outline_coach
 * @Author: jeecg-boot
 * @Date:   2020-01-04
 * @Version: V1.0
 */
public interface OutlineCoachMapper extends BaseMapper<OutlineCoach> {
	void deleteCoachByTestId(@Param("testId") String testId);
}
