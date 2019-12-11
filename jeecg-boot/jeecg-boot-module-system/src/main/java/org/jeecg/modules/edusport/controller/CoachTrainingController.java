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
import org.jeecg.modules.edusport.entity.CoachTraining;
import org.jeecg.modules.edusport.service.ICoachTrainingService;

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
 * @Description: 教练员学习培训信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/coachTraining")
@Slf4j
public class CoachTrainingController extends JeecgController<CoachTraining, ICoachTrainingService> {
	@Autowired
	private ICoachTrainingService coachTrainingService;
	
	/**
	 * 分页列表查询
	 *
	 * @param coachTraining
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CoachTraining coachTraining,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CoachTraining> queryWrapper = QueryGenerator.initQueryWrapper(coachTraining, req.getParameterMap());
		Page<CoachTraining> page = new Page<CoachTraining>(pageNo, pageSize);
		IPage<CoachTraining> pageList = coachTrainingService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param coachTraining
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoachTraining coachTraining) {
		coachTrainingService.save(coachTraining);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param coachTraining
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoachTraining coachTraining) {
		coachTrainingService.updateById(coachTraining);
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
		coachTrainingService.removeById(id);
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
		this.coachTrainingService.removeByIds(Arrays.asList(ids.split(",")));
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
		CoachTraining coachTraining = coachTrainingService.getById(id);
		if(coachTraining==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(coachTraining);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coachTraining
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoachTraining coachTraining) {
        return super.exportXls(request, coachTraining, CoachTraining.class, "教练员学习培训信息表");
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
        return super.importExcel(request, response, CoachTraining.class);
    }

}
