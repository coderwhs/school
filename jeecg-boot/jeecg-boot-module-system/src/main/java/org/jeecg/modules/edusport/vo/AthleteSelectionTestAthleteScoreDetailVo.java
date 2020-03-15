package org.jeecg.modules.edusport.vo;

import java.io.Serializable;

import org.jeecgframework.poi.excel.annotation.Excel;

import lombok.Data;

/**
 * @Description: 运动员选材测试运动员成绩明细表
 * @Author: jeecg-boot
 * @Date:   2020-02-20
 * @Version: V1.0
 */
@Data
public class AthleteSelectionTestAthleteScoreDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;
	/**所属组别*/
    private java.lang.String groupId;
	/**指标*/
    private java.lang.String indexCode;
	/**指标名称*/
    private java.lang.String indexName;
    
	/**指标1*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode1;
	/**指标2*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode2;
	/**指标3*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode3;
	/**指标4*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode4;
	/**指标5*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode5;
	/**指标6*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode6;
	/**指标7*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode7;
	/**指标8*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode8;
	/**指标9*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode9;
	/**指标10*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode10;
    
    /**指标11*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode11;
	/**指标12*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode12;
	/**指标13*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode13;
	/**指标14*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode14;
	/**指标15*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode15;
	/**指标16*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode16;
	/**指标17*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode17;
	/**指标18*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode18;
	/**指标19*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode19;
	/**指标20*/
    @Excel(name = "", width = 15)
    private java.lang.String indexCode20;

}
