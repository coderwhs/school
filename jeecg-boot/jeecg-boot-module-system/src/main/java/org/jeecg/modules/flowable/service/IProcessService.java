package org.jeecg.modules.flowable.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.jeecg.common.system.base.service.JeecgService;
import org.jeecg.modules.demo.test.entity.JeecgDemo;

/**
 * @Description: 工作流功能接口
 * @Author: 李鹏
 * @Date: 2019-12-17
 * @Version: V1.0
 */
public interface IProcessService extends JeecgService<JeecgDemo> {

	/**
	   * 启动一个工作流
	 *
	 * @param userId    用户Id
	 * @param money     报销金额
	 * @param descption 描述
	 */
	String addFlowable(String userName, String billType, String billId, HashMap<String, Object> map);

	/**
	   * 获取审批管理列表
	 */
	List<String> list(String userId);

	/**
	   * 批准
	 *
	 * @param taskId 任务ID
	 */
	String apply(String taskId);

	/**
	   * 拒绝
	 * 
	 * @param taskId 任务ID
	 */
	String reject(String taskId);

	/**
	 * 生成流程图
	 *
	 * @param processId 任务ID
	 */

	InputStream processDiagram(String procInstId) throws Exception;

}
