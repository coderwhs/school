package org.jeecg.modules.demo.test.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.demo.test.entity.JeecgDemo;
import org.jeecg.modules.demo.test.service.IJeecgDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "工作流测试DEMO")
@RestController
//@Controller
@RequestMapping(value = "process")
public class FlowableController extends JeecgController<JeecgDemo, IJeecgDemoService> {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessEngine processEngine;

	/*************** 此处为业务代码 ******************/

	/**
	 * 添加报销
	 *
	 * @param userId    用户Id
	 * @param money     报销金额
	 * @param descption 描述
	 */

    @ApiOperation(value = "添加流程", notes = "添加工作流")
	@RequestMapping(value = "add")
	@ResponseBody
	public String addFlowable(String userId, Integer leaveType, String descption) {
		// 启动流程
		HashMap<String, Object> map = new HashMap<>();
		map.put("taskUser", userId);
		map.put("leaveType", leaveType);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Leave", map);
		return "提交成功.流程Id为：" + processInstance.getId();
	}

	/**
	 * 获取审批管理列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object Flowablelist(String userId) {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
		for (Task task : tasks) {
			System.out.println(task.toString());
		}
		return tasks.toString();
	}

	/**
	 * 批准
	 *
	 * @param taskId 任务ID
	 */
	@RequestMapping(value = "apply")
	@ResponseBody
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

	/**
	 * 拒绝
	 */
	@ResponseBody
	@RequestMapping(value = "reject")
	public String reject(String taskId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("outcome", "驳回");
		taskService.complete(taskId, map);
		return "reject";
	}

	/**
	 * 生成流程图
	 *
	 * @param processId 任务ID
	 */
	@RequestMapping(value = "processDiagram")
	public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

		// 流程走完的不显示图
		if (pi == null) {
			return;
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
		OutputStream out = null;
		byte[] buf = new byte[1024];
		int legth = 0;
		try {
			out = httpServletResponse.getOutputStream();
			while ((legth = in.read(buf)) != -1) {
				out.write(buf, 0, legth);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

}