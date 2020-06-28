package org.jeecg.modules.flowable.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.entity.AthleteSelectionTestCoach;
import org.jeecg.modules.edusport.entity.Coach;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.entity.SportClassAthleteLeave;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.ICoachService;
import org.jeecg.modules.edusport.service.IDormAthleteLeaveService;
import org.jeecg.modules.edusport.service.ISportClassAthleteLeaveService;
import org.jeecg.modules.flowable.handle.DeleteOthersTaskCmd;
import org.jeecg.modules.flowable.vo.LeaveRequestHistoryVo;
import org.jeecg.modules.flowable.vo.LeaveRequestTaskVo;
import org.jeecg.modules.shiro.vo.DefContants;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import javassist.expr.NewArray;
import lombok.extern.slf4j.Slf4j;

@Api(tags="请假申请工作流")
@RequestMapping("/edusport/leaveRequest")
@RestController
@Slf4j
public class LeaveRequestFlowableController {
	
	@Autowired
	private ISysUserService sysUserService;
	// 运动员
	@Autowired
	private IAthleteService athleteService;
	@Autowired
	private ICoachService coachService;
	// 宿舍请假
	@Autowired
	private IDormAthleteLeaveService dormAthleteLeaveService;
	// 训练课请假
	@Autowired
	private ISportClassAthleteLeaveService sportClassAthleteLeaveService;
	
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
	@Autowired  
	private HistoryService historyService; 
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    
	/**
	 *   创建工作流
	 *
	 * @param leaveRequestTaskVo
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody LeaveRequestTaskVo leaveRequestTaskVo, HttpServletRequest req) {
		// 设置请假流程参数
//		Map<String, Object> variables = JSON.parseObject(JSON.toJSONString(leaveRequestTaskVo), Map.class);
		
		// 获取申请人：运动员
		String userName = JwtUtil.getUserNameByToken(req);
		SysUser sysUserAthlete = sysUserService.getUserByNameAndType("18621966866", "3");
	    if(sysUserAthlete == null) {
	    	return Result.error("Token无效!");
	    }
	    
    	if (1 == sysUserAthlete.getUserType()) {
    		// 教务人员, 看到所有运动员
    		
    		// Skip do something
    		
    	} else if (2 == sysUserAthlete.getUserType()) {
    		// 教练员, 教练员只能看到所带班级运动员，只能看到自己所在队及
    		
    	} else if (3 == sysUserAthlete.getUserType()) {
    		// 运动员, 运动员只能看到自己，只能看到自己所在队及所在队的教练
    	}
	    
		Athlete athlete = athleteService.getOne(new QueryWrapper<Athlete>().eq("mobile", sysUserAthlete.getUsername()));
	    if(athlete == null) {
	    	return Result.error("未匹配运动员!");
	    }
	    
		// 获取审批执行人：教练员
	    Coach coach = coachService.getById(athlete.getCoachId());
	    if(coach == null) {
	    	return Result.error("该运动员无带训教练员!");
	    }
	    SysUser sysUserCoach = sysUserService.getUserByNameAndType(coach.getMobile(), "2");
	    if(sysUserCoach == null) {
	    	return Result.error("该带训教练员未开通账号!");
	    }
	    
		// 获取审批执行人：班主任
	    SysUser classTeacher = sysUserService.getById(athlete.getClassTeacherId());
	    
		// 获取审批候选组：训练科组
		// 获取审批候选组：校长组
		// 获取审批候选组: 管理员组
	    
	    
		// 处理业务逻辑
		String requestId = UUIDGenerator.generate();
		leaveRequestTaskVo.setRequestId(requestId);
		leaveRequestTaskVo.setRequestDate(new Date());
		leaveRequestTaskVo.setAthleteUserId(sysUserAthlete.getId());
		leaveRequestTaskVo.setCoachUserId(sysUserCoach.getId());
		if ("dormLeaveRequest".equals(leaveRequestTaskVo.getRequestType())) {
			if (StringUtils.isEmpty(leaveRequestTaskVo.getDormId())) {
				return Result.error("未选择宿舍");
			}
			
			DormAthleteLeave dormAthleteLeave = new DormAthleteLeave();
			dormAthleteLeave.setId(requestId);
			dormAthleteLeave.setDormId(leaveRequestTaskVo.getDormId());
			dormAthleteLeave.setAthleteId(leaveRequestTaskVo.getAthleteUserId());
			dormAthleteLeave.setStartDate(leaveRequestTaskVo.getStartDate());
			dormAthleteLeave.setEndDate(leaveRequestTaskVo.getEndDate());
			dormAthleteLeave.setReason(leaveRequestTaskVo.getReason());
			dormAthleteLeave.setWorkflowStatus("待审批");
			dormAthleteLeaveService.save(dormAthleteLeave);
		} else if ("classLeaveRequest".equals(leaveRequestTaskVo.getRequestType())) {
			if (StringUtils.isEmpty(leaveRequestTaskVo.getSportClassId())) {
				return Result.error("未选择班级");
			}
			
			SportClassAthleteLeave sportClassAthleteLeave = new SportClassAthleteLeave();
			sportClassAthleteLeave.setId(requestId);
			sportClassAthleteLeave.setSportClassId(leaveRequestTaskVo.getSportClassId());
			sportClassAthleteLeave.setAthleteId(leaveRequestTaskVo.getAthleteUserId());
			sportClassAthleteLeave.setStartDate(leaveRequestTaskVo.getStartDate());
			sportClassAthleteLeave.setEndDate(leaveRequestTaskVo.getEndDate());
			sportClassAthleteLeave.setReason(leaveRequestTaskVo.getReason());
			sportClassAthleteLeave.setWorkflowStatus("待审批");
			sportClassAthleteLeaveService.save(sportClassAthleteLeave);
			
		} else {
			return Result.error("请假类型设置错误");
		}
		

		
		// 处理工作流任务
		// 设置请假流程参数
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("requestAthlete", leaveRequestTaskVo.getAthleteUserId());
		
		// 请假类型
		variables.put("requestType", leaveRequestTaskVo.getRequestType());
		// 设置请假宿舍
		variables.put("dormId", leaveRequestTaskVo.getDormId());
		// 设置请假班级
		variables.put("sportClassId", leaveRequestTaskVo.getSportClassId());

		variables.put("requestId", leaveRequestTaskVo.getRequestId());
		variables.put("requestDate", leaveRequestTaskVo.getRequestDate());
		variables.put("startDate", leaveRequestTaskVo.getStartDate());
		variables.put("endDate", leaveRequestTaskVo.getEndDate());
		
		// 计算请假天数并设置
		Calendar startCal=Calendar.getInstance();
		startCal.setTime(leaveRequestTaskVo.getStartDate());
		Calendar endCal=Calendar.getInstance();
		endCal.setTime(leaveRequestTaskVo.getEndDate());
		int requestDays = DateUtils.dateDiff('d', endCal, startCal);
		variables.put("requestDays", "" + requestDays);
		
		// 设置请假原因
		variables.put("reason", leaveRequestTaskVo.getReason());
		// 设置审批教练
		variables.put("approvalCoach", leaveRequestTaskVo.getCoachUserId());
		// 设置审批班主任
		if (classTeacher != null) {
			variables.put("approvalTeacher", classTeacher.getId());
			variables.put("isNeedClassTeacherApproval", true);
		} else {
			variables.put("approvalTeacher", "无班主任");
			variables.put("isNeedClassTeacherApproval", false);
		}
		// 设置审批训练科组
		variables.put("trainingApprovalGroup", "trainingApprovalGroup");
		variables.put("isNeedTrainingApproval", true);
		// 设置审批校长组
		variables.put("headmasterApprovalGroup", "headmasterApprovalGroup");
		// 设置审批管理员组
		variables.put("adminApprovalGroup", "adminApprovalGroup");
		
		// 创建请假流程，并设置流程发起人
		String businessKey = leaveRequestTaskVo.getRequestType() + ":" + requestId;
		Authentication.setAuthenticatedUserId(leaveRequestTaskVo.getAthleteUserId());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("leaveRequest", businessKey, variables);
		Authentication.setAuthenticatedUserId(null);
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		if (task.getTaskDefinitionKey().equals("athleteRequestTask")) {
			taskService.setVariable(task.getId(), "approvalResult", "申请");
			taskService.setVariable(task.getId(), "requestAthleteName", athlete.getAthleteName());
			taskService.setVariableLocal(task.getId(), "approvalResult", "申请");
			taskService.setVariableLocal(task.getId(), "assigneeName", athlete.getAthleteName());
			taskService.addComment(task.getId(), pi.getId(), leaveRequestTaskVo.getReason());
			taskService.claim(task.getId(), Convert.toStr(variables.get("requestAthlete")));
			taskService.complete(task.getId());
		}
		
		return Result.ok("添加成功！");
	}
	
	/**
	 * 通过id查询任务详情
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryByProcessId")
	public Result<?> queryById(@RequestParam(name="processId", required=true) String processId) {
		LeaveRequestTaskVo leaveRequestTaskVo = new LeaveRequestTaskVo();
		List<LeaveRequestHistoryVo> leaveRequestHistoryVoList = new ArrayList();
		
		List<HistoricVariableInstance> hisVariableList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processId).excludeTaskVariables().list();
		Map<String, Object> variables = new HashMap<String, Object>();
		hisVariableList.forEach(hisVariable -> {
			variables.put(hisVariable.getVariableName(), hisVariable.getValue());
		});
		
		leaveRequestTaskVo.setAthleteUserId(Convert.toStr(variables.get("requestAthlete")));
		leaveRequestTaskVo.setAthleteName(Convert.toStr(variables.get("requestAthleteName")));
		leaveRequestTaskVo.setRequestType(Convert.toStr(variables.get("requestType")));
		leaveRequestTaskVo.setDormId(Convert.toStr(variables.get("dormId")));
		leaveRequestTaskVo.setSportClassId(Convert.toStr(variables.get("sportClassId")));
		leaveRequestTaskVo.setRequestId(Convert.toStr(variables.get("requestId")));
		leaveRequestTaskVo.setRequestDate(Convert.toDate(variables.get("requestDate")));
		leaveRequestTaskVo.setStartDate(Convert.toDate(variables.get("startDate")));
		leaveRequestTaskVo.setEndDate(Convert.toDate(variables.get("endDate")));
		leaveRequestTaskVo.setRequestDays(Convert.toStr(variables.get("requestDays")));
		leaveRequestTaskVo.setReason(Convert.toStr(variables.get("reason")));
		leaveRequestTaskVo.setProcessId(processId);
		
		// 获取当前节点
		List<Task> runtimeTasks = taskService.createTaskQuery().processInstanceId(processId).list();
		String workflowNode = "";
		for (int i = 0; i < runtimeTasks.size(); i++) {
			if (workflowNode.length() > 0) {
				workflowNode += ", ";
			}
			workflowNode += runtimeTasks.get(i).getName();
		}
		leaveRequestTaskVo.setWorkflowNode(workflowNode);
		
		
		List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processId).orderByTaskCreateTime().asc().list();
		if(hisTasks == null) {
			return Result.error("未找到对应数据");
		}
		
		hisTasks.forEach(task -> {
			if (task.getDeleteReason() == null && task.getEndTime() != null) {
				// 审批人
				HistoricVariableInstance hisVarAssigneeName = 
						historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(task.getProcessInstanceId())
						.taskId(task.getId()).variableName("assigneeName").singleResult();
				String assigneeName = hisVarAssigneeName == null ? "" : Convert.toStr(hisVarAssigneeName.getValue());
				
				// 审批结果
				HistoricVariableInstance hisVarApprovalResult = 
						historyService.createHistoricVariableInstanceQuery()
						.processInstanceId(task.getProcessInstanceId())
						.taskId(task.getId()).variableName("approvalResult").singleResult();
				String approvalResult = hisVarApprovalResult == null ? "" : Convert.toStr(hisVarApprovalResult.getValue());
				
				// 审批意见
				Comment comment = taskService.getTaskComments(task.getId()).get(0);
				String approvalComment = comment == null ? "" : Convert.toStr(comment.getFullMessage());
				
				LeaveRequestHistoryVo leaveRequestHistoryVo = new LeaveRequestHistoryVo();
				leaveRequestHistoryVo.setProcessId(processId);
				leaveRequestHistoryVo.setTaskId(task.getId());
				leaveRequestHistoryVo.setWorkflowNode(task.getName());
				leaveRequestHistoryVo.setApproverId(task.getAssignee());
				leaveRequestHistoryVo.setApprover(assigneeName);
				leaveRequestHistoryVo.setApprovalResult(approvalResult);
				leaveRequestHistoryVo.setApprovalComment(approvalComment);
				leaveRequestHistoryVo.setApprovalDate(task.getEndTime());
				
				leaveRequestHistoryVoList.add(leaveRequestHistoryVo);
			}
		});

		leaveRequestTaskVo.setApprovalList(leaveRequestHistoryVoList);
		
		return Result.ok(leaveRequestTaskVo);
	}
	
	/**
	 * 分页列表查询（我的待办任务/我的所有任务）
	 *
	 * @param leaveRequestTaskVo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/taskList")
	public Result<?> queryMyHistoricTaskList(
			LeaveRequestTaskVo leaveRequestTaskVoQeuary,
			@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
			HttpServletRequest req) {
		
		// 获取用户信息
		String userName = JwtUtil.getUserNameByToken(req);
		SysUser sysUser = sysUserService.getUserByName(userName);
		List<String> roles = sysUserService.getRole(userName);
	    if(sysUser == null) {
	    	return Result.error("Token无效!");
	    }
	    String userId = sysUser.getId();
	    
	    Set<String> candidateGroup = new HashSet<String>();
	    candidateGroup.add("");
	    roles.forEach(role -> {
	    	switch (role) {
			case "athlete":
				break;
			case "coach":
				break;
			case "teacher":
				break;
			case "training":
				candidateGroup.add("trainingApprovalGroup");
				break;
			case "headmater":
				candidateGroup.add("headmasterApprovalGroup");
				break;
			case "admin":
				candidateGroup.add("adminApprovalGroup");
				break;
			default:
				break;
			}
	    });
	    
		// 我的所有参与的流程(分页)
		List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery()
				.or().taskAssignee(userId).taskCandidateUser(userId).taskCandidateGroupIn(candidateGroup)
				.endOr().orderByTaskCreateTime().desc().list();
	    
		Set<String> processInstanceIds = new HashSet<String>(); 
		hisTasks.forEach(task -> {
			processInstanceIds.add(task.getProcessInstanceId());
		});
		
		Page<LeaveRequestTaskVo> page = new Page<LeaveRequestTaskVo>(pageNo, pageSize);
		Long hisPIsCount = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds)
				.orderByProcessInstanceStartTime().desc().count();
		page.setTotal(hisPIsCount);
		
		if (hisPIsCount > 0) {
			List<HistoricProcessInstance> hisPIs = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds)
					.orderByProcessInstanceStartTime().desc().listPage(pageNo-1, pageSize);
			
			List<LeaveRequestTaskVo> leaveRequestTaskVoList = new ArrayList<LeaveRequestTaskVo>();
			hisPIs.forEach(hisPI -> {
				
				// 获取工作流变量
				List<HistoricVariableInstance> hisVariableList = 
						historyService.createHistoricVariableInstanceQuery().processInstanceId(hisPI.getId())
						.excludeTaskVariables().list();
				Map<String, Object> variables = new HashMap<String, Object>();
				hisVariableList.forEach(hisVariable -> {
					variables.put(hisVariable.getVariableName(), hisVariable.getValue());
				});
				
				LeaveRequestTaskVo leaveRequestTaskVo = new LeaveRequestTaskVo();
				leaveRequestTaskVo.setAthleteUserId(Convert.toStr(variables.get("requestAthlete")));
				leaveRequestTaskVo.setAthleteName(Convert.toStr(variables.get("requestAthleteName")));
				leaveRequestTaskVo.setRequestType(Convert.toStr(variables.get("requestType")));
				leaveRequestTaskVo.setDormId(Convert.toStr(variables.get("dormId")));
				leaveRequestTaskVo.setSportClassId(Convert.toStr(variables.get("sportClassId")));
				leaveRequestTaskVo.setRequestId(Convert.toStr(variables.get("requestId")));
				leaveRequestTaskVo.setRequestDate(Convert.toDate(variables.get("requestDate")));
				leaveRequestTaskVo.setStartDate(Convert.toDate(variables.get("startDate")));
				leaveRequestTaskVo.setEndDate(Convert.toDate(variables.get("endDate")));
				leaveRequestTaskVo.setRequestDays(Convert.toStr(variables.get("requestDays")));
				leaveRequestTaskVo.setReason(Convert.toStr(variables.get("reason")));
				leaveRequestTaskVo.setProcessId(hisPI.getId());

				// 获取用户当前任务
				Task userTask = 
						taskService.createTaskQuery().processInstanceId(hisPI.getId())
						.or().taskAssignee(userId).taskCandidateUser(userId).taskCandidateGroupIn(candidateGroup).endOr()
						.orderByTaskCreateTime().asc().singleResult();
				if (userTask != null) {
					leaveRequestTaskVo.setTaskId(userTask.getId());
				}
				
				// 获取流程当前节点
				List<Task> runtimeTasks = 
						taskService.createTaskQuery().processInstanceId(hisPI.getId())
						.orderByTaskCreateTime().asc().list();
				String workflowNode = "";
				for (int i = 0; i < runtimeTasks.size(); i++) {
					if (workflowNode.length() > 0) {
						workflowNode += ", ";
					}
					workflowNode += runtimeTasks.get(i).getName();
				}
				leaveRequestTaskVo.setWorkflowNode(workflowNode);
				
				// 添加列表数据
				leaveRequestTaskVoList.add(leaveRequestTaskVo);
			});
			
			page.setRecords(leaveRequestTaskVoList);
		}
		
		// 我的所有参与的流程(分页)
//		Page<LeaveRequestTaskVo> page = new Page<LeaveRequestTaskVo>(pageNo, pageSize);
//		Long hisTasksCount = historyService.createHistoricTaskInstanceQuery()
//				.or().taskAssignee(userId).taskCandidateUser(userId).taskCandidateGroupIn(candidateGroup)
//				.endOr().orderByTaskCreateTime().desc().count();
//		page.setTotal(hisTasksCount);
//		
//		if (hisTasksCount > 0) {
//			List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery()
//					.or().taskAssignee(userId).taskCandidateUser(userId).taskCandidateGroupIn(candidateGroup)
//					.endOr().orderByTaskCreateTime().desc().listPage(pageNo-1, pageSize);
//			
//			List<LeaveRequestTaskVo> leaveRequestTaskVoList = new ArrayList();
//			hisTasks.forEach(hisTask -> {
//				List<HistoricVariableInstance> hisVariableList = historyService.createHistoricVariableInstanceQuery().processInstanceId(hisTask.getProcessInstanceId()).excludeTaskVariables().list();
//				
//				
//				Map<String, Object> variables = new HashMap<String, Object>();
//				hisVariableList.forEach(hisVariable -> {
//					variables.put(hisVariable.getVariableName(), hisVariable.getValue());
//				});
//				
//				LeaveRequestTaskVo leaveRequestTaskVo = new LeaveRequestTaskVo();
//				leaveRequestTaskVo.setAthleteUserId(Convert.toStr(variables.get("requestAthlete")));
//				leaveRequestTaskVo.setAthleteName(Convert.toStr(variables.get("requestAthleteName")));
//				leaveRequestTaskVo.setRequestType(Convert.toStr(variables.get("requestType")));
//				leaveRequestTaskVo.setDormId(Convert.toStr(variables.get("dormId")));
//				leaveRequestTaskVo.setSportClassId(Convert.toStr(variables.get("sportClassId")));
//				leaveRequestTaskVo.setRequestId(Convert.toStr(variables.get("requestId")));
//				leaveRequestTaskVo.setRequestDate(Convert.toDate(variables.get("requestDate")));
//				leaveRequestTaskVo.setStartDate(Convert.toDate(variables.get("startDate")));
//				leaveRequestTaskVo.setEndDate(Convert.toDate(variables.get("endDate")));
//				leaveRequestTaskVo.setRequestDays(Convert.toStr(variables.get("requestDays")));
//				leaveRequestTaskVo.setReason(Convert.toStr(variables.get("reason")));
//				leaveRequestTaskVo.setProcessId(hisTask.getProcessInstanceId());
//				leaveRequestTaskVo.setTaskId(hisTask.getId());
//				
//				// 获取当前节点
//				List<Task> runtimeTasks = taskService.createTaskQuery().processInstanceId(hisTask.getProcessInstanceId()).list();
//				String workflowNode = "";
//				for (int i = 0; i < runtimeTasks.size(); i++) {
//					if (workflowNode.length() > 0) {
//						workflowNode += ", ";
//					}
//					workflowNode += runtimeTasks.get(i).getName();
//				}
//				leaveRequestTaskVo.setWorkflowNode(workflowNode);
//				
//				
//				leaveRequestTaskVoList.add(leaveRequestTaskVo);
//			});
//			page.setRecords(leaveRequestTaskVoList);
//		}
		
		return Result.ok(page);
	}
    
    
    /**
     * 更新工作流（同意、驳回、转办、意见征询、回退）
     * 
     */
	@PutMapping(value = "/update")
	public Result<?> update(@RequestBody LeaveRequestTaskVo leaveRequestTaskVo, HttpServletRequest req) {
		// 同意：agree，驳回：reject，转办：delegate，意见征询：consultation，回退：rollback。
		
		// 获取用户信息
		String userName = JwtUtil.getUserNameByToken(req);
		SysUser sysUser = sysUserService.getUserByName(userName);
	    if(sysUser == null) {
	    	return Result.error("Token无效!");
	    }
	    String userId = sysUser.getId();
	    
		// 我的任务
		String taskId = Convert.toStr(leaveRequestTaskVo.getTaskId(), "");
		String approvalResult = Convert.toStr(leaveRequestTaskVo.getApprovalResult(), "");
		String approvalComment = Convert.toStr(leaveRequestTaskVo.getApprovalComment(), "");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			return Result.error("任务不存在！");
		}
		
