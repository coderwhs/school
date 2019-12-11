package org.jeecg.modules.edusport.service.impl;

import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.mapper.DormAthleteLeaveMapper;
import org.jeecg.modules.edusport.service.IDormAthleteLeaveService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 宿舍运动员请假表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Service
public class DormAthleteLeaveServiceImpl extends ServiceImpl<DormAthleteLeaveMapper, DormAthleteLeave> implements IDormAthleteLeaveService {
	
	@Autowired
	private DormAthleteLeaveMapper dormAthleteLeaveMapper;
	
	@Override
	public List<DormAthleteLeave> selectByMainId(String mainId) {
		return dormAthleteLeaveMapper.selectByMainId(mainId);
	}
}
