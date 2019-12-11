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
import org.jeecg.modules.edusport.entity.SportClassWinterPlan;
import org.jeecg.modules.edusport.service.ISportClassWinterPlanService;

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
 * @Description: 冬训阶段训练计划信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/sportClassWinterPlan")
@Slf4j
public class SportClassWinterPlanController extends JeecgController<SportClassWinterPlan, ISportClassWinterPlanService> {
	@Autowired
	private ISportClassWinterPlanService sportClassWinterPlanService;
	
	/**
	 * 分页列表查询
	 *
	 * @param sportClassWinterPlan
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SportClassWinterPlan sportClassWinterPlan,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SportClassWinterPlan> queryWrapper = QueryGenerator.initQueryWrapper(sportClassWinterPlan, req.getParameterMap());
		Page<SportClassWinterPlan> page = new Page<SportClassWinterPlan>(pageNo, pageSize);
		IPage<SportClassWinterPlan> pageList = sportClassWinterPlanService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param sportClassWinterPlan
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SportClassWinterPlan sportClassWinterPlan) {
		sportClassWinterPlanService.save(sportClassWinterPlan);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param sportClassWinterPlan
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SportClassWinterPlan sportClassWinterPlan) {
		sportClassWinterPlanService.updateById(sportClassWinterPlan);
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
		sportClassWinterPlanService.removeById(id);
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
		this.sportClassWinterPlanService.removeByIds(Arrays.asList(ids.split(",")));
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
		SportClassWinterPlan sportClassWinterPlan = sportClassWinterPlanService.getById(id);
		if(sportClassWinterPlan==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(sportClassWinterPlan);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sportClassWinterPlan
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SportClassWinterPlan sportClassWinterPlan) {
        return super.exportXls(request, sportClassWinterPlan, SportClassWinterPlan.class, "冬训阶段训练计划信息表");
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
        return super.importExcel(request, response, SportClassWinterPlan.class);
    }

}
