package org.jeecg.modules.edusport.service;

import org.jeecg.modules.edusport.entity.DormAthleteLiving;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.entity.Dorm;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 宿舍信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
public interface IDormService extends IService<Dorm> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(Dorm dorm,List<DormAthleteLiving> dormAthleteLivingList,List<DormAthleteLeave> dormAthleteLeaveList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(Dorm dorm,List<DormAthleteLiving> dormAthleteLivingList,List<DormAthleteLeave> dormAthleteLeaveList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
