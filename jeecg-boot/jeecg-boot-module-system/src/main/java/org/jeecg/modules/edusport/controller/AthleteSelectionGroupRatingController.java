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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupRating;
import org.jeecg.modules.edusport.service.IAthleteSelectionGroupRatingService;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 运动员选材测试等级评定标准表
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/athleteSelectionGroupRating")
@Slf4j
public class AthleteSelectionGroupRatingController extends JeecgController<AthleteSelectionGroupRating, IAthleteSelectionGroupRatingService> {
	@Autowired
	private IAthleteSelectionGroupRatingService athleteSelectionGroupRatingService;
	
	/**
	 * 分页列表查询
	 *
	 * @param athleteSelectionGroupRating
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AthleteSelectionGroupRating athleteSelectionGroupRating,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AthleteSelectionGroupRating> queryWrapper = QueryGenerator.initQueryWrapper(athleteSelectionGroupRating, req.getParameterMap());
		Page<AthleteSelectionGroupRating> page = new Page<AthleteSelectionGroupRating>(pageNo, pageSize);
		queryWrapper.orderByAsc("group_id");
		IPage<AthleteSelectionGroupRating> pageList = athleteSelectionGroupRatingService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param athleteSelectionGroupRating
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AthleteSelectionGroupRating athleteSelectionGroupRating) {
		athleteSelectionGroupRatingService.save(athleteSelectionGroupRating);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionGroupRating
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AthleteSelectionGroupRating athleteSelectionGroupRating) {
		athleteSelectionGroupRatingService.updateById(athleteSelectionGroupRating);
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
		athleteSelectionGroupRatingService.removeById(id);
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
		this.athleteSelectionGroupRatingService.removeByIds(Arrays.asList(ids.split(",")));
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
		AthleteSelectionGroupRating athleteSelectionGroupRating = athleteSelectionGroupRatingService.getById(id);
		if(athleteSelectionGroupRating==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(athleteSelectionGroupRating);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param athleteSelectionGroupRating
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AthleteSelectionGroupRating athleteSelectionGroupRating) {
        return super.exportXls(request, athleteSelectionGroupRating, AthleteSelectionGroupRating.class, "运动员选材测试等级评定标准表");
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
        return super.importExcel(request, response, AthleteSelectionGroupRating.class);
    }

}
