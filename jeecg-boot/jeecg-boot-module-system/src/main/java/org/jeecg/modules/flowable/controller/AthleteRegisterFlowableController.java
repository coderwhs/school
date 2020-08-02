package org.jeecg.modules.flowable.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.entity.Coach;
import org.jeecg.modules.edusport.service.IAthleteRegisterService;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.ICoachService;
import org.jeecg.modules.flowable.handle.DeleteOthersTaskCmd;
import org.jeecg.modules.flowable.vo.AthleteRegisterHistoryVo;
import org.jeecg.modules.flowable.vo.AthleteRegisterTaskVo;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * @description：运动员注册流程
 * @author:whs
 * @date: 2020/7/22 11:02
 */
@Api(tags="运动员注册申请工作流")
@RequestMapping("/athleteRegister")
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
//        System.out.println("传过来的athleteRegisterTaskVo"+athleteRegisterTaskVo);

        Coach coach = coachService.getById(athleteRegisterTaskVo.getCoachId());
//        System.out.println("coach的内容是："+coach);//获取教练的所有信息

        SysUser sysUserCoach = sysUserService.getOne(new QueryWrapper<SysUser>().eq("phone", coach.getMobile()));
        if(sysUserCoach == null){
            return Result.error("未匹配到教练！");
        }
//        System.out.println("sysUserCoach的内容是："+sysUserCoach);

        // 处理业务逻辑
        String registerId = UUIDGenerator.generate();
        athleteRegisterTaskVo.setRegisterId(registerId);
        athleteRegisterTaskVo.setRegisterDate(new Date());
        /**
         * 执行save之后数据就会插入tb_edu_athlete
         * set后面的字段要和数据库字段一致,get后面的字段要和前端的一样
         */


        // 处理工作流任务
        // 设置请假流程参数
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("registerId", athleteRegisterTaskVo.getRegisterId());
        variables.put("athleteName", athleteRegisterTaskVo.getAthleteName());//加上这局，前端审批注册页面，才有注册人
        variables.put("requestAthlete", athleteRegisterTaskVo.getAthleteName());
        variables.put("mobile", athleteRegisterTaskVo.getMobile());
        variables.put("password", athleteRegisterTaskVo.getPassword());
        variables.put("nation", athleteRegisterTaskVo.getNation());
        variables.put("gender", athleteRegisterTaskVo.getGender());
        variables.put("idNo", athleteRegisterTaskVo.getIdNo());
        variables.put("coach", athleteRegisterTaskVo.getCoachId());
        variables.put("sportCode", athleteRegisterTaskVo.getSportCode());
        variables.put("homeAddress", athleteRegisterTaskVo.getHomeAddress());
        variables.put("father", athleteRegisterTaskVo.getFather());
        variables.put("fatherMobile", athleteRegisterTaskVo.getFatherMobile());
        variables.put("mother", athleteRegisterTaskVo.getMother());
        variables.put("motherMobile", athleteRegisterTaskVo.getMotherMobile());
        variables.put("registerDate", athleteRegisterTaskVo.getRegisterDate());
        variables.put("processId", athleteRegisterTaskVo.getProcessId());
        // 设置审批教练员
        variables.put("approvalCoach", sysUserCoach.getId());
        // 设置审批系统管理员
        variables.put("adminApprovalGroup", "adminApprovalGroup");
//        System.out.println("variables的内容是:"+variables);


        // 创建注册流程，并设置注册流程发起人
        String businessKey = athleteRegisterTaskVo.getCoach() + ":" + registerId;
        Authentication.setAuthenticatedUserId(athleteRegisterTaskVo.getAthleteName());
        //下面这一行参数s是和流程图连接关键
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("athleteRegister", businessKey, variables);
        Authentication.setAuthenticatedUserId(null);

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        if ("athleteRequest".equals(task.getTaskDefinitionKey())) {
            //上面这一行括号里的数据要和流程图userTask的一样,          ********感觉这里有点问题，s1的值要和哪个一致？？？
            taskService.setVariable(task.getId(), "approvalResult", "申请");
            taskService.setVariable(task.getId(), "athleteName", athleteRegisterTaskVo.getAthleteName());
            taskService.setVariableLocal(task.getId(), "approvalResult", "申请");
            taskService.setVariableLocal(task.getId(), "assigneeName", athleteRegisterTaskVo.getAthleteName() );
            taskService.claim(task.getId(), Convert.toStr(variables.get("athleteName")));
            taskService.complete(task.getId());
        }
        Athlete athlete = new Athlete();
        athlete.setAthleteName(athleteRegisterTaskVo.getAthleteName());
        athlete.setGender(athleteRegisterTaskVo.getGender());
        athlete.setIdNo(athleteRegisterTaskVo.getIdNo());
        athlete.setNation(athleteRegisterTaskVo.getNation());
        athlete.setMobile(athleteRegisterTaskVo.getMobile());
        athlete.setCoachId(athleteRegisterTaskVo.getCoachId());
        athlete.setSportCode(athleteRegisterTaskVo.getSportCode());
        athlete.setHomeAddress(athleteRegisterTaskVo.getHomeAddress());
        athlete.setFather(athleteRegisterTaskVo.getFather());
        athlete.setFatherMobile(athleteRegisterTaskVo.getFatherMobile());
        athlete.setMother(athleteRegisterTaskVo.getMother());
        athlete.setMotherMobile(athleteRegisterTaskVo.getMotherMobile());
        athlete.setCreateTime(athleteRegisterTaskVo.getRegisterDate());
        athlete.setProcessId(pi.getProcessInstanceId());
