package org.jeecg.modules.edusport.service;

import org.jeecg.modules.edusport.entity.DormStudentLeave;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 宿舍运动员请假信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
public interface IDormStudentLeaveService extends IService<DormStudentLeave> {

	public List<DormStudentLeave> selectByMainId(String mainId);
}
