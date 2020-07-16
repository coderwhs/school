package org.jeecg.modules.edusport.mapper;

import org.apache.ibatis.annotations.Param;

import org.jeecg.modules.edusport.entity.CoachLeave;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 教练请假表
 * @Author: whs
 */
public interface CoachLeaveMapper extends BaseMapper<CoachLeave> {
    public void updateWorkflowState(@Param("id") String id);
}
