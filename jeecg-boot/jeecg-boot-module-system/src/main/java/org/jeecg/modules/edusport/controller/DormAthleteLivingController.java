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
import org.jeecg.modules.edusport.entity.DormAthleteLiving;
import org.jeecg.modules.edusport.service.IDormAthleteLivingService;

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
 * @Description: 宿舍运动员入住表
 * @Author: jeecg-boot
 * @Date:   2019-12-12
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/dormAthleteLiving")
@Slf4j
public class DormAthleteLivingController extends JeecgController<DormAthleteLiving, IDormAthleteLivingService> {
	@Autowired
	private IDormAthleteLivingService dormAthleteLivingService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dormAthleteLiving
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DormAthleteLiving dormAthleteLiving,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DormAthleteLiving> queryWrapper = QueryGenerator.initQueryWrapper(dormAthleteLiving, req.getParameterMap());
		Page<DormAthleteLiving> page = new Page<DormAthleteLiving>(pageNo, pageSize);
		IPage<DormAthleteLiving> pageList = dormAthleteLivingService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dormAthleteLiving
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DormAthleteLiving dormAthleteLiving) {
		dormAthleteLivingService.save(dormAthleteLiving);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dormAthleteLiving
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DormAthleteLiving dormAthleteLiving) {
		dormAthleteLivingService.updateById(dormAthleteLiving);
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
		dormAthleteLivingService.removeById(id);
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
		this.dormAthleteLivingService.removeByIds(Arrays.asList(ids.split(",")));
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
		DormAthleteLiving dormAthleteLiving = dormAthleteLivingService.getById(id);
		if(dormAthleteLiving==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dormAthleteLiving);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dormAthleteLiving
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DormAthleteLiving dormAthleteLiving) {
        return super.exportXls(request, dormAthleteLiving, DormAthleteLiving.class, "宿舍运动员入住表");
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
        return super.importExcel(request, response, DormAthleteLiving.class);
    }

}
