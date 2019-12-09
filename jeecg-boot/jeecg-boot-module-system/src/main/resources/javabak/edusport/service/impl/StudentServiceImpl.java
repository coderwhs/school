package org.jeecg.modules.edusport.service.impl;

import org.jeecg.modules.edusport.entity.Student;
import org.jeecg.modules.edusport.mapper.StudentMapper;
import org.jeecg.modules.edusport.service.IStudentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 运动员信息
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
