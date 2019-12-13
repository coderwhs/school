package org.jeecg.modules.edusport.service.impl;

import org.jeecg.modules.edusport.entity.Sport;
import org.jeecg.modules.edusport.mapper.SportMapper;
import org.jeecg.modules.edusport.service.ISportService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 运动项目表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@Service
public class SportServiceImpl extends ServiceImpl<SportMapper, Sport> implements ISportService {

}
