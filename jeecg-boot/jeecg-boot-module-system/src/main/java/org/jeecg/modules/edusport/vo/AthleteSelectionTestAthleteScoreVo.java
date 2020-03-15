package org.jeecg.modules.edusport.vo;

import java.io.Serializable;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 运动员选材测试运动员成绩表
 * @Author: jeecg-boot
 * @Date:   2020-02-20
 * @Version: V1.0
 */
@Data
public class AthleteSelectionTestAthleteScoreVo implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**测试远动员id*/
	@Excel(name = "运动员编号", width = 15)
    private java.lang.String athleteId;
	@Excel(name = "运动员", width = 15)
    private java.lang.String athleteName;
	
}
