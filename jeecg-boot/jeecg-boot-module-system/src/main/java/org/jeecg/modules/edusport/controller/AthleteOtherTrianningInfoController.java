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
import org.jeecg.modules.edusport.entity.AthleteOtherTrianningInfo;
import org.jeecg.modules.edusport.service.IAthleteOtherTrianningInfoService;

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
 * @Description: 运动员训练其他事项记录信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-15
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/athleteOtherTrianningInfo")
@Slf4j
public class AthleteOtherTrianningInfoController extends JeecgController<AthleteOtherTrianningInfo, IAthleteOtherTrianningInfoService> {
	@Autowired
	private IAthleteOtherTrianningInfoService athleteOtherTrianningInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param athleteOtherTrianningInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AthleteOtherTrianningInfo athleteOtherTrianningInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AthleteOtherTrianningInfo> queryWrapper = QueryGenerator.initQueryWrapper(athleteOtherTrianningInfo, req.getParameterMap());
		Page<AthleteOtherTrianningInfo> page = new Page<AthleteOtherTrianningInfo>(pageNo, pageSize);
		IPage<AthleteOtherTrianningInfo> pageList = athleteOtherTrianningInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param athleteOtherTrianningInfo
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AthleteOtherTrianningInfo athleteOtherTrianningInfo) {
		athleteOtherTrianningInfoService.save(athleteOtherTrianningInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteOtherTrianningInfo
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AthleteOtherTrianningInfo athleteOtherTrianningInfo) {
		athleteOtherTrianningInfoService.updateById(athleteOtherTrianningInfo);
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
		athleteOtherTrianningInfoService.removeById(id);
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
		this.athleteOtherTrianningInfoService.removeByIds(Arrays.asList(ids.split(",")));
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
		AthleteOtherTrianningInfo athleteOtherTrianningInfo = athleteOtherTrianningInfoService.getById(id);
		if(athleteOtherTrianningInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(athleteOtherTrianningInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param athleteOtherTrianningInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AthleteOtherTrianningInfo athleteOtherTrianningInfo) {
        return super.exportXls(request, athleteOtherTrianningInfo, AthleteOtherTrianningInfo.class, "运动员训练其他事项记录信息表");
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
        return super.importExcel(request, response, AthleteOtherTrianningInfo.class);
    }

}
