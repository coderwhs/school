package org.jeecg.modules.edusport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.edusport.entity.AthleteRegister;
import org.jeecg.modules.edusport.entity.CoachLeave;

import java.util.Date;

/**
 * @description：运动员注册表
 * @author:whs
 * @date: 2020/7/25 16:43
 */
public interface IAthleteRegisterService extends IService<AthleteRegister> {
    String getAthleteName();

    String getGender();

    String getFatherMobile();

    String getIdNo();

    String getNation();

    String getSportCode();

    String getCoachId();

    String getHomeAddress();

    String getFather();

    String getMother();

    String getMotherMobile();

    Date getCreateTime();
}
