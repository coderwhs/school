package org.jeecg.modules.edusport.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScore;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreDetailService;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

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
	public Result<?> importAthlete(@RequestBody AthleteSelectionAthleteScore athleteSelectionAthleteScore) {
				System.out.println("============================" + athleteSelectionAthleteScore.getGroupId());
		//athleteSelectionAthleteScoreService.save(athleteSelectionAthleteScore);
		
		return Result.ok("引入运动员成功！"+ athleteSelectionAthleteScore.getGroupId());
	}
	
	/**
	 *   引入运动员信息.
	 *
	 * @param athleteSelectionAthleteScore
	 * @return
	 */
	@GetMapping(value = "/importAthleteB")
	//@RequiresPermissions("user:add")
	public Result<?> importAthlete(@RequestParam(name="groupId",required=true) String groupId) {
				System.out.println("============================" + groupId);
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
//	@RequiresPermissions("athlete:calculateScore")
	public Result<?> calculateScore(@RequestParam(name="groupId",required=true) String groupId) {
		System.out.println("=============计算运动员成绩信息===============" + groupId);
		return Result.ok("计算运动员成绩成功！");
	}
	
	/**
	 *   审核成绩信息.
	 *
	 * @param athleteSelectionAthleteScore
	 * @return
	 */
	@PostMapping(value = "/audit")
	@RequiresPermissions("athlete:audit")
	public Result<?> audit(@RequestBody AthleteSelectionAthleteScore athleteSelectionAthleteScore) {
		
		return Result.ok("审核成绩成功！");
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
