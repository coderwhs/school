package org.jeecg.modules.edusport.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

/**
 * @Description: 运动员选材测试教练表
 * @Author: jeecg-boot
 * @Date:   2020-02-20
 * @Version: V1.0
 */
@Data
public class AthleteSelectionTestCoachVo implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**测试教练表主键*/
	@Excel(name = "文档编号", width = 15)
    private java.lang.String testCoachid;
	/**所属测试*/
	@Excel(name = "测试编号", width = 15)
    private java.lang.String testId;
	/**测试日期*/
	@Excel(name = "测试日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date testDate;
	
    private java.lang.String testName;
    
	/**测试运动员*/
	@ExcelCollection(name="测试运动员")
	ArrayList<AthleteSelectionTestAthleteScoreVo> testAthleteScoreVoList;
	
	/**成绩明细*/
//	@ExcelCollection(name="测试指标")
	ArrayList<AthleteSelectionTestAthleteScoreDetailVo> testAthleteScoreDetailList;
	
	/**成绩明细*/
	@ExcelCollection(name="测试指标")
	ArrayList<HashMap<String, Object>> testAthleteScoreDetailMapList;
	
}
