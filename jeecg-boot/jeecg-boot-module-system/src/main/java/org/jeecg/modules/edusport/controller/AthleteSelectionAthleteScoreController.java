package org.jeecg.modules.edusport.controller;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScore;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupRating;
import org.jeecg.modules.edusport.mapper.AthleteSelectionAthleteScoreDetailMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionAthleteScoreMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionGroupRatingMapper;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreDetailService;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreService;
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
 * @Description: 运动员选材测试成绩表
 * @Author: jeecg-boot
 * @Date:   2019-12-19
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/athleteSelectionAthleteScore")
@Slf4j
public class AthleteSelectionAthleteScoreController extends JeecgController<AthleteSelectionAthleteScore, IAthleteSelectionAthleteScoreService> {
	@Autowired
	private IAthleteSelectionAthleteScoreService athleteSelectionAthleteScoreService;
	@Autowired
	private IAthleteSelectionAthleteScoreDetailService iAthleteSelectionAthleteScoreDetailService;
	@Resource
	private AthleteSelectionAthleteScoreMapper athleteSelectionAthleteScoreMapper;
	@Resource
	private AthleteSelectionAthleteScoreDetailMapper athleteSelectionAthleteScoreDetailMapper;
	@Resource
	private AthleteSelectionGroupRatingMapper athleteSelectionGroupRatingMapper;
	/**
	 * 分页列表查询
	 *
	 * @param athleteSelectionAthleteScore
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AthleteSelectionAthleteScore athleteSelectionAthleteScore,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AthleteSelectionAthleteScore> queryWrapper = QueryGenerator.initQueryWrapper(athleteSelectionAthleteScore, req.getParameterMap());
		Page<AthleteSelectionAthleteScore> page = new Page<AthleteSelectionAthleteScore>(pageNo, pageSize);
		queryWrapper.orderByDesc("test_id");
		IPage<AthleteSelectionAthleteScore> pageList = athleteSelectionAthleteScoreService.page(page, queryWrapper);

		return Result.ok(pageList);
	}
	
	/**
	 *   引入运动员信息.
	 *
	 * @param athleteSelectionAthleteScore
	 * @return
	 */
	@GetMapping(value = "/importAthlete")
	//@RequiresPermissions("user:add")
	public Result<?> importAthlete(@RequestParam(name="groupId",required=true) String groupId) {
		//athleteSelectionAthleteScoreService.save(athleteSelectionAthleteScore);
		
		return Result.ok("引入运动员成功！"+ groupId);
	}

	/**
	 *   计算运动员成绩信息.
	 *
	 * @param athleteSelectionAthleteScore
	 * @return
	 */
	@GetMapping(value = "/calculateScore")
	@Transactional
	public Result<?> calculateScore(@RequestParam(name="id",required=true) String id) {
		AthleteSelectionAthleteScore athleteSelectionAthleteScore = athleteSelectionAthleteScoreService.getById(id);
		String resultInfo = "计算运动员成绩成功!";
		if(athleteSelectionAthleteScore != null) {
			if("2".equals(athleteSelectionAthleteScore.getAuditState())) {// 1：初始化，2：审核
				resultInfo = "运动员信息已经审核，不能再次计算，请确认!";
			} else {
				// 根据运动员取得成绩总分.
				Integer testScore = athleteSelectionAthleteScoreDetailMapper.getAthleteScoreById(id);
				// 根据分数判断相应等级.
				AthleteSelectionGroupRating groupRating = athleteSelectionGroupRatingMapper.getAthleteScoreRatingByScore(testScore);
				if(groupRating != null && groupRating.getId() != null) {
					athleteSelectionAthleteScore.setTestScore(testScore);
					athleteSelectionAthleteScore.setTestGrade(groupRating.getId());
					
					athleteSelectionAthleteScoreService.updateById(athleteSelectionAthleteScore);
				} else {
					resultInfo = "没有设置相应的评分等级!";
				}
			}
		} else {
			resultInfo = "运动员信息不存在，请确认!";
		}

		return Result.ok(resultInfo);
	}
	
	/**
	 *   审核成绩信息.
	 *
	 * @param athleteSelectionAthleteScore
	 * @return
	 */
	@GetMapping(value = "/audit")
	@Transactional
	public Result<?> audit(@RequestParam(name="id",required=true) String id) {
		String resultInfo = "审核成绩成功！";
		AthleteSelectionAthleteScore athleteSelectionAthleteScore = athleteSelectionAthleteScoreService.getById(id);
		if(athleteSelectionAthleteScore != null) {
			if("2".equals(athleteSelectionAthleteScore.getAuditState())){
				resultInfo = "运动员成绩已经审核，请确认!";
			} else {
				athleteSelectionAthleteScore.setAuditState("2");// 审核// 1：初始化，2：审核
				athleteSelectionAthleteScoreService.updateById(athleteSelectionAthleteScore);
			}
		} else {
			resultInfo = "运动员信息不存，请确认!";
		}

		return Result.ok(resultInfo);
	}
	
	/**
	 *   添加
	 *
	 * @param athleteSelectionAthleteScore
	 * @return
	 */
	@PostMapping(value = "/add")
	@Transactional
	public Result<?> add(@RequestBody AthleteSelectionAthleteScore athleteSelectionAthleteScore) {
		
		
		athleteSelectionAthleteScoreService.save(athleteSelectionAthleteScore);
		
		
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionAthleteScore
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AthleteSelectionAthleteScore athleteSelectionAthleteScore) {
		athleteSelectionAthleteScoreService.updateById(athleteSelectionAthleteScore);
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
		athleteSelectionAthleteScoreService.removeById(id);
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
		this.athleteSelectionAthleteScoreService.removeByIds(Arrays.asList(ids.split(",")));
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
		AthleteSelectionAthleteScore athleteSelectionAthleteScore = athleteSelectionAthleteScoreService.getById(id);
		if(athleteSelectionAthleteScore==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(athleteSelectionAthleteScore);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param athleteSelectionAthleteScore
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AthleteSelectionAthleteScore athleteSelectionAthleteScore) {
        return super.exportXls(request, athleteSelectionAthleteScore, AthleteSelectionAthleteScore.class, "运动员选材测试成绩表");
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
        return super.importExcel(request, response, AthleteSelectionAthleteScore.class);
    }

}
