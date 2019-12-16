package org.jeecg.modules.edusport.controller;

import java.util.List;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.edusport.entity.GraphReport;
import org.jeecg.modules.edusport.mapper.GraphReportMapper;
import org.jeecg.modules.edusport.service.IGraphReportService;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 报表信息
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/graphReport")
@Slf4j
public class GraphReportController extends JeecgController<GraphReport, IGraphReportService> {
	@Autowired
	private IGraphReportService graphReportService;
    @Autowired
    private GraphReportMapper mapper;
    
    /**
     * 获取运动员总人数
     * @return
     * @throws Exception
     */
    @GetMapping("/athlete/total")
    public Result<?> getAthleteTotalData() throws Exception {
    	StringBuffer querySql = new StringBuffer();
    	querySql.append("(select COUNT(1) as total from tb_edu_athlete where athlete_name != '') as t");
    	
    	GraphReport graphReport = this.mapper.getTotalData(querySql.toString(), Wrappers.query().select("*"));
        log.info("getAthleteTotalData: ", JSON.toJSONString(graphReport));
        
        return Result.ok(graphReport);
    }
    
    /**
     * 获取教练员总人数
     * @return
     * @throws Exception
     */
    @GetMapping("/coach/total")
    public Result<?> getCoachTotalData() throws Exception {
    	StringBuffer querySql = new StringBuffer();
    	querySql.append("(select COUNT(1) as total from tb_edu_coach where coach_name != '') as t");
    	
    	GraphReport graphReport = this.mapper.getTotalData(querySql.toString(), Wrappers.query().select("*"));
    	log.info("getCoachTotalData: ", JSON.toJSONString(graphReport));
        
        return Result.ok(graphReport);
    }
    
    /**
     * 获取运动员技术等级分组
     * @return
     * @throws Exception
     */
    @GetMapping("/athlete/techGrade/group")
    public Result<?> getAthleteTechGradeGroupData() throws Exception {
    	StringBuffer querySql = new StringBuffer();
    	querySql.append("(");
    	querySql.append(" select t.item_text as x, count(1) as y from tb_edu_athlete_contest ");
    	querySql.append(" left join ");
    	querySql.append(" (select item_value, item_text from sys_dict_item where dict_id in ");
    	querySql.append("  (select id from sys_dict t where t.dict_code='athlete_tech_grade')) as t");
    	querySql.append(" on t.item_value = awarded_tech_grade ");
    	querySql.append(" GROUP BY awarded_tech_grade ");
    	querySql.append(") as t");
    	
    	List<GraphReport> graphReportList = this.mapper.getGroupData(querySql.toString(), Wrappers.query().select("*"));
        log.info("getAthleteTechGradeGroupData: ", JSON.toJSONString(graphReportList));
        
        return Result.ok(graphReportList);
    }
    
}
