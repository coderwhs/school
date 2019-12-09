package org.jeecg.modules.edusport.service.impl;

import org.jeecg.modules.edusport.entity.DormStudent;
import org.jeecg.modules.edusport.mapper.DormStudentMapper;
import org.jeecg.modules.edusport.service.IDormStudentService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 宿舍运动员入住信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Service
public class DormStudentServiceImpl extends ServiceImpl<DormStudentMapper, DormStudent> implements IDormStudentService {
	
	@Autowired
	private DormStudentMapper dormStudentMapper;
	
	@Override
	public List<DormStudent> selectByMainId(String mainId) {
		return dormStudentMapper.selectByMainId(mainId);
	}
}
