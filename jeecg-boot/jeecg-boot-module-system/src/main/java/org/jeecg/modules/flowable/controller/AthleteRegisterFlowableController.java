package org.jeecg.modules.flowable.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.modules.edusport.entity.Coach;
import org.jeecg.modules.edusport.entity.CoachLeave;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.ICoachLeaveService;
import org.jeecg.modules.edusport.service.ICoachService;
import org.jeecg.modules.flowable.handle.DeleteOthersTaskCmd;
import org.jeecg.modules.flowable.vo.AthleteRegisterTaskVo;
import org.jeecg.modules.flowable.vo.CoachLeaveRequestHistoryVo;
import org.jeecg.modules.flowable.vo.CoachLeaveRequestTaskVo;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @description：运动员注册流程图测试
 * @author:whs
 * @date: 2020/7/22 11:02
 */
@Api(tags="运动员注册申请工作流")
@RequestMapping("/sys/user/register")
@RestController
@Slf4j
public class AthleteRegisterFlowableController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ICoachService coachService;

    @Autowired
    private IAthleteService athleteService;


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
     * @param athleteRegisterTaskVo
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody AthleteRegisterTaskVo athleteRegisterTaskVo, HttpServletRequest req) {
        // 获取申请人：运动员
        System.out.println(req);
        String userName = JwtUtil.getUserNameByToken(req);
        SysUser sysUserCoach = sysUserService.getUserByNameAndType("13576976968", "2");
        if(sysUserCoach == null) {
            return Result.error("Token无效!");
        }

        Coach coach = coachService.getOne(new QueryWrapper<Coach>().eq("mobile", sysUserCoach.getUsername()));
        System.out.println(sysUserCoach.getUsername());
        if(coach  == null) {
            return Result.error("未匹配教练!");
        }


        // 处理业务逻辑
        String requestId = UUIDGenerator.generate();
        athleteRegisterTaskVo.setRequestId(requestId);
        athleteRegisterTaskVo.setRequestDate(new Date());
        athleteRegisterTaskVo.setCoachUserId(sysUserCoach.getId());

        if ("thingsLeaveRequest".equals(athleteRegisterTaskVo.getRequestType())
                || "sickLeaveRequest".equals(coachleaveRequestTaskVo.getRequestType())
                || "trainLeaveRequest".equals(coachleaveRequestTaskVo.getRequestType())
                || "matchLeaveRequest" .equals(coachleaveRequestTaskVo.getRequestType())) {
            CoachLeave coachLeave = new CoachLeave();
            coachLeave.setId(requestId);
            coachLeave.setCoach_id(coachleaveRequestTaskVo.getCoachUserId());
            coachLeave.setStartDate(coachleaveRequestTaskVo.getStartDate());
            coachLeave.setEndDate(coachleaveRequestTaskVo.getEndDate());
            coachLeave.setReason(coachleaveRequestTaskVo.getReason());
            coachLeave.setWorkflowStatus("待审批");
            coachLeaveService.save(coachLeave);
        }else {
            return Result.error("请假类型设置错误");
        }




        // 处理工作流任务
        // 设置请假流程参数
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("requestCoach", coachleaveRequestTaskVo.getCoachUserId());
        variables.put("requestType", coachleaveRequestTaskVo.getRequestType());
        variables.put("requestId", coachleaveRequestTaskVo.getRequestId());
        variables.put("requestDate", coachleaveRequestTaskVo.getRequestDate());
        variables.put("startDate", coachleaveRequestTaskVo.getStartDate());
        variables.put("endDate", coachleaveRequestTaskVo.getEndDate());

        // 计算请假天数并设置
        Calendar startCal=Calendar.getInstance();
        startCal.setTime(coachleaveRequestTaskVo.getStartDate());
        Calendar endCal=Calendar.getInstance();
        endCal.setTime(coachleaveRequestTaskVo.getEndDate());
        int requestDays = DateUtils.dateDiff('d', endCal, startCal);
        variables.put("requestDays", "" + requestDays);

        // 设置请假原因
        variables.put("reason", coachleaveRequestTaskVo.getReason());
        // 设置审批训练科组
        variables.put("trainingApprovalGroup", "trainingApprovalGroup");
        // 设置审批副校长组
        variables.put("viceHeadmasterApprovalGroup", "viceHeadmasterApprovalGroup");
        // 设置审批校长组
        variables.put("headmasterApprovalGroup", "headmasterApprovalGroup");


        // 创建请假流程，并设置流程发起人
        String businessKey = coachleaveRequestTaskVo.getRequestType() + ":" + requestId;
        Authentication.setAuthenticatedUserId(coachleaveRequestTaskVo.getCoachUserId());
        //下面这一行coachLeave参数是和流程图连接关键
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("coachLeave", businessKey, variables);
        Authentication.setAuthenticatedUserId(null);

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        if (task.getTaskDefinitionKey().equals("coachRequest")) {
            //上面这一行括号里的数据要和流程图userTask的一样
            taskService.setVariable(task.getId(), "approvalResult", "申请");
            taskService.setVariable(task.getId(), "requestCoachName", coach.getCoachName());
            taskService.setVariableLocal(task.getId(), "approvalResult", "申请");
            taskService.setVariableLocal(task.getId(), "assigneeName",  coach.getCoachName());
            taskService.addComment(task.getId(), pi.getId(), coachleaveRequestTaskVo.getReason());
            taskService.claim(task.getId(), Convert.toStr(variables.get("requestCoach")));
            taskService.complete(task.getId());
        }
        return Result.ok("添加成功！");
    }

    /**
     * 通过id查询任务详情
     *
     //     * @param id
     * @return
     */
    @GetMapping(value = "/queryByProcessId")
    public Result<?> queryById(@RequestParam(name="processId", required=true) String processId) {
        CoachLeaveRequestTaskVo coachleaveRequestTaskVo = new CoachLeaveRequestTaskVo();
        List<CoachLeaveRequestHistoryVo> coachleaveRequestHistoryVoList = new ArrayList();

        List<HistoricVariableInstance> hisVariableList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processId).excludeTaskVariables().list();
        Map<String, Object> variables = new HashMap<String, Object>();
        hisVariableList.forEach(hisVariable -> {
            variables.put(hisVariable.getVariableName(), hisVariable.getValue());
        });

        coachleaveRequestTaskVo.setRequestType(Convert.toStr(variables.get("requestType")));
        coachleaveRequestTaskVo.setRequestId(Convert.toStr(variables.get("requestId")));
        coachleaveRequestTaskVo.setRequestDate(Convert.toDate(variables.get("requestDate")));
        coachleaveRequestTaskVo.setStartDate(Convert.toDate(variables.get("startDate")));
        coachleaveRequestTaskVo.setEndDate(Convert.toDate(variables.get("endDate")));
        coachleaveRequestTaskVo.setRequestDays(Convert.toStr(variables.get("requestDays")));
        coachleaveRequestTaskVo.setReason(Convert.toStr(variables.get("reason")));
        coachleaveRequestTaskVo.setProcessId(processId);

        // 获取当前节点
        List<Task> runtimeTasks = taskService.createTaskQuery().processInstanceId(processId).list();
        String workflowNode = "";
        for (int i = 0; i < runtimeTasks.size(); i++) {
            if (workflowNode.length() > 0) {
                workflowNode += ", ";
            }
            workflowNode += runtimeTasks.get(i).getName();
        }
        coachleaveRequestTaskVo.setWorkflowNode(workflowNode);


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

                CoachLeaveRequestHistoryVo coachleaveRequestHistoryVo = new CoachLeaveRequestHistoryVo();
                coachleaveRequestHistoryVo.setProcessId(processId);
                coachleaveRequestHistoryVo.setTaskId(task.getId());
                coachleaveRequestHistoryVo.setWorkflowNode(task.getName());
                coachleaveRequestHistoryVo.setApproverId(task.getAssignee());
                coachleaveRequestHistoryVo.setApprover(assigneeName);
                coachleaveRequestHistoryVo.setApprovalResult(approvalResult);
                coachleaveRequestHistoryVo.setApprovalComment(approvalComment);
                coachleaveRequestHistoryVo.setApprovalDate(task.getEndTime());

                coachleaveRequestHistoryVoList.add(coachleaveRequestHistoryVo);
            }
        });

        coachleaveRequestTaskVo.setApprovalList(coachleaveRequestHistoryVoList);

        return Result.ok(coachleaveRequestTaskVo);
    }

    /**
     * 分页列表查询（我的待办任务/我的所有任务）
     *
     //     * @param coachleaveRequestTaskVo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/taskList")
    public Result<?> queryMyHistoricTaskList(
            CoachLeaveRequestTaskVo coachleaveRequestTaskVoQeuary,
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
                case "coach":
                    break;
                case "training":
                    candidateGroup.add("trainingApprovalGroup");
                    break;
                case "viceHeadmater":
                    candidateGroup.add("viceHeadmasterApprovalGroup");
                    break;
                case "headmater":
                    candidateGroup.add("headmasterApprovalGroup");
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

        Page<CoachLeaveRequestTaskVo> page = new Page<CoachLeaveRequestTaskVo>(pageNo, pageSize);
        Long hisPIsCount = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds)
                .orderByProcessInstanceStartTime().desc().count();
        page.setTotal(hisPIsCount);

        if (hisPIsCount > 0) {
            List<HistoricProcessInstance> hisPIs = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds)
                    .orderByProcessInstanceStartTime().desc().listPage(pageNo-1, pageSize);

            List<CoachLeaveRequestTaskVo> coachleaveRequestTaskVoList = new ArrayList<CoachLeaveRequestTaskVo>();
            hisPIs.forEach(hisPI -> {

                // 获取工作流变量
                List<HistoricVariableInstance> hisVariableList =
                        historyService.createHistoricVariableInstanceQuery().processInstanceId(hisPI.getId())
                                .excludeTaskVariables().list();
                Map<String, Object> variables = new HashMap<String, Object>();
                hisVariableList.forEach(hisVariable -> {
                    variables.put(hisVariable.getVariableName(), hisVariable.getValue());
                });

                //这里提供页面显示的信息
                CoachLeaveRequestTaskVo coachleaveRequestTaskVo = new CoachLeaveRequestTaskVo();
                coachleaveRequestTaskVo.setCoachUserId(Convert.toStr(variables.get("requestCoach")));
                coachleaveRequestTaskVo.setCoachName(Convert.toStr(variables.get("requestCoachName")));
                coachleaveRequestTaskVo.setRequestType(Convert.toStr(variables.get("requestType")));
                coachleaveRequestTaskVo.setRequestId(Convert.toStr(variables.get("requestId")));
                coachleaveRequestTaskVo.setRequestDate(Convert.toDate(variables.get("requestDate")));
                coachleaveRequestTaskVo.setStartDate(Convert.toDate(variables.get("startDate")));
                coachleaveRequestTaskVo.setEndDate(Convert.toDate(variables.get("endDate")));

                coachleaveRequestTaskVo.setWorkflowStatus(Convert.toStr(variables.get("workflowStatus")));

                coachleaveRequestTaskVo.setRequestDays(Convert.toStr(variables.get("requestDays")));
                coachleaveRequestTaskVo.setReason(Convert.toStr(variables.get("reason")));
                coachleaveRequestTaskVo.setProcessId(hisPI.getId());

                // 获取用户当前任务
                Task userTask =
                        taskService.createTaskQuery().processInstanceId(hisPI.getId())
                                .or().taskAssignee(userId).taskCandidateUser(userId).taskCandidateGroupIn(candidateGroup).endOr()
                                .orderByTaskCreateTime().asc().singleResult();
                if (userTask != null) {
                    coachleaveRequestTaskVo.setTaskId(userTask.getId());
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
                coachleaveRequestTaskVo.setWorkflowNode(workflowNode);

                // 添加列表数据
                coachleaveRequestTaskVoList.add(coachleaveRequestTaskVo);
            });

            page.setRecords(coachleaveRequestTaskVoList);
        }

        return Result.ok(page);
    }


    /**
     * 更新工作流（同意、驳回、转办、意见征询、回退）
     *
     */
    @PutMapping(value = "/update")
    public Result<?> update(@RequestBody CoachLeaveRequestTaskVo leaveRequestTaskVo, HttpServletRequest req) {
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
