package org.jeecg.modules.flowable.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.jeecg.modules.demo.test.entity.JeecgDemo;
import org.jeecg.modules.demo.test.mapper.JeecgDemoMapper;
import org.jeecg.modules.edusport.mapper.DormAthleteLeaveMapper;
import org.jeecg.modules.flowable.service.IProcessService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;

/**
 * @Description: jeecg 测试demo
 * @Author: jeecg-boot
 * @Date: 2018-12-29
 * @Version: V1.0
 */
@Service
public class ProcessServiceImpl extends ServiceImpl<JeecgDemoMapper, JeecgDemo> implements IProcessService {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessEngine processEngine;
	@Resource
	private DormAthleteLeaveMapper dormAthleteLeaveMapper;
	@Autowired
	private ISysUserService sysUserService;

	/*************** 此处为业务代码 ******************/

	@Override
	public String addFlowable(String userName, String billType, String businessKey, HashMap<String, Object> map ) {
	
		String processDefinitionKey = billType;
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,
				map);
		System.out.println("提交成功.流程Id为：" + processInstance.getId());
		return "提交成功.流程Id为：" + processInstance.getId();
	}

	@Override
	public List<String> list(String username) {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).orderByTaskCreateTime().desc().list();
		List<String> results = Lists.newArrayList();
		for (Task task : tasks) {
			results.add(task.getId());
		}
		return results;
	}

	@Override
	public String apply(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new RuntimeException("流程不存在");
		}
		// 通过审核
		HashMap<String, Object> map = new HashMap<>();
		map.put("outcome", "通过");
		taskService.complete(taskId, map);
		return "processed ok!";
	}

	@Override
	public String reject(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new RuntimeException("流程不存在");
		}
		// 驳回.
		HashMap<String, Object> map = new HashMap<>();
		map.put("outcome", "驳回");
		taskService.complete(taskId, map);

		return "reject!";
	}

	@Override
	public InputStream processDiagram(String procInstId) throws Exception {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).singleResult();
		// 流程走完的不显示图
		if (pi == null) {
			return null;
		}
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		String InstanceId = task.getProcessInstanceId();
		List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(InstanceId).list();

		// 得到正在执行的Activity的Id
		List<String> activityIds = new ArrayList<>();
		List<String> flows = new ArrayList<>();
		for (Execution exe : executions) {
			List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
			activityIds.addAll(ids);
		}

		// 获取流程图
		BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
		ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
		engconf.setActivityFontName("宋体");
		engconf.setLabelFontName("宋体");
		engconf.setAnnotationFontName("宋体");
		ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
		InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows,
				engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(),
				engconf.getClassLoader(), 1.0, true);
		
		return in;

	}

}
