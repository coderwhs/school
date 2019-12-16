package org.jeecg.modules.edusport.service;

import java.util.List;

import org.jeecg.modules.edusport.entity.GraphReport;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 报表信息
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
public interface IGraphReportService extends IService<GraphReport> {

	List<GraphReport> getGroupData();

	GraphReport getTotalData();

}
