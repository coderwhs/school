package org.jeecg.modules.edusport.service.impl;

import java.util.List;

import org.jeecg.modules.edusport.entity.GraphReport;
import org.jeecg.modules.edusport.mapper.GraphReportMapper;
import org.jeecg.modules.edusport.service.IGraphReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 报表信息
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@Service
public class GraphReportServiceImpl extends ServiceImpl<GraphReportMapper, GraphReport> implements IGraphReportService {

    @Autowired
    private GraphReportMapper mapper;
    
	/**
	 * 获取分组数据
	 */
	@Override
	public List<GraphReport> getGroupData() {
		return mapper.getGroupData("", Wrappers.query().select("*"));
	}
	
	/**
	 * 获取合计数据
	 */
	@Override
	public GraphReport getTotalData() {
		return mapper.getTotalData("", Wrappers.query().select("*"));
	}
}
