package org.jeecg.modules.edusport.service.impl;

import org.jeecg.modules.edusport.entity.DormAthleteLiving;
import org.jeecg.modules.edusport.mapper.DormAthleteLivingMapper;
import org.jeecg.modules.edusport.service.IDormAthleteLivingService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 宿舍运动员入住表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Service
public class DormAthleteLivingServiceImpl extends ServiceImpl<DormAthleteLivingMapper, DormAthleteLiving> implements IDormAthleteLivingService {
	
	@Autowired
	private DormAthleteLivingMapper dormAthleteLivingMapper;
	
	@Override
	public List<DormAthleteLiving> selectByMainId(String mainId) {
		return dormAthleteLivingMapper.selectByMainId(mainId);
	}
}