//        System.out.println("athlete的内容是"+athlete);
        athleteService.save(athlete);

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
        AthleteRegisterTaskVo athleteRegisterTaskVo = new AthleteRegisterTaskVo();
        List<AthleteRegisterHistoryVo> athleteRegisterHistoryVoList = new ArrayList();

        List<HistoricVariableInstance> hisVariableList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processId).excludeTaskVariables().list();
        Map<String, Object> variables = new HashMap<String, Object>();
        hisVariableList.forEach(hisVariable -> {
            variables.put(hisVariable.getVariableName(), hisVariable.getValue());
        });

        athleteRegisterTaskVo.setRegisterId(Convert.toStr(variables.get("registerId")));
        athleteRegisterTaskVo.setAthleteName(Convert.toStr(variables.get("athleteName")));
        athleteRegisterTaskVo.setMobile(Convert.toStr(variables.get("mobile")));
        athleteRegisterTaskVo.setPassword(Convert.toStr(variables.get("password")));
        athleteRegisterTaskVo.setNation(Convert.toStr(variables.get("nation")));
        athleteRegisterTaskVo.setGender(Convert.toStr(variables.get("gender")));
        athleteRegisterTaskVo.setIdNo(Convert.toStr(variables.get("idNo")));
        athleteRegisterTaskVo.setCoach(Convert.toStr(variables.get("coach")));
        athleteRegisterTaskVo.setSportCode(Convert.toStr(variables.get("sportCode")));
        athleteRegisterTaskVo.setHomeAddress(Convert.toStr(variables.get("homeAddress")));
        athleteRegisterTaskVo.setFather(Convert.toStr(variables.get("father")));
        athleteRegisterTaskVo.setFatherMobile(Convert.toStr(variables.get("fatherMobile")));
        athleteRegisterTaskVo.setMother(Convert.toStr(variables.get("mother")));
        athleteRegisterTaskVo.setMotherMobile(Convert.toStr(variables.get("motherMobile")));
        athleteRegisterTaskVo.setProcessId(processId);
//        System.out.println("上面athleteRegisterTaskVo的内容是："+athleteRegisterTaskVo);
        /**
         * 上面athleteRegisterTaskVo的内容是：AthleteRegisterTaskVo(registerId=40288ae873a254840173a25484630000, registerDate=null, athleteName=六,
         * mobile=15170787476, nation=02, gender=1, idNo=562, coach=1207508909888151553, coachId=null, sportCode=a6d4a2ac214611ea914d39deaeccf25b,
         * homeAddress=赣州, father=吴某某, fatherMobile=15170787475, mother=曾某某, motherMobile=15170787475, processId=18f5fb9f-d2c7-11ea-8501-d6258b4e9751,
         * taskId=null, workflowStatus=null, taskStatus=null, workflowNode=null, approverId=null, approver=null, approvalDate=null, approvalResult=null,
         * approvalComment=null, approvalList=null)
         */



        // 获取当前节点
        List<Task> runtimeTasks = taskService.createTaskQuery().processInstanceId(processId).list();
        String workflowNode = "";
        for (int i = 0; i < runtimeTasks.size(); i++) {
            if (workflowNode.length() > 0) {
                workflowNode += ", ";
            }
            workflowNode += runtimeTasks.get(i).getName();
        }
        athleteRegisterTaskVo.setWorkflowNode(workflowNode);

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

