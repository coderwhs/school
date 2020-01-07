package org.jeecg.modules.edusport.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.OutlineCoach;
import org.jeecg.modules.edusport.service.IOutlineCoachService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecgframework.poi.util.PoiPublicUtil;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecgframework.poi.util.PoiPublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: tb_edu_outline_coach
 * @Author: jeecg-boot
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/outlineCoach")
@Slf4j
public class OutlineCoachController extends JeecgController<OutlineCoach, IOutlineCoachService> {
	@Autowired
	private IOutlineCoachService outlineCoachService;

	/**
	 * 分页列表查询
	 *
	 * @param outlineCoach
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OutlineCoach outlineCoach,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OutlineCoach> queryWrapper = QueryGenerator.initQueryWrapper(outlineCoach, req.getParameterMap());
		Page<OutlineCoach> page = new Page<OutlineCoach>(pageNo, pageSize);
		IPage<OutlineCoach> pageList = outlineCoachService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param outlineCoach
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OutlineCoach outlineCoach) {
		outlineCoachService.save(outlineCoach);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param outlineCoach
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OutlineCoach outlineCoach) {
		outlineCoachService.updateById(outlineCoach);
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
		outlineCoachService.removeById(id);
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
		this.outlineCoachService.removeByIds(Arrays.asList(ids.split(",")));
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
		OutlineCoach outlineCoach = outlineCoachService.getById(id);
		if(outlineCoach==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(outlineCoach);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param outlineCoach
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OutlineCoach outlineCoach) {
        return super.exportXls(request, outlineCoach, OutlineCoach.class, "tb_edu_outline_coach");
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
        return super.importExcel(request, response, OutlineCoach.class);
//    	 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//         Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//         for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//             MultipartFile file = entity.getValue();// 获取上传文件对象
//             ImportParams params = new ImportParams();
//             params.setTitleRows(2);
//             params.setHeadRows(1);
//             params.setNeedSave(true);
//             System.out.println("file.getName() = " + file.getName());
//             try {
// 				InputStream inputstream = file.getInputStream();
// 				System.out.println();
// 				List<T> result = new ArrayList<T>();
// 				Workbook book = null;
// 				boolean isXSSFWorkbook = true;
// 				if (!(inputstream.markSupported())) {
// 					inputstream = new PushbackInputStream(inputstream, 8);
// 				}
// 				if (POIFSFileSystem.hasPOIFSHeader(inputstream)) {
// 					book = new HSSFWorkbook(inputstream);
// 					isXSSFWorkbook = false;
// 				} else if (POIXMLDocument.hasOOXMLHeader(inputstream)) {
// 					try {
// 						book = new XSSFWorkbook(OPCPackage.open(inputstream));
// 					} catch (InvalidFormatException e) {
// 						e.printStackTrace();
// 					}
// 				}
//
// 				System.out.println("getFirstCellNum = " + book.getNumberOfSheets());
// 				Sheet sheet = book.getSheetAt(0);
// 				for(int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
// 					Row row = sheet.getRow(i);
// 					for(int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
// 						System.out.println("row = " + i + ", col = " + j + ", value = " + row.getCell(j));
// 					}
// 					
// 				}
// 				Map<String, PictureData> pictures;
// 				for (int i = 0; i < params.getSheetNum(); i++) {
// 					if (isXSSFWorkbook) {
// 						pictures = PoiPublicUtil.getSheetPictrues07((XSSFSheet) book.getSheetAt(i), (XSSFWorkbook) book);
// 					} else {
// 						pictures = PoiPublicUtil.getSheetPictrues03((HSSFSheet) book.getSheetAt(i), (HSSFWorkbook) book);
// 					}
//
// 					
//
// 				}
// 				
// 			} catch (Exception e) {
//                log.error(e.getMessage(), e);
//                return Result.error("文件导入失败:" + e.getMessage());
//            } finally {
//                try {
//                    file.getInputStream().close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//         }
//         return Result.ok("文件导入成功！数据行数：" );
    }

}
