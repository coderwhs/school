package org.jeecg.modules.edusport.service.impl;

import org.jeecg.modules.edusport.entity.Dorm;
import org.jeecg.modules.edusport.entity.DormStudent;
import org.jeecg.modules.edusport.entity.DormStudentLeave;
import org.jeecg.modules.edusport.mapper.DormStudentMapper;
import org.jeecg.modules.edusport.mapper.DormStudentLeaveMapper;
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
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Service
public class DormServiceImpl extends ServiceImpl<DormMapper, Dorm> implements IDormService {

	@Autowired
	private DormMapper dormMapper;
	@Autowired
	private DormStudentMapper dormStudentMapper;
	@Autowired
	private DormStudentLeaveMapper dormStudentLeaveMapper;
	
	@Override
	@Transactional
	public void saveMain(Dorm dorm, List<DormStudent> dormStudentList,List<DormStudentLeave> dormStudentLeaveList) {
		dormMapper.insert(dorm);
		if(dormStudentList!=null && dormStudentList.size()>0) {
			for(DormStudent entity:dormStudentList) {
				//外键设置
				entity.setDormId(dorm.getId());
				dormStudentMapper.insert(entity);
			}
		}
		if(dormStudentLeaveList!=null && dormStudentLeaveList.size()>0) {
			for(DormStudentLeave entity:dormStudentLeaveList) {
				//外键设置
				entity.setDormId(dorm.getId());
				dormStudentLeaveMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(Dorm dorm,List<DormStudent> dormStudentList,List<DormStudentLeave> dormStudentLeaveList) {
		dormMapper.updateById(dorm);
		
		//1.先删除子表数据
		dormStudentMapper.deleteByMainId(dorm.getId());
		dormStudentLeaveMapper.deleteByMainId(dorm.getId());
		
		//2.子表数据重新插入
		if(dormStudentList!=null && dormStudentList.size()>0) {
			for(DormStudent entity:dormStudentList) {
				//外键设置
				entity.setDormId(dorm.getId());
				dormStudentMapper.insert(entity);
			}
		}
		if(dormStudentLeaveList!=null && dormStudentLeaveList.size()>0) {
			for(DormStudentLeave entity:dormStudentLeaveList) {
				//外键设置
				entity.setDormId(dorm.getId());
				dormStudentLeaveMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		dormStudentMapper.deleteByMainId(id);
		dormStudentLeaveMapper.deleteByMainId(id);
		dormMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			dormStudentMapper.deleteByMainId(id.toString());
			dormStudentLeaveMapper.deleteByMainId(id.toString());
			dormMapper.deleteById(id);
		}
	}
	
}
