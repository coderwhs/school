package org.jeecg.modules.edusport.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 公告消息表
 * @Author: jeecg-boot
 * @Date:   2019-12-26
 * @Version: V1.0
 */
@Data
@TableName("tb_edu_cms_news")
public class CmsNews implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键id*/
	@TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
	/**新闻类别*/
	@Excel(name = "新闻类别", width = 15)
    private java.lang.String newsCat;
	/**标题*/
	@Excel(name = "标题", width = 15)
    private java.lang.String newsTitle;
	/**副标题*/
	@Excel(name = "副标题", width = 15)
    private java.lang.String newsSubTitle;
	/**链接地址*/
	@Excel(name = "链接地址", width = 15)
    private java.lang.String newsLink;
	/**置顶状态*/
	@Excel(name = "置顶状态", width = 15)
    private java.lang.String topStatus;
	/**显示顺序*/
	@Excel(name = "显示顺序", width = 15)
    private java.lang.Integer newsOrder;
	/**启用状态*/
	@Excel(name = "启用状态", width = 15)
    private java.lang.String enableStatus;
	/**来源*/
	@Excel(name = "来源", width = 15)
    private java.lang.String newsFrom;
	/**报道时间*/
	@Excel(name = "报道时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date publishDate;
	/**标题图片*/
	@Excel(name = "标题图片", width = 15)
    private java.lang.String titleImage;
	/**摘要*/
	@Excel(name = "摘要", width = 15)
    private java.lang.String newsSummary;
	/**内容*/
	@Excel(name = "内容", width = 15)
    private java.lang.String newsContent;
	/**阅览次数*/
	@Excel(name = "阅览次数", width = 15)
    private java.lang.Integer newsHits;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
	/**更新时间*/
	@Excel(name = "更新时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;
}
