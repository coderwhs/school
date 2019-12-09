package org.jeecg.modules.edusport.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.DormStudent;
import org.jeecg.modules.edusport.entity.DormStudentLeave;
import org.jeecg.modules.edusport.entity.Dorm;
import org.jeecg.modules.edusport.vo.DormPage;
import org.jeecg.modules.edusport.service.IDormService;
import org.jeecg.modules.edusport.service.IDormStudentService;
import org.jeecg.modules.edusport.service.IDormStudentLeaveService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 宿舍信息表
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/dorm")
@Slf4j
public class DormController {
	@Autowired
	private IDormService dormService;
	@Autowired
	private IDormStudentService dormStudentService;
	@Autowired
	private IDormStudentLeaveService dormStudentLeaveService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dorm
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Dorm dorm,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Dorm> queryWrapper = QueryGenerator.initQueryWrapper(dorm, req.getParameterMap());
		Page<Dorm> page = new Page<Dorm>(pageNo, pageSize);
		IPage<Dorm> pageList = dormService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param dormPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DormPage dormPage) {
		Dorm dorm = new Dorm();
		BeanUtils.copyProperties(dormPage, dorm);
		dormService.saveMain(dorm, dormPage.getDormStudentList(),dormPage.getDormStudentLeaveList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dormPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DormPage dormPage) {
		Dorm dorm = new Dorm();
		BeanUtils.copyProperties(dormPage, dorm);
		Dorm dormEntity = dormService.getById(dorm.getId());
		if(dormEntity==null) {
			return Result.error("未找到对应数据");
		}
		dormService.updateMain(dorm, dormPage.getDormStudentList(),dormPage.getDormStudentLeaveList());
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
		dormService.delMain(id);
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
		this.dormService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Dorm dorm = dormService.getById(id);
		if(dorm==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dorm);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryDormStudentByMainId")
	public Result<?> queryDormStudentListByMainId(@RequestParam(name="id",required=true) String id) {
		List<DormStudent> dormStudentList = dormStudentService.selectByMainId(id);
		return Result.ok(dormStudentList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryDormStudentLeaveByMainId")
	public Result<?> queryDormStudentLeaveListByMainId(@RequestParam(name="id",required=true) String id) {
		List<DormStudentLeave> dormStudentLeaveList = dormStudentLeaveService.selectByMainId(id);
		return Result.ok(dormStudentLeaveList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dorm
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Dorm dorm) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<Dorm> queryWrapper = QueryGenerator.initQueryWrapper(dorm, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<Dorm> queryList = dormService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<Dorm> dormList = new ArrayList<Dorm>();
      if(oConvertUtils.isEmpty(selections)) {
          dormList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          dormList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<DormPage> pageList = new ArrayList<DormPage>();
      for (Dorm main : dormList) {
          DormPage vo = new DormPage();
          BeanUtils.copyProperties(main, vo);
          List<DormStudent> dormStudentList = dormStudentService.selectByMainId(main.getId());
          vo.setDormStudentList(dormStudentList);
          List<DormStudentLeave> dormStudentLeaveList = dormStudentLeaveService.selectByMainId(main.getId());
          vo.setDormStudentLeaveList(dormStudentLeaveList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "宿舍信息表列表");
      mv.addObject(NormalExcelConstants.CLASS, DormPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("宿舍信息表数据", "导出人:"+sysUser.getRealname(), "宿舍信息表"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<DormPage> list = ExcelImportUtil.importExcel(file.getInputStream(), DormPage.class, params);
              for (DormPage page : list) {
                  Dorm po = new Dorm();
                  BeanUtils.copyProperties(page, po);
                  dormService.saveMain(po, page.getDormStudentList(),page.getDormStudentLeaveList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

}