//                 审批意见
//                Comment comment = taskService.getTaskComments(task.getId()).get(0);
//                String approvalComment = comment == null ? "" : Convert.toStr(comment.getFullMessage());

                AthleteRegisterHistoryVo athleteRegisterHistoryVo = new AthleteRegisterHistoryVo();
                athleteRegisterHistoryVo.setProcessId(processId);
                athleteRegisterHistoryVo.setTaskId(task.getId());
                athleteRegisterHistoryVo.setWorkflowNode(task.getName());
                athleteRegisterHistoryVo.setApproverId(task.getAssignee());
                athleteRegisterHistoryVo.setApprover(assigneeName);
//                athleteRegisterHistoryVo.setApprovalComment(approvalComment);
                athleteRegisterHistoryVo.setApprovalResult(approvalResult);
                athleteRegisterHistoryVo.setApprovalDate(task.getEndTime());
                athleteRegisterHistoryVoList.add(athleteRegisterHistoryVo);
            }
        });

        athleteRegisterTaskVo.setApprovalList(athleteRegisterHistoryVoList);
        return Result.ok(athleteRegisterTaskVo);

    }

    /**
     * 分页列表查询（我的待办任务/我的所有任务）
     *
     //     * @param athleteRegisterTaskVo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/taskList")
    public Result<?> queryMyHistoricTaskList(
            AthleteRegisterTaskVo athleteRegisterTaskVoQeuary,
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
                    candidateGroup.add("approvalCoach");
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
                .processDefinitionKey("athleteRegister")
                .or().taskAssignee(userId).taskCandidateUser(userId).taskCandidateGroupIn(candidateGroup)
                .endOr().orderByTaskCreateTime().desc().list();

        Set<String> processInstanceIds = new HashSet<String>();
        hisTasks.forEach(task -> {
            processInstanceIds.add(task.getProcessInstanceId());
        });

        Page<AthleteRegisterTaskVo> page = new Page<AthleteRegisterTaskVo>(pageNo, pageSize);
        Long hisPIsCount = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds)
                .orderByProcessInstanceStartTime().desc().count();
        page.setTotal(hisPIsCount);

        if (hisPIsCount > 0) {
            List<HistoricProcessInstance> hisPIs = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds)
                    .orderByProcessInstanceStartTime().desc().listPage(pageNo-1, pageSize);

            List<AthleteRegisterTaskVo> athleteRegisterTaskVoList = new ArrayList<AthleteRegisterTaskVo>();
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
                AthleteRegisterTaskVo athleteRegisterTaskVo = new AthleteRegisterTaskVo();
                athleteRegisterTaskVo.setAthleteName(Convert.toStr(variables.get("athleteName")));
                athleteRegisterTaskVo.setRegisterDate(Convert.toDate(variables.get("registerDate")));
                athleteRegisterTaskVo.setGender(Convert.toStr(variables.get("gender")));
                athleteRegisterTaskVo.setPassword(Convert.toStr(variables.get("password")));
                athleteRegisterTaskVo.setSportCode(Convert.toStr(variables.get("sportCode")));
                athleteRegisterTaskVo.setWorkflowStatus(Convert.toStr(variables.get("workflowStatus")));
                athleteRegisterTaskVo.setProcessId(hisPI.getId());
//                System.out.println("返回到前端的athleteRegisterTaskVo"+athleteRegisterTaskVo);

                // 获取用户当前任务
                Task userTask =
                        taskService.createTaskQuery().processInstanceId(hisPI.getId())
                                .or().taskAssignee(userId).taskCandidateUser(userId).taskCandidateGroupIn(candidateGroup).endOr()
                                .orderByTaskCreateTime().asc().singleResult();
                if (userTask != null) {
                    athleteRegisterTaskVo.setTaskId(userTask.getId());
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
                athleteRegisterTaskVo.setWorkflowNode(workflowNode);
//                System.out.println("返回到前端的athleteRegisterTaskVoList"+athleteRegisterTaskVoList);
                // 添加列表数据
                athleteRegisterTaskVoList.add(athleteRegisterTaskVo);

            });
            page.setRecords(athleteRegisterTaskVoList);
        }
        return Result.ok(page);
    }


    /**
     * 更新工作流（同意、驳回、转办、意见征询、回退）
     *
     */
    @PutMapping(value = "/update")
    public Result<?> update(@RequestBody AthleteRegisterTaskVo athleteRegisterTaskVo, HttpServletRequest req) {
        // 同意：agree，驳回：reject，转办：delegate，意见征询：consultation，回退：rollback。
//        System.out.println("athleteRegisterTaskVo11111的内容是:"+athleteRegisterTaskVo);
        // 获取用户信息
        String userName = JwtUtil.getUserNameByToken(req);
        SysUser sysUser = sysUserService.getUserByName(userName);
        if(sysUser == null) {
            return Result.error("Token无效!");
        }
        String userId = sysUser.getId();


        // 我的任务
        String taskId = Convert.toStr(athleteRegisterTaskVo.getTaskId(), "");
        String approvalResult = Convert.toStr(athleteRegisterTaskVo.getApprovalResult(), "");
        String approvalComment = Convert.toStr(athleteRegisterTaskVo.getApprovalComment(), "");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return Result.error("任务不存在！");
        }

        if ("agree".equals(approvalResult)) {
            taskService.setVariable(task.getId(), "approvalResult", "通过");
            taskService.setVariableLocal(task.getId(), "approvalResult", "通过");
            taskService.setVariableLocal(task.getId(), "assigneeName", sysUser.getRealname());
            taskService.addComment(task.getId(), task.getProcessInstanceId(), approvalComment);
            //这句表结束流程
            taskService.complete(task.getId());
            //流程结束的操作
            HistoryService historyService = processEngine.getHistoryService();
            //这里要换成当前的流程id，获取方式为athleteRegisterTaskVo.getProcessId()
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(athleteRegisterTaskVo.getProcessId()).singleResult();

            Date date = historicProcessInstance.getEndTime();
            if(date != null){//必须加判断，不然教练同意就会插入数据
                System.out.println("athleteRegisterTaskVo*****的内容是："+athleteRegisterTaskVo);
                Athlete athlete = athleteService.getOne(new QueryWrapper<Athlete>().eq("process_id",athleteRegisterTaskVo.getProcessId()));
                SysUser user = new SysUser();
                user.setCreateTime(new Date());// 设置创建时间
                String salt = oConvertUtils.randomGen(8);
                String passwordEncode = PasswordUtil.encrypt(athlete.getMobile(),athleteRegisterTaskVo.getPassword() , salt);
                user.setSalt(salt);
                user.setUsername(athlete.getMobile());
                user.setRealname(athleteRegisterTaskVo.getAthleteName());
                user.setPassword(passwordEncode);
                user.setStatus(1);
                user.setUserType(3);
                user.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
                user.setActivitiSync(CommonConstant.ACT_SYNC_1);
                System.out.println("user的内容是:"+user);
                sysUserService.addUserWithRole(user,"ee8626f80f7c2619917b6236f3a7f02b");
            }
        } else if ("reject".equals(approvalResult)) {
            taskService.setVariable(task.getId(), "approvalResult", "驳回");
            taskService.setVariableLocal(task.getId(), "approvalResult", "驳回");
            taskService.setVariableLocal(task.getId(), "assigneeName", sysUser.getRealname());
            taskService.addComment(task.getId(), task.getProcessInstanceId(), approvalComment);
            taskService.claim(task.getId(), userId);
            ManagementService managementService = processEngine.getManagementService();
            managementService.executeCommand(new DeleteOthersTaskCmd(task.getProcessInstanceId(), task.getId()));
            taskService.complete(task.getId());
            //先找到该运动员，再删除注册记录,这里athleteRegisterTaskVo包含的信息很少，唯一标识就是process_id
            Athlete athlete = athleteService.getOne(new QueryWrapper<Athlete>().eq("process_id",athleteRegisterTaskVo.getProcessId()));
            athleteService.removeById(athlete.getId());//tb_edu_athlete表内容是删了，但是前端表格页面的没删除

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
    @RequestMapping(value = "/processDiagram")
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


    /**
     * 通过身份证号码查询注册进度
     *
     * idNo
     */
    @GetMapping(value = "/queryRegister")
    public Serializable queryRegister(@RequestParam(name="idNo", required=true) String idNo){
        Athlete athlete = athleteService.getOne(new QueryWrapper<Athlete>().eq("id_no",idNo));//column的值要和数据库的表字段一样
        if(athlete  == null) {
            return Result.error("请注册账户");
        }
        List<Task> runtimeTasks = taskService.createTaskQuery().processInstanceId(athlete.getProcessId()).list();
        String workflowNode = "";
        for (int i = 0; i < runtimeTasks.size(); i++) {
            if (workflowNode.length() > 0) {
                workflowNode += ", ";
            }
            workflowNode += runtimeTasks.get(i).getName();
        }
        return Result.ok(workflowNode);
    }

}
