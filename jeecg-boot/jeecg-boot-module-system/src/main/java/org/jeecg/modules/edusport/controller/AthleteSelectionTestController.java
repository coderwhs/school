package org.jeecg.modules.edusport.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroup;
import org.jeecg.modules.edusport.entity.AthleteSelectionTest;
import org.jeecg.modules.edusport.entity.OutlineCoach;
import org.jeecg.modules.edusport.entity.Sport;
import org.jeecg.modules.edusport.mapper.AthleteMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionAthleteScoreDetailMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionAthleteScoreMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionGroupIndexMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionTestMapper;
import org.jeecg.modules.edusport.mapper.CoachMapper;
import org.jeecg.modules.edusport.mapper.OutlineCoachMapper;
import org.jeecg.modules.edusport.mapper.SportMapper;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreDetailService;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreService;
import org.jeecg.modules.edusport.service.IAthleteSelectionGroupService;
import org.jeecg.modules.edusport.service.IAthleteSelectionTestService;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.IOutlineCoachService;
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
 * @Description: 运动员选材测试表
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/athleteSelectionTest")
@Slf4j
public class AthleteSelectionTestController extends JeecgController<AthleteSelectionTest, IAthleteSelectionTestService> {
	@Autowired
	private IAthleteSelectionTestService athleteSelectionTestService;
	@Autowired
	private IAthleteService athleteService; // 运动员.
	@Autowired
	private IOutlineCoachService outlineCoachService; // 教练.
	@Autowired
	private IAthleteSelectionGroupService athleteSelectionGroupService; // 组 .
	@Autowired
	private IAthleteSelectionAthleteScoreService athleteSelectionAthleteScoreService;
	@Autowired
	private IAthleteSelectionAthleteScoreDetailService athleteSelectionAthleteScoreDetailService;
	@Resource
	private AthleteSelectionGroupIndexMapper athleteSelectionGroupIndexMapper;
	@Resource
	private AthleteSelectionTestMapper athleteSelectionTestMapper;
	@Resource
	private AthleteMapper athleteMapper;
	@Resource
	private CoachMapper coachMapper;
	@Resource
	private SportMapper sportMapper;
	@Resource
	private OutlineCoachMapper outlineCoachMapper;
	@Resource
	private AthleteSelectionAthleteScoreMapper athleteSelectionAthleteScoreMapper;
	@Resource
	private AthleteSelectionAthleteScoreDetailMapper athleteSelectionAthleteScoreDetailMapper;
	@Autowired
	private ISysUserService sysUserService;
	private static String enabled = "1";
	private static String disabled = "2";
	/**
	 * 分页列表查询
	 *
	 * @param athleteSelectionTest
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AthleteSelectionTest athleteSelectionTest,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AthleteSelectionTest> queryWrapper = QueryGenerator.initQueryWrapper(athleteSelectionTest, req.getParameterMap());
		Page<AthleteSelectionTest> page = new Page<AthleteSelectionTest>(pageNo, pageSize);
//		queryWrapper.orderByDesc("publish_date","test_name");
		IPage<AthleteSelectionTest> pageList = athleteSelectionTestService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	@GetMapping(value = "/navigation")
	public Result<?> navigation(HttpServletRequest req) {

		return Result.ok("");
	}
	
	/**
	 *   审核
	 *
	 * @param athleteSelectionTest
	 * @return
	 */
	@GetMapping(value = "/audit")
	public Result<?> audit(@RequestParam(name="id",required=true) String id)  {
		String resultInfo = "启用成功！";
		AthleteSelectionTest athleteSelectionTest = athleteSelectionTestService.getById(id);
		if(athleteSelectionTest != null) {
			if(enabled.equals(athleteSelectionTest.getBillState())){
				resultInfo = "大纲已经启用，请确认!";
			} else {
				athleteSelectionTest.setBillState(enabled); // 1：启用，2：禁用
				athleteSelectionTestService.updateById(athleteSelectionTest);
			}
		} else {
			resultInfo = "大纲信息不存在，请确认!";
		}

		return Result.ok(resultInfo);
	}
	
