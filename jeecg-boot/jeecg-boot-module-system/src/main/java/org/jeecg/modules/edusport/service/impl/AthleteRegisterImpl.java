package org.jeecg.modules.edusport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.edusport.entity.AthleteRegister;
import org.jeecg.modules.edusport.mapper.AthleteRegisterMapper;
import org.jeecg.modules.edusport.service.IAthleteRegisterService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description：运动员注册表
 * @author:whs
 * @date: 2020/7/25 16:30
 */
@Service
public class AthleteRegisterImpl extends ServiceImpl<AthleteRegisterMapper, AthleteRegister> implements IAthleteRegisterService {

    @Override
    public String getAthleteName() {
        return null;
    }

    @Override
    public String getGender() {
        return null;
    }

    @Override
    public String getFatherMobile() {
        return null;
    }

    @Override
    public String getIdNo() {
        return null;
    }

    @Override
    public String getNation() {
        return null;
    }

    @Override
    public String getSportCode() {
        return null;
    }

    @Override
    public String getCoachId() {
        return null;
    }

    @Override
    public String getHomeAddress() {
        return null;
    }

    @Override
    public String getFather() {
        return null;
    }

    @Override
    public String getMother() {
        return null;
    }

    @Override
    public String getMotherMobile() {
        return null;
    }

    @Override
    public Date getCreateTime() {
        return null;
    }
}