		if (approvalResult.equals("agree")) {
			taskService.setVariable(task.getId(), "approvalResult", "通过");
			taskService.setVariableLocal(task.getId(), "approvalResult", "通过");
			taskService.setVariableLocal(task.getId(), "assigneeName", sysUser.getRealname());
			taskService.addComment(task.getId(), task.getProcessInstanceId(), approvalComment);
			taskService.complete(task.getId());
		} else if (approvalResult.equals("reject")) {
			taskService.setVariable(task.getId(), "approvalResult", "驳回");
			taskService.setVariableLocal(task.getId(), "approvalResult", "驳回");
			taskService.setVariableLocal(task.getId(), "assigneeName", sysUser.getRealname());
			taskService.addComment(task.getId(), task.getProcessInstanceId(), approvalComment);
			taskService.claim(task.getId(), userId);
			ManagementService managementService = processEngine.getManagementService();
			managementService.executeCommand(new DeleteOthersTaskCmd(task.getProcessInstanceId(), task.getId()));
			taskService.complete(task.getId());
		}
		
		
		return Result.ok("审批完成！");
	}
    /**
     * 更新工作流（同意）
     * 
     */
    
    
    /**
     * 更新工作流（驳回）
     * 
     */
    
    
    /**
     * 更新工作流（转办）
     * 
     */
    
    
    /**
     * 更新工作流（意见征询）
     * 
     */
    
    
    /**
     * 删除工作流 
     * 
     */
    
    
    /**
     * 生成流程图
     * 
     * @param processId 流程Id
     */
    @RequestMapping(value = "processDiagram")
    public void getProcessDiagram(HttpServletResponse httpServletResponse, 
    		@RequestParam(name = "processId", required = false) String processId) throws Exception {
    	
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        // 流程走完的不显示图
        if (pi == null) {
            return;
        }
        
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // 查询运行时服务中，当前任务对应的当前执行
        String taskProcessId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(taskProcessId).list();

        // 取得当前执行对应的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        // 获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        // 设置中文字体
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