	/**
	 *   反审核
	 *
	 * @param athleteSelectionTest
	 * @return
	 */
	@GetMapping(value = "/unAudit")
	public Result<?> unAudit(@RequestParam(name="id",required=true) String id) {
		String resultInfo = "禁用成功！";
		AthleteSelectionTest athleteSelectionTest = athleteSelectionTestService.getById(id);
		if(athleteSelectionTest != null) {
			if(disabled.equals(athleteSelectionTest.getBillState())){
				resultInfo = "大纲已经禁用，请确认!";
			} else {
				athleteSelectionTest.setBillState(disabled); // 1：启用，2：禁用
				athleteSelectionTestService.updateById(athleteSelectionTest);
			}
		} else {
			resultInfo = "大纲信息不存在，请确认!";
		}

		return Result.ok(resultInfo);
	}
	
	/**
	 *   添加
	 *
	 * @param athleteSelectionTest
	 * @return
	 */
	@PostMapping(value = "/add")
	@Transactional
	public Result<?> add(HttpServletRequest request, @RequestBody AthleteSelectionTest athleteSelectionTest) {
		athleteSelectionTest.setBillState(enabled);// 启用.
		athleteSelectionTest.setCreateTime(new Date());
		athleteSelectionTest.setCreateBy(UserUtil.getSystemUser(request, sysUserService).getUsername());
		if(athleteSelectionTest.getIndexCodes() == null || "".equals(athleteSelectionTest.getIndexCodes())) {
			athleteSelectionTest.setIndexCodes(athleteSelectionGroupIndexMapper.getIndexByGroupId(athleteSelectionTest.getGroupId()).getIndexId());
		}
		athleteSelectionTestService.save(athleteSelectionTest);

		// 系统用户.
		SysUser sysUser = getSystemUser(request);
		saveCoachInfo(athleteSelectionTest, sysUser);

		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionTest
	 * @return
	 */
	@PutMapping(value = "/edit")
	@Transactional
	public Result<?> edit(HttpServletRequest request, @RequestBody AthleteSelectionTest athleteSelectionTest) {
		AthleteSelectionTest test = athleteSelectionTestService.getById(athleteSelectionTest.getId());
		if(test.getIndexCodes().isEmpty()) {
			test.setIndexCodes(athleteSelectionGroupIndexMapper.getIndexByGroupId(test.getGroupId()).getIndexId());
		}

		if(test != null) {
			if(disabled.equals(test.getBillState())) {// 1:启用，2:禁用
				athleteSelectionTestService.updateById(athleteSelectionTest);
				String testId = test.getId();
				outlineCoachMapper.deleteCoachByTestId(testId);
				athleteSelectionAthleteScoreMapper.deleteScoreByTestId(testId);
				athleteSelectionAthleteScoreDetailMapper.deleteScoreDetailByTestId(testId);
				
				// 系统用户.
				SysUser sysUser = getSystemUser(request);
				// 教练信息.
				saveCoachInfo(athleteSelectionTest, sysUser);
			} else {
				return Result.ok("启用状态，不允许编辑!");
			}
		} else {
			return Result.ok("大纲信息不存在，请确认!");
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
	@Transactional
	public Result<?> delete(HttpServletRequest request, @RequestParam(name="id",required=true) String id) {
		AthleteSelectionTest athleteSelectionTest = athleteSelectionTestService.getById(id);
		if(athleteSelectionTest != null) {
			if(disabled.equals(athleteSelectionTest.getBillState())) {// 1:启用，2:禁用
				athleteSelectionTestService.removeById(id);
				String testId = athleteSelectionTest.getId();
				outlineCoachMapper.deleteCoachByTestId(testId);
				athleteSelectionAthleteScoreMapper.deleteScoreByTestId(testId);
				athleteSelectionAthleteScoreDetailMapper.deleteScoreDetailByTestId(testId);
			} else {
				return Result.ok("启用状态，不允许删除!");
			}
		}

		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	@Transactional
	public Result<?> deleteBatch(HttpServletRequest request, @RequestParam(name="ids",required=true) String ids) {
		String[] testIds = ids.split(",");
		for(int i = 0; i < testIds.length; i++) {
			AthleteSelectionTest athleteSelectionTest = athleteSelectionTestService.getById(testIds[i]);
			if(athleteSelectionTest != null) {
				if(enabled.equals(athleteSelectionTest.getBillState())) {// 1:启用，2:禁用
					return Result.ok("启用状态，不允许删除!");
				}
			}
		}

		for(int i = 0; i < testIds.length; i++) {
			AthleteSelectionTest athleteSelectionTest = athleteSelectionTestService.getById(testIds[i]);
			String testId = athleteSelectionTest.getId();
			outlineCoachMapper.deleteCoachByTestId(testId);
			athleteSelectionAthleteScoreMapper.deleteScoreByTestId(testId);
			athleteSelectionAthleteScoreDetailMapper.deleteScoreDetailByTestId(testId);
		}
		this.athleteSelectionTestService.removeByIds(Arrays.asList(ids.split(",")));
		
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
		AthleteSelectionTest athleteSelectionTest = athleteSelectionTestService.getById(id);
		if(athleteSelectionTest==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(athleteSelectionTest);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param athleteSelectionTest
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AthleteSelectionTest athleteSelectionTest) {
        return super.exportXls(request, athleteSelectionTest, AthleteSelectionTest.class, "运动员选材测试表");
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
        return super.importExcel(request, response, AthleteSelectionTest.class);
    }

	
	/**
	 * 保存教练信息.
	 * @param athleteSelectionTest
	 * @param sysUser
	 */
	private void saveCoachInfo(AthleteSelectionTest athleteSelectionTest, SysUser sysUser) {
		String groupId = athleteSelectionTest.getGroupId();
		// 取得组信息.
		AthleteSelectionGroup athleteSelectionGroup = athleteSelectionGroupService.getById(groupId);

		Sport sport = sportMapper.getSportByCode(athleteSelectionTest.getSportCode());
		// 教练信息处理.
		// 取得所有选择的学生.
		List<OutlineCoach> outlineCoachList = new ArrayList<OutlineCoach>();
		@SuppressWarnings("rawtypes")
		List<HashMap> coachList = coachMapper.getCoachByAthleteId(athleteSelectionTest.getAthleteNos().split(","));
		for(int i = 0; i < coachList.size(); i++) {
			HashMap<?, ?> map = coachList.get(i);
			OutlineCoach outlineCoach = new OutlineCoach();
			if(map.get("athlete") != null && !"".equals(map.get("athlete").toString())) {
				outlineCoach.setAlthleteNos(map.get("athlete").toString());// 运动员.
			}
			if(map.get("id") != null && !"".equals(map.get("id").toString())) {
				outlineCoach.setCoachId(map.get("id").toString());// 教练.
			}

			outlineCoach.setEventCodes(athleteSelectionGroup.getEventCodes());// 运动项目.
			outlineCoach.setGroupId(groupId);// 组.
			outlineCoach.setOutlineId(athleteSelectionTest.getId());// 大纲ID.
			if(sport != null) {
				outlineCoach.setSportId(sport.getId());// 运动项目.
			}
			if(athleteSelectionTest.getIndexCodes() != null && !"".equals(athleteSelectionTest.getIndexCodes())) {
				outlineCoach.setIndexCodes(athleteSelectionTest.getIndexCodes());// 指标信息
			} else {
				outlineCoach.setIndexCodes(athleteSelectionGroupIndexMapper.getIndexByGroupId(groupId).getIndexId());
			}
			outlineCoach.setTestDate(athleteSelectionTest.getPublishDate());// 测试日期
			outlineCoach.setState(disabled);// 未状态
			outlineCoach.setCreateTime(Calendar.getInstance().getTime());
			outlineCoach.setCreateBy(sysUser.getUsername());
			outlineCoach.setUpdateTime(Calendar.getInstance().getTime());
			outlineCoach.setUpdateBy(sysUser.getUsername());
			outlineCoachList.add(outlineCoach);
		}
		outlineCoachService.saveBatch(outlineCoachList);// 保存教练表信息.
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
//			System.out.println(">>> username: " + username);
			user = sysUserService.getUserByName(username);
		}

		return user;
	}
}
