package org.jeecg.modules.edusport.service.impl;

import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.mapper.AthleteMapper;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 运动员信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@Service
public class AthleteServiceImpl extends ServiceImpl<AthleteMapper, Athlete> implements IAthleteService {

}
