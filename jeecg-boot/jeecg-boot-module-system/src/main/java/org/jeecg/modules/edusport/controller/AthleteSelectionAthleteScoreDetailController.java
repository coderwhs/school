package org.jeecg.modules.edusport.controller;

import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScoreDetail;
import org.jeecg.modules.edusport.mapper.AthleteMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionGroupIndexGradeMapper;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreDetailService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: 运动员选材测试成绩明细表
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/athleteSelectionAthleteScoreDetail")
@Slf4j
public class AthleteSelectionAthleteScoreDetailController extends JeecgController<AthleteSelectionAthleteScoreDetail, IAthleteSelectionAthleteScoreDetailService> {
	@Autowired
	private IAthleteSelectionAthleteScoreDetailService athleteSelectionAthleteScoreDetailService;
	@Resource
	private AthleteMapper athleteMapper;
	@Resource
	private AthleteSelectionGroupIndexGradeMapper athleteSelectionGroupIndexGradeMapper;
	/**
	 * 分页列表查询
	 *
	 * @param athleteSelectionAthleteScoreDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AthleteSelectionAthleteScoreDetail> queryWrapper = QueryGenerator.initQueryWrapper(athleteSelectionAthleteScoreDetail, req.getParameterMap());
		Page<AthleteSelectionAthleteScoreDetail> page = new Page<AthleteSelectionAthleteScoreDetail>(pageNo, pageSize);
		IPage<AthleteSelectionAthleteScoreDetail> pageList = athleteSelectionAthleteScoreDetailService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param athleteSelectionAthleteScoreDetail
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail) {
		athleteSelectionAthleteScoreDetailService.save(athleteSelectionAthleteScoreDetail);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionAthleteScoreDetail
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail) {
		athleteSelectionAthleteScoreDetailService.updateById(athleteSelectionAthleteScoreDetail);
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
		athleteSelectionAthleteScoreDetailService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *   计算考核成绩信息.
	 *
	 * @param athleteSelectionAthleteScore
	 * @return
	 */
	@GetMapping(value = "/calcScore")
	@Transactional
	@ResponseBody
	public Result<?> audit(@RequestParam(name = "athleteId", required = true) String athleteId,
			@RequestParam(name = "groupId", required = true) String groupId,
			@RequestParam(name = "indexCode", required = true) String indexCode,
			@RequestParam(name = "testValue", required = true) String testValue) {
		HashMap athleteMap = (HashMap) athleteMapper.getAthleteAgeById(athleteId);// 运动员信息.
		
		System.out.println("============" + athleteMap.get("age"));
		
/*		Integer score = athleteSelectionGroupIndexGradeMapper.getAthleteScoreByTestValue(indexId, groupId,
				athleteMap.get("gender"), Integer.valueOf(athleteMap.get("age")), testValue);*/
		Integer score = athleteSelectionGroupIndexGradeMapper.getAthleteScoreByTestValue(indexCode, groupId,
				"1", Integer.valueOf("1"), Integer.valueOf("1"));
		Result result = new Result();
		AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail = new AthleteSelectionAthleteScoreDetail();
		athleteSelectionAthleteScoreDetail.setTestScore(Integer.valueOf("10"));
		JSONObject jsonObject = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
		return Result.ok(jsonObject);
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.athleteSelectionAthleteScoreDetailService.removeByIds(Arrays.asList(ids.split(",")));
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
		AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail = athleteSelectionAthleteScoreDetailService.getById(id);
		if(athleteSelectionAthleteScoreDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(athleteSelectionAthleteScoreDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param athleteSelectionAthleteScoreDetail
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail) {
        return super.exportXls(request, athleteSelectionAthleteScoreDetail, AthleteSelectionAthleteScoreDetail.class, "运动员选材测试成绩明细表");
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
        return super.importExcel(request, response, AthleteSelectionAthleteScoreDetail.class);
    }

}
