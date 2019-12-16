package org.jeecg.modules.edusport.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description: 报表信息
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@Data
public class GraphReport implements Serializable {
    private static final long serialVersionUID = 1L;
	
	/** 图表: 排名列表 名称 */
    private java.lang.String name;
    
	/** 图表: 排名列表 值 */
    private java.lang.String total;
    
	/** 图表: 柱状图/面积图 x轴 */
    private java.lang.String x;
    
	/** 图表值: 柱状图/面积图 y轴 */
    private java.lang.Integer y;
    
	/** 图表: 键 */
    private java.lang.String chartKey;
    
	/** 图表: 值 */
    private java.lang.String chartValue;
    
}
