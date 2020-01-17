package org.jeecg.modules.edusport.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.entity.TactRuTask;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.IDormAthleteLeaveService;
import org.jeecg.modules.edusport.service.ITactRuTaskService;
import org.jeecg.modules.shiro.vo.DefContants;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: tact_ru_task
 * @Author: jeecg-boot
 * @Date:   2019-12-16
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/tactRuTask")
@Slf4j
public class TactRuTaskController extends JeecgController<TactRuTask, ITactRuTaskService> {
	@Autowired
	private ITactRuTaskService tactRuTaskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private IAthleteService athleteService;
	@Autowired
	private IDormAthleteLeaveService dormAthleteLeaveService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tactRuTask
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(HttpServletRequest request, TactRuTask tactRuTask,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TactRuTask> queryWrapper = QueryGenerator.initQueryWrapper(tactRuTask, req.getParameterMap());
		Page<TactRuTask> page = new Page<TactRuTask>(pageNo, pageSize);
		IPage<TactRuTask> pageList = tactRuTaskService.page(page, queryWrapper);
//		String userId = "教练";
		String userId = getSystemUser(request).getUsername();
		
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		SysUser user = new SysUser();
		  if(!oConvertUtils.isEmpty(token)) {
		   String username = JwtUtil.getUsername(token);
		   System.out.println(">>> userName: " + username);
		   user = sysUserService.getUserByName(username);
		   System.out.println(">>> userId: " + user.getId());
		  }
		  List<Task> tasks = Lists.newArrayList();
		  if("admin".equals(userId)) {
			  tasks = taskService.createTaskQuery().ignoreAssigneeValue().orderByTaskCreateTime().desc().list();
		  } else {
			  tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
		  }
		  
		List<TactRuTask> rs = Lists.newArrayList();
		for (Task task :tasks) {
			TactRuTask t = new TactRuTask();
			t = tactRuTaskService.getById(task.getId());
			if(t == null) {
				t = new TactRuTask();
			}
			t.setId(task.getId());// 任务ID.
			t.setProcDefId(task.getProcessDefinitionId());
			t.setProcInstId(task.getProcessInstanceId());
	        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
	        if ("Leave".equals(processInstance.getProcessDefinitionKey())) {
	        	DormAthleteLeave dormAthleteLeave = dormAthleteLeaveService.getById(processInstance.getBusinessKey());
//	        	System.out.println(athleteService.getById(dormAthleteLeave.getAthleteId()).getAthleteName());
//	        	System.out.println("单据ID：" + processInstance.getBusinessKey());
	        	
		        // 申请理由.
	        	t.setDelegation(dormAthleteLeave.getLeaveCause());
		        // 任务描述.
		        t.setOwner("【任务】请审批【" + athleteService.getById(dormAthleteLeave.getAthleteId()).getAthleteName() + "】的请假申请！" + "【请假原因】：" + dormAthleteLeave.getLeaveCause());
		        // 审批意见.
//		        t.setDescription("");
		        // 运动员ID.
		        t.setScopeId(dormAthleteLeave.getAthleteId());// 运动员ID.
		        t.setCreateBy(dormAthleteLeave.getUpdateBy());
		        t.setCreateTime(dormAthleteLeave.getUpdateTime());
	        }
	        // 流程名称.
	        if("Leave".equals(processInstance.getProcessDefinitionKey())) {
	        	t.setCategory("请假申请");
	        } else {
	        	t.setCategory("考勤请假");
	        }
//	        System.out.println("审批意见：" + t.getDescription());
			t.setName(task.getName());
			t.setAssignee(task.getAssignee());
			t.setParentTaskId(processInstance.getBusinessKey());// 单据ID.

			rs.add(t);
			tactRuTaskService.saveOrUpdate(t);
		}
		pageList.setRecords(rs);
		
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(pageList);
		
		
        return r;
//		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tactRuTask
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TactRuTask tactRuTask) {
		tactRuTaskService.save(tactRuTask);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tactRuTask
	 * @return
	 */
	@PutMapping(value = "/edit")
	@Transactional
	public Result<?> edit(HttpServletRequest request, @RequestBody TactRuTask tactRuTask) {
		tactRuTask.setUpdateBy(getSystemUser(request).getUsername());
		tactRuTask.setUpdateTime(new Date());
		tactRuTaskService.updateById(tactRuTask);
		
		if("1".equals(tactRuTask.getIsAgree())) {// 1:同意，2:不同意.
			
		} else {
			
		}
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		tactRuTaskService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tactRuTaskService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TactRuTask tactRuTask = tactRuTaskService.getById(id);
		if(tactRuTask==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tactRuTask);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tactRuTask
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TactRuTask tactRuTask) {
        return super.exportXls(request, tactRuTask, TactRuTask.class, "tact_ru_task");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TactRuTask.class);
    }
    
    /**
	 * 取得当前登录用户.
	 * 
	 * @param request
	 * @return
	 */
	private SysUser getSystemUser(HttpServletRequest request) {
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		SysUser user = new SysUser();
		if (!oConvertUtils.isEmpty(token)) {
			String username = JwtUtil.getUsername(token);
			System.out.println(">>> username: " + username);
			user = sysUserService.getUserByName(username);
		}

		return user;
	}

}
