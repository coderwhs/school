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
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupIndexGrade;
import org.jeecg.modules.edusport.service.IAthleteSelectionGroupIndexGradeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import freemarker.template.utility.StringUtil;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

 /**
 * @Description: 运动员选材测试指标评分标准列表
 * @Author: jeecg-boot
 * @Date:   2020-02-13
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/athleteSelectionGroupIndexGrade")
@Slf4j
public class AthleteSelectionGroupIndexGradeController extends JeecgController<AthleteSelectionGroupIndexGrade, IAthleteSelectionGroupIndexGradeService> {
	@Autowired
	private IAthleteSelectionGroupIndexGradeService athleteSelectionGroupIndexGradeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param athleteSelectionGroupIndexGrade
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AthleteSelectionGroupIndexGrade athleteSelectionGroupIndexGrade,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AthleteSelectionGroupIndexGrade> queryWrapper = QueryGenerator.initQueryWrapper(athleteSelectionGroupIndexGrade, req.getParameterMap());
		queryWrapper.orderByAsc("group_id", "index_code", "gender", "start_age", "min_data", "score");
		Page<AthleteSelectionGroupIndexGrade> page = new Page<AthleteSelectionGroupIndexGrade>(pageNo, pageSize);
		IPage<AthleteSelectionGroupIndexGrade> pageList = athleteSelectionGroupIndexGradeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param athleteSelectionGroupIndexGrade
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AthleteSelectionGroupIndexGrade athleteSelectionGroupIndexGrade) {
		athleteSelectionGroupIndexGradeService.save(athleteSelectionGroupIndexGrade);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionGroupIndexGrade
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AthleteSelectionGroupIndexGrade athleteSelectionGroupIndexGrade) {
		athleteSelectionGroupIndexGradeService.updateById(athleteSelectionGroupIndexGrade);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   添加
	 *
	 * @param jsonObject
	 * @return
	 */
	@PostMapping(value = "/addBatch")
	public Result<?> addBatch(@RequestBody JSONObject jsonObject) {
		log.info("editBatch.jsonObject: {}", jsonObject);
		log.info("editBatch.groupId: {}", jsonObject.getString("groupId"));
		log.info("editBatch.indexCode: {}", jsonObject.getString("indexCode"));
		log.info("editBatch.gender: {}", jsonObject.getString("gender"));
		log.info("editBatch.getJSONArray.indexGradeList: {}", jsonObject.getJSONArray("indexGradeList").toJavaList(AthleteSelectionGroupIndexGrade.class));
		
		String groupId = jsonObject.getString("groupId");
		String indexCode = jsonObject.getString("indexCode");
		String gender = jsonObject.getString("gender");
		JSONArray indexGradeJsonArray = jsonObject.getJSONArray("indexGradeList");
		
		if (!StringUtils.isBlank(groupId) && !StringUtils.isBlank(indexCode) && !StringUtils.isBlank(indexCode) && indexGradeJsonArray != null) {
			// 删除已有记录
			athleteSelectionGroupIndexGradeService.remove(new QueryWrapper<AthleteSelectionGroupIndexGrade>()
					.eq("group_id", groupId).eq("index_code", indexCode).eq("gender", gender));
			
			// 新增测试评分标准列表
			List<AthleteSelectionGroupIndexGrade> indexGradeList = indexGradeJsonArray.toJavaList(AthleteSelectionGroupIndexGrade.class);
			for (AthleteSelectionGroupIndexGrade indexGrade: indexGradeList) {
				indexGrade.setGroupId(groupId);
				indexGrade.setIndexCode(indexCode);
				indexGrade.setGender(gender);
				athleteSelectionGroupIndexGradeService.save(indexGrade);
			}
		}
		
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionGroupIndexGrade
	 * @return
	 */
	@PutMapping(value = "/editBatch")
	public Result<?> editBatch(@RequestBody JSONObject jsonObject) {
		log.info("editBatch.jsonObject: {}", jsonObject);
		log.info("editBatch.groupId: {}", jsonObject.getString("groupId"));
		log.info("editBatch.indexCode: {}", jsonObject.getString("indexCode"));
		log.info("editBatch.gender: {}", jsonObject.getString("gender"));
		log.info("editBatch.getJSONArray.indexGradeList: {}", jsonObject.getJSONArray("indexGradeList").toJavaList(AthleteSelectionGroupIndexGrade.class));
		
		String groupId = jsonObject.getString("groupId");
		String indexCode = jsonObject.getString("indexCode");
		String gender = jsonObject.getString("gender");
		JSONArray indexGradeJsonArray = jsonObject.getJSONArray("indexGradeList");
		
		if (!StringUtils.isBlank(groupId) && !StringUtils.isBlank(indexCode) && !StringUtils.isBlank(indexCode) && indexGradeJsonArray != null) {
			// 删除已有记录
			athleteSelectionGroupIndexGradeService.remove(new QueryWrapper<AthleteSelectionGroupIndexGrade>()
					.eq("group_id", groupId).eq("index_code", indexCode).eq("gender", gender));
			
			// 新增测试评分标准列表
			List<AthleteSelectionGroupIndexGrade> indexGradeList = indexGradeJsonArray.toJavaList(AthleteSelectionGroupIndexGrade.class);
			for (AthleteSelectionGroupIndexGrade indexGrade: indexGradeList) {
				indexGrade.setGroupId(groupId);
				indexGrade.setIndexCode(indexCode);
				indexGrade.setGender(gender);
				athleteSelectionGroupIndexGradeService.save(indexGrade);
			}
		}
		
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
		athleteSelectionGroupIndexGradeService.removeById(id);
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
		this.athleteSelectionGroupIndexGradeService.removeByIds(Arrays.asList(ids.split(",")));
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
		AthleteSelectionGroupIndexGrade athleteSelectionGroupIndexGrade = athleteSelectionGroupIndexGradeService.getById(id);
		if(athleteSelectionGroupIndexGrade==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(athleteSelectionGroupIndexGrade);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param athleteSelectionGroupIndexGrade
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AthleteSelectionGroupIndexGrade athleteSelectionGroupIndexGrade) {
        return super.exportXls(request, athleteSelectionGroupIndexGrade, AthleteSelectionGroupIndexGrade.class, "运动员选材测试指标评分标准列表");
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
        return super.importExcel(request, response, AthleteSelectionGroupIndexGrade.class);
    }

}
