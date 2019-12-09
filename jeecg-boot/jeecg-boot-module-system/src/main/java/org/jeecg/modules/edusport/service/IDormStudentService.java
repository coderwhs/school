package org.jeecg.modules.edusport.service;

import org.jeecg.modules.edusport.entity.DormStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 宿舍运动员入住信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
public interface IDormStudentService extends IService<DormStudent> {

	public List<DormStudent> selectByMainId(String mainId);
}
