package org.jeecg.modules.edusport.service;

import org.jeecg.modules.edusport.entity.DormAthleteLiving;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 宿舍运动员入住表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
public interface IDormAthleteLivingService extends IService<DormAthleteLiving> {

	public List<DormAthleteLiving> selectByMainId(String mainId);
}
