package org.jeecg.modules.edusport.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.CoachLeave;
import org.jeecg.modules.edusport.mapper.CoachLeaveMapper;
import org.jeecg.modules.edusport.service.ICoachLeaveService;
import org.jeecg.modules.edusport.util.UserUtil;
import org.jeecg.modules.shiro.vo.DefContants;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * @Description: 教练员请假表
 * @Author: whs
 * @Date:
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/coachLeave")
@Slf4j
public class CoachLeaveController extends JeecgController<CoachLeave, ICoachLeaveService> {
    @Autowired
    private ICoachLeaveService coachLeaveService;
    @Resource
    private CoachLeaveMapper coachLeaveMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 分页列表查询
     *
     * @param coachLeave
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */

    @GetMapping(value = "/list")
    public Result<?> queryPageList(CoachLeave coachLeave,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<CoachLeave> queryWrapper = QueryGenerator.initQueryWrapper(coachLeave, req.getParameterMap());
        Page<CoachLeave> page = new Page<CoachLeave>(pageNo, pageSize);
        queryWrapper.orderByDesc("start_date","dorm_id");
        IPage<CoachLeave> pageList = coachLeaveService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     *   添加
     *
     * @param coachLeave
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(HttpServletRequest request, @RequestBody CoachLeave coachLeave) {
        coachLeave.setCreateTime(new Date());
        coachLeave.setCreateBy(UserUtil.getSystemUser(request, sysUserService).getUsername());
        coachLeave.setBillType("1");// 教练请假单
        coachLeave.setWorkflowStatus("1");// 初始化.
        coachLeaveService.save(coachLeave);
        return Result.ok("添加成功！");
    }

    /**
     *  编辑
     *
     * @param coachLeave
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody CoachLeave coachLeave) {
        coachLeaveService.updateById(coachLeave);
        return Result.ok("编辑成功!");
    }

    /**
     *  更新状态
     *
     * @param coachLeave
     * @return
     */
    @PutMapping(value = "/submit")
    @Transactional
    public String submit(HttpServletRequest request, @RequestBody CoachLeave coachLeave) {
        coachLeaveMapper.updateWorkflowState(coachLeave.getId());
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

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        map.put("taskUser", user.getId());
        map.put("leaveType", coachLeave.getBillType());
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
        coachLeaveService.removeById(id);
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
        this.coachLeaveService.removeByIds(Arrays.asList(ids.split(",")));
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
        CoachLeave coachLeave = coachLeaveService.getById(id);
        if(coachLeave==null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(coachLeave);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param coachLeave
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoachLeave coachLeave) {
        return super.exportXls(request, coachLeave, CoachLeave.class, "教练请假表");
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
        return super.importExcel(request, response, CoachLeave.class);
    }
}
