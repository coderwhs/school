package org.jeecg.modules.edusport.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScore;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScoreDetail;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroup;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupIndex;
import org.jeecg.modules.edusport.entity.AthleteSelectionTest;
import org.jeecg.modules.edusport.entity.OutlineCoach;
import org.jeecg.modules.edusport.mapper.AthleteMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionAthleteScoreMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionGroupIndexMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionTestMapper;
import org.jeecg.modules.edusport.mapper.CoachMapper;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreDetailService;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreService;
import org.jeecg.modules.edusport.service.IAthleteSelectionGroupService;
import org.jeecg.modules.edusport.service.IAthleteSelectionTestService;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.IOutlineCoachService;
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
	private AthleteSelectionAthleteScoreMapper athleteSelectionAthleteScoreMapper;
	@Autowired
	private ISysUserService sysUserService;
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
	
	/**
	 *   添加
	 *
	 * @param athleteSelectionTest
	 * @return
	 */
	@PostMapping(value = "/add")
	@Transactional
	public Result<?> add(HttpServletRequest request, @RequestBody AthleteSelectionTest athleteSelectionTest) {
		athleteSelectionTestService.save(athleteSelectionTest);

		String groupId = athleteSelectionTest.getGroupId();
		// 取得组信息.
		AthleteSelectionGroup athleteSelectionGroup = athleteSelectionGroupService.getById(groupId);
		
		// 系统用户.
		SysUser sysUser = getSystemUser(request);
		
		// 教练信息处理.
		// 取得所有选择的学生.
		List<OutlineCoach> outlineCoachList = new ArrayList<OutlineCoach>();
		@SuppressWarnings("rawtypes")
		List<HashMap> coachList = coachMapper.getCoachByAthleteId(athleteSelectionTest.getAthleteNos().split(","));
		for(int i = 0; i < coachList.size(); i++) {
			HashMap<?, ?> map = coachList.get(i);
			OutlineCoach outlineCoach = new OutlineCoach();
			outlineCoach.setAlthleteNos(map.get("athlete").toString());// 运动员.
			outlineCoach.setCoachId(map.get("id").toString());// 教练.
			outlineCoach.setEventCodes(athleteSelectionGroup.getEventCodes());// 运动项目.
			outlineCoach.setGroupId(groupId);// 组.
			outlineCoach.setOutlineId(athleteSelectionTest.getId());// 大纲ID.
			outlineCoach.setSportId(athleteSelectionTest.getSportCode());// 运动项目.
			outlineCoach.setTestDate(athleteSelectionTest.getPublishDate());// 发布日期
			outlineCoach.setState("1");// 状态
			outlineCoach.setCreateTime(Calendar.getInstance().getTime());
			outlineCoach.setCreateBy(sysUser.getUsername());
			outlineCoach.setUpdateTime(Calendar.getInstance().getTime());
			outlineCoach.setUpdateBy(sysUser.getUsername());
			outlineCoachList.add(outlineCoach);
		}
		outlineCoachService.saveBatch(outlineCoachList);// 保存教练表信息.
		
		// 运动项目信息.
		String[] sportCode = athleteSelectionGroup.getEventCodes().split(",");
		for(int j = 0; j < sportCode.length; j++){//运动项目信息.
			// 取得所有选择的学生.
			String[] athleteNo = athleteSelectionTest.getAthleteNos().split(",");
			for(int k = 0; k < athleteNo.length; k++) {// 查询所有运动员信息.
				Athlete athlete = athleteMapper.getAthleteByNo(athleteNo[k]);
				// 学生信息：教练、年级、出生年月、性别.
				AthleteSelectionAthleteScore athleteSelectionAthleteScore = new AthleteSelectionAthleteScore();
				athleteSelectionAthleteScore.setAthleteId(athlete.getId());// 运动员.
				athleteSelectionAthleteScore.setBirthday(athlete.getBirthDate());// 出生日期.
				athleteSelectionAthleteScore.setGender(athlete.getGender());// 性别.
				athleteSelectionAthleteScore.setGrade(athlete.getGrade());// 年级
				athleteSelectionAthleteScore.setCoachId(athlete.getMajorSportTeacherCode());// 教练员
				athleteSelectionAthleteScore.setGroupId(groupId);// 组
				athleteSelectionAthleteScore.setTestId(athleteSelectionTest.getId());// 大纲ID
				athleteSelectionAthleteScore.setEventCode(athleteSelectionGroup.getEventCodes());// 运动项目
				athleteSelectionAthleteScore.setTestScore(Integer.valueOf(0));// 成绩
				athleteSelectionAthleteScore.setTestGrade("");// 测试等级.
				athleteSelectionAthleteScore.setAuditState("1");// 状态
				athleteSelectionAthleteScore.setCreateTime(Calendar.getInstance().getTime());
				athleteSelectionAthleteScore.setCreateBy(sysUser.getUsername());
				athleteSelectionAthleteScore.setUpdateTime(Calendar.getInstance().getTime());
				athleteSelectionAthleteScore.setUpdateBy(sysUser.getUsername());
				athleteSelectionAthleteScoreService.save(athleteSelectionAthleteScore);

				// 指标信息.
				AthleteSelectionGroupIndex athleteSelectionGroupIndex = athleteSelectionGroupIndexMapper.getIndexByGroupId(groupId);
				
				List<AthleteSelectionAthleteScoreDetail> athleteScoreDetailList = new ArrayList<AthleteSelectionAthleteScoreDetail>();
				String[] indexids = athleteSelectionGroupIndex.getIndexId().split(",");
				for(int i = 0; i < indexids.length; i++){//指标信息.
					AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail = new AthleteSelectionAthleteScoreDetail();
					athleteSelectionAthleteScoreDetail.setAthleteScoreId(athleteSelectionAthleteScore.getId());// 成绩ID
					athleteSelectionAthleteScoreDetail.setAthleteId(athlete.getId());// 运动员.
					athleteSelectionAthleteScoreDetail.setTestId(athleteSelectionTest.getId());// 大纲ID
					athleteSelectionAthleteScoreDetail.setGroupId(groupId);// 组
					athleteSelectionAthleteScoreDetail.setIndexCode(indexids[i]);// 指标
					athleteSelectionAthleteScoreDetail.setEventCode(sportCode[j]);// 运动项目
					athleteSelectionAthleteScoreDetail.setTestValue("");// 测试值
					athleteSelectionAthleteScoreDetail.setTestScore(Integer.valueOf(0));// 测试分数
					athleteSelectionAthleteScoreDetail.setCreateTime(Calendar.getInstance().getTime());
					athleteSelectionAthleteScoreDetail.setCreateBy(sysUser.getUsername());
					athleteSelectionAthleteScoreDetail.setUpdateTime(Calendar.getInstance().getTime());
					athleteSelectionAthleteScoreDetail.setUpdateBy(sysUser.getUsername());
					athleteScoreDetailList.add(athleteSelectionAthleteScoreDetail);
				}
				athleteSelectionAthleteScoreDetailService.saveBatch(athleteScoreDetailList);
			}
		}

		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionTest
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AthleteSelectionTest athleteSelectionTest) {
		athleteSelectionTestService.updateById(athleteSelectionTest);
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
		athleteSelectionTestService.removeById(id);
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
