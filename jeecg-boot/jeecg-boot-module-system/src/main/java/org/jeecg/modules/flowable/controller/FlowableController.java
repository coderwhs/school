package org.jeecg.modules.flowable.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.test.entity.JeecgDemo;
import org.jeecg.modules.demo.test.service.IJeecgDemoService;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.entity.TactRuTask;
import org.jeecg.modules.edusport.mapper.DormAthleteLeaveMapper;
import org.jeecg.modules.edusport.service.IDormAthleteLeaveService;
import org.jeecg.modules.edusport.util.UserUtil;
import org.jeecg.modules.flowable.service.IProcessService;
import org.jeecg.modules.shiro.vo.DefContants;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "工作流测试DEMO")
@RestController
//@Controller
@RequestMapping(value = "/process")
public class FlowableController extends JeecgController<JeecgDemo, IJeecgDemoService> {
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
	@Autowired
	private IProcessService processServices;
	@Autowired
	private IDormAthleteLeaveService dormAthleteLeaveService;

	/*************** 此处为业务代码 ******************/

	@RequestMapping(value = "create")
	@ResponseBody
	@Transactional
	public String createFlowable(HttpServletRequest request, @RequestBody DormAthleteLeave dormAthleteLeave) {
		dormAthleteLeaveMapper.updateWorkflowState(dormAthleteLeave.getId());
		System.out.println("提交成功abcdefg");

		HashMap<String, Object> map1 = new HashMap<>();

		String userName = UserUtil.getSystemUser(request, sysUserService).getUsername();
		map1.put("taskUser", userName);
		map1.put("leaveType", 1);
		processServices.addFlowable(userName, "Leave", dormAthleteLeave.getId(), map1);
		List<String> tasks = list(userName);
		if (tasks.size() != 0) {
			String taskId = tasks.get(0);
//			apply(dormAthleteLeave, tasks.get(0));

			// 更改状态.
			dormAthleteLeave = dormAthleteLeaveService.getById(dormAthleteLeave.getId());
			dormAthleteLeave.setWorkflowStatus("2");
			dormAthleteLeaveService.saveOrUpdate(dormAthleteLeave);
			System.out.println("==============" + dormAthleteLeave.getId());

			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if (task == null) {
				throw new RuntimeException("流程不存在");
			}
			// 通过审核
			HashMap<String, Object> map = new HashMap<>();
			map.put("outcome", "通过");
			map.put("nextHandler", userName);
			map.put("billType", "leave");
			map.put("billId", dormAthleteLeave.getId());
			taskService.complete(taskId, map);
		}

		return "提交成功.";
	}

	/**
	 * 启动一个工作流
	 *
	 * @param userId    用户Id
	 * @param money     报销金额
	 * @param descption 描述
	 */

	@ApiOperation(value = "添加流程", notes = "添加工作流")
	@RequestMapping(value = "add")
	@ResponseBody
	@Transactional
	public String addFlowable(HttpServletRequest request, String userId, String leaveType, String billId) {
		// 启动流程
		HashMap<String, Object> map = new HashMap<>();

		String userName = UserUtil.getSystemUser(request, sysUserService).getUsername();
		map.put("taskUser", userName);
		map.put("leaveType", 1);
		return processServices.addFlowable(userName, "leave", billId, map);

//		// 启动流程
//		HashMap<String, Object> map = new HashMap<>();
//		
//		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//		map.put("taskUser", userId);
//		map.put("leaveType", leaveType);
//		String billType = "SickLeave";// 宿舍请假单.
//		if("2".equals(leaveType)) {
//			billType = "PersonalLeave";// 考勤请假单.
//		}
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Leave",billId, map);
//		System.out.println( "提交成功.流程Id为：" + processInstance.getId());
//		return "提交成功.流程Id为：" + processInstance.getId();
	}

	/**
	 * 获取审批管理列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public List<String> list(String userId) {
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
		List<String> results = Lists.newArrayList();
		for (Task task : tasks) {
			results.add(task.getId());
		}
		return results;
	}

	/**
	 * 批准
	 *
	 * @param taskId 任务ID
	 */
	@PutMapping(value = "/apply")
	@Transactional
	public String apply(HttpServletRequest request, @RequestBody TactRuTask tactRuTask) {
		// 更改状态.
//		dormAthleteLeaveMapper.updateWorkflowState(dormAthleteLeave.getId());
//		dormAthleteLeave.setWorkflowState("3");
//		System.out.println("==============" + dormAthleteLeave.getId());
		SysUser user = UserUtil.getSystemUser(request, sysUserService);
		List<String> tasks = list(user.getUsername());
		if (tasks != null && tasks.size() != 0) {
			String taskId = tasks.get(0);
//			apply(dormAthleteLeave, tasks.get(0));

			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if (task == null) {
				throw new RuntimeException("流程不存在");
			}
			String processInstanceId = task.getProcessInstanceId();
	        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	        DormAthleteLeave dormAthleteLeave = dormAthleteLeaveService.getById(processInstance.getBusinessKey());
	        long reqDays = DateUtil.between(dormAthleteLeave.getStartDate(), dormAthleteLeave.getEndDate(), DateUnit.DAY);
	        System.out.println("请假天数：" + reqDays);
			// 通过审核
			HashMap<String, Object> map = new HashMap<>();
			map.put("outcome", "通过");
			map.put("nextHandler", user.getId());
			map.put("reqDays", reqDays);
			taskService.complete(taskId, map);
		}
		return "processed ok!";
	}

	/**
	 * 拒绝
	 */
	@Transactional
	@PutMapping(value = "reject")
	public String reject(HttpServletRequest request, @RequestBody TactRuTask tactRuTask) {
		// 更改状态.
		DormAthleteLeave dormAthleteLeave = dormAthleteLeaveService.getById(tactRuTask.getParentTaskId());
		dormAthleteLeave.setWorkflowStatus("4");// 驳回.
		dormAthleteLeaveService.saveOrUpdate(dormAthleteLeave);
		System.out.println("==============" + dormAthleteLeave.getId());
		SysUser user = UserUtil.getSystemUser(request, sysUserService);
		List<String> tasks = list(user.getUsername());
		if (tasks != null && tasks.size() != 0) {
			String taskId = tasks.get(0);
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if (task == null) {
				throw new RuntimeException("流程不存在");
			}
			// 驳回.
			HashMap<String, Object> map = new HashMap<>();
			map.put("outcome", "驳回");
			taskService.complete(taskId, map);
		}

		return "reject!";
	}

	/**
	 * 生成流程图
	 *
	 * @param processId 任务ID
	 */
	@RequestMapping(value = "processDiagram")
	public void processDiagram(HttpServletResponse httpServletResponse,
			@RequestParam(name = "procInstId", required = false) String procInstId) throws Exception {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).singleResult();
		httpServletResponse.setContentType("image/png");
//		httpServletResponse.setContentType("application/x-img");
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