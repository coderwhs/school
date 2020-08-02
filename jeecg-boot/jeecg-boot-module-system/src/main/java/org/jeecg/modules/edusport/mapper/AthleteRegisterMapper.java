package org.jeecg.modules.edusport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.edusport.entity.AthleteRegister;

/**
 * @description：
 * @author:whs
 * @date: 2020/7/25 17:16
 */
public interface AthleteRegisterMapper extends BaseMapper<AthleteRegister> {
//    空的updateWorkflowState方法
    public void updateWorkflowState(@Param("id") String id);
}
