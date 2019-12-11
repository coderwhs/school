package org.jeecg.modules.edusport.service.impl;

import org.jeecg.modules.edusport.entity.Dorm;
import org.jeecg.modules.edusport.entity.DormAthleteLiving;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.mapper.DormAthleteLivingMapper;
import org.jeecg.modules.edusport.mapper.DormAthleteLeaveMapper;
import org.jeecg.modules.edusport.mapper.DormMapper;
import org.jeecg.modules.edusport.service.IDormService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 宿舍信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Service
public class DormServiceImpl extends ServiceImpl<DormMapper, Dorm> implements IDormService {

	@Autowired
	private DormMapper dormMapper;
	@Autowired
	private DormAthleteLivingMapper dormAthleteLivingMapper;
	@Autowired
	private DormAthleteLeaveMapper dormAthleteLeaveMapper;
	
	@Override
	@Transactional
	public void saveMain(Dorm dorm, List<DormAthleteLiving> dormAthleteLivingList,List<DormAthleteLeave> dormAthleteLeaveList) {
		dormMapper.insert(dorm);
		if(dormAthleteLivingList!=null && dormAthleteLivingList.size()>0) {
			for(DormAthleteLiving entity:dormAthleteLivingList) {
				//外键设置
				entity.setDromId(dorm.getId());
				dormAthleteLivingMapper.insert(entity);
			}
		}
		if(dormAthleteLeaveList!=null && dormAthleteLeaveList.size()>0) {
			for(DormAthleteLeave entity:dormAthleteLeaveList) {
				//外键设置
				entity.setDormId(dorm.getId());
				dormAthleteLeaveMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Dorm dorm,List<DormAthleteLiving> dormAthleteLivingList,List<DormAthleteLeave> dormAthleteLeaveList) {
		dormMapper.updateById(dorm);
		
		//1.先删除子表数据
		dormAthleteLivingMapper.deleteByMainId(dorm.getId());
		dormAthleteLeaveMapper.deleteByMainId(dorm.getId());
		
		//2.子表数据重新插入
		if(dormAthleteLivingList!=null && dormAthleteLivingList.size()>0) {
			for(DormAthleteLiving entity:dormAthleteLivingList) {
				//外键设置
				entity.setDromId(dorm.getId());
				dormAthleteLivingMapper.insert(entity);
			}
		}
		if(dormAthleteLeaveList!=null && dormAthleteLeaveList.size()>0) {
			for(DormAthleteLeave entity:dormAthleteLeaveList) {
				//外键设置
				entity.setDormId(dorm.getId());
				dormAthleteLeaveMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		dormAthleteLivingMapper.deleteByMainId(id);
		dormAthleteLeaveMapper.deleteByMainId(id);
		dormMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			dormAthleteLivingMapper.deleteByMainId(id.toString());
			dormAthleteLeaveMapper.deleteByMainId(id.toString());
			dormMapper.deleteById(id);
		}
	}
	
}
