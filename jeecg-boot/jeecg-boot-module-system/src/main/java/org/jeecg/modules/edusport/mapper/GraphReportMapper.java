package org.jeecg.modules.edusport.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.edusport.entity.GraphReport;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 报表信息
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
public interface GraphReportMapper extends BaseMapper<GraphReport> {

	
	/**
	 * 获取分组数据
	 */
	@Select("select ${ew.SqlSelect} from ${tableName} ${ew.customSqlSegment}")
	List<GraphReport> getGroupData(@Param("tableName") String tableName, @Param("ew") Wrapper wrapper); 

	
	/**
	 * 获取合计数据
	 */
	@Select("select ${ew.SqlSelect} from ${tableName} ${ew.customSqlSegment}")
	GraphReport getTotalData(@Param("tableName") String tableName, @Param("ew") Wrapper wrapper); 
	
	/**
	动态查找：
		@Select("select ${ew.SqlSelect} from ${tableName} ${ew.customSqlSegment}")
		List<File> listFileByCondition(@Param("tableName") String tableName, @Param("ew") Wrapper wrapper);
		
		
		ew.SqlSelect：所需要查找的字段
		tableName：使用的是那张表
		ew.customSqlSegment：条件
	
	用法：
		allFileMapper.listFileByCondition(tableName,Wrappers.query().select("*").in("relation_uuid", uuids));
	结果： 
		select * from tablName where relation_uuid in ()
		
	动态修改
		@Update("update ${tableName} set ${ew.sqlSet} ${ew.customSqlSegment}")
		int updateByCondition(@Param("tableName") String tableName, @Param("ew") Wrapper wrapper);
	
		ew.sqlSet：修改的字段
		tableName：使用的是那张表
		ew.customSqlSegment：条件
		
	用法：
		mapper.updateByCondition(tableName, Wrappers.update().set("state", "初始状态").in("id", ids));
	结果： 
		update tableName set state = '初始状态' where id in ()
	*/
	
}
