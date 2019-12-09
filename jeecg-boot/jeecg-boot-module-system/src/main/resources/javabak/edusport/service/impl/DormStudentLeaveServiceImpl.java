package org.jeecg.modules.edusport.service.impl;

import org.jeecg.modules.edusport.entity.DormStudentLeave;
import org.jeecg.modules.edusport.mapper.DormStudentLeaveMapper;
import org.jeecg.modules.edusport.service.IDormStudentLeaveService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 宿舍运动员请假信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Service
public class DormStudentLeaveServiceImpl extends ServiceImpl<DormStudentLeaveMapper, DormStudentLeave> implements IDormStudentLeaveService {
	
	@Autowired
	private DormStudentLeaveMapper dormStudentLeaveMapper;
	
	@Override
	public List<DormStudentLeave> selectByMainId(String mainId) {
		return dormStudentLeaveMapper.selectByMainId(mainId);
	}
}
