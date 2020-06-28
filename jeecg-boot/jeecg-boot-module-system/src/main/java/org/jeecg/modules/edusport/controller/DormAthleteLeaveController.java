package org.jeecg.modules.edusport.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.DormAthleteLeave;
import org.jeecg.modules.edusport.mapper.DormAthleteLeaveMapper;
import org.jeecg.modules.edusport.service.IDormAthleteLeaveService;
import org.jeecg.modules.edusport.util.UserUtil;
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

import lombok.extern.slf4j.Slf4j;
 /**
 * @Description: 宿舍运动员请假表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/dormAthleteLeave")
@Slf4j
public class DormAthleteLeaveController extends JeecgController<DormAthleteLeave, IDormAthleteLeaveService> {
	@Autowired
	private IDormAthleteLeaveService dormAthleteLeaveService;
	@Resource
	private DormAthleteLeaveMapper dormAthleteLeaveMapper;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ISysUserService sysUserService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dormAthleteLeave
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DormAthleteLeave dormAthleteLeave,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DormAthleteLeave> queryWrapper = QueryGenerator.initQueryWrapper(dormAthleteLeave, req.getParameterMap());
		Page<DormAthleteLeave> page = new Page<DormAthleteLeave>(pageNo, pageSize);
		queryWrapper.orderByDesc("start_date","dorm_id");
		IPage<DormAthleteLeave> pageList = dormAthleteLeaveService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dormAthleteLeave
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(HttpServletRequest request, @RequestBody DormAthleteLeave dormAthleteLeave) {
		dormAthleteLeave.setCreateTime(new Date());
		dormAthleteLeave.setCreateBy(UserUtil.getSystemUser(request, sysUserService).getUsername());
		dormAthleteLeave.setBillType("1");// 运动员宿舍请假单
		dormAthleteLeave.setWorkflowStatus("1");// 初始化.
		dormAthleteLeaveService.save(dormAthleteLeave);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dormAthleteLeave
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DormAthleteLeave dormAthleteLeave) {
		dormAthleteLeaveService.updateById(dormAthleteLeave);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *  更新状态
	 *
	 * @param dormAthleteLeave
	 * @return
	 */
	@PutMapping(value = "/submit")
	@Transactional
	public String submit(HttpServletRequest request, @RequestBody DormAthleteLeave dormAthleteLeave) {
		dormAthleteLeaveMapper.updateWorkflowState(dormAthleteLeave.getId());
		System.out.println("提交成功abcdefg");
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		SysUser user = new SysUser();
		  if(!oConvertUtils.isEmpty(token)) {
		   String username = JwtUtil.getUsername(token);
		   log.info(">>> username: ", username);
		   user = sysUserService.getUserByName(username);
		  }
		// 启动流程
		HashMap<String, Object> map = new HashMap<>();
		
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		map.put("taskUser", user.getId());
		map.put("leaveType", dormAthleteLeave.getBillType());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Leave", map);
		System.out.println( "提交成功.流程Id为：" + processInstance.getId());
		return "提交成功.流程Id为：" + processInstance.getId();
	}
	
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dormAthleteLeaveService.removeById(id);
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
		this.dormAthleteLeaveService.removeByIds(Arrays.asList(ids.split(",")));
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
		DormAthleteLeave dormAthleteLeave = dormAthleteLeaveService.getById(id);
		if(dormAthleteLeave==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dormAthleteLeave);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dormAthleteLeave
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DormAthleteLeave dormAthleteLeave) {
        return super.exportXls(request, dormAthleteLeave, DormAthleteLeave.class, "宿舍运动员请假表");
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
        return super.importExcel(request, response, DormAthleteLeave.class);
    }

}
