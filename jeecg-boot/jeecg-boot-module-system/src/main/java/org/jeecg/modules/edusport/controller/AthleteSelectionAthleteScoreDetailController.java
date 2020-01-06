package org.jeecg.modules.edusport.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScoreDetail;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupIndex;
import org.jeecg.modules.edusport.mapper.AthleteMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionAthleteScoreDetailMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionGroupIndexGradeMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionGroupIndexMapper;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreDetailService;
import org.jeecg.modules.edusport.service.IAthleteSelectionGroupIndexService;
import org.jeecg.modules.edusport.service.IAthleteSelectionIndexService;
import org.jeecg.modules.shiro.vo.DefContants;
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
import lombok.Data;
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
	private AthleteSelectionGroupIndexMapper athleteSelectionGroupIndexMapper;
	@Resource
	private AthleteSelectionGroupIndexGradeMapper athleteSelectionGroupIndexGradeMapper;
	@Autowired
	private IAthleteSelectionGroupIndexService ahleteSelectionGroupIndexService;
	@Autowired
	private IAthleteSelectionIndexService athleteSelectionIndexService;
	@Resource
	private AthleteSelectionAthleteScoreDetailMapper athleteSelectionAthleteScoreDetailMapper;
	/**o
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
//		queryWrapper.orderByAsc("group_id","index_id");
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
	@Transactional
	public Result<?> add(@RequestBody AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail) {
		athleteSelectionAthleteScoreDetailService.save(athleteSelectionAthleteScoreDetail);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 引入运动员信息.
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/importAthleteIndex")
	@Transactional
	public Result<?> calculateScore(HttpServletRequest request, @RequestParam(name="athleteScoreId",required=true) String athleteScoreId,@RequestParam(name="athleteId",required=true) String athleteId,
			@RequestParam(name="testId",required=true) String testId,@RequestParam(name="groupId",required=true) String groupId) {
		String resultInfo = "添加成功！";
		AthleteSelectionGroupIndex athleteSelectionGroupIndex = athleteSelectionGroupIndexMapper.getIndexByGroupId(groupId);
		if(athleteSelectionGroupIndex != null && athleteSelectionGroupIndex.getIndexId() != null
				&& !"".equals(athleteSelectionGroupIndex.getIndexId())) {
			String[] indexId = athleteSelectionGroupIndex.getIndexId().split(",");
			String token = request.getHeader(DefContants.X_ACCESS_TOKEN);

			String username = "";
			if(!oConvertUtils.isEmpty(token)) {
				username = JwtUtil.getUsername(token);
			}

			List<AthleteSelectionAthleteScoreDetail> list = new ArrayList<AthleteSelectionAthleteScoreDetail>();
			List<AthleteSelectionAthleteScoreDetail> indexList = new ArrayList<AthleteSelectionAthleteScoreDetail>();
			for(int i = 0; i < indexId.length; i++) {
				AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail = new AthleteSelectionAthleteScoreDetail();
				AthleteSelectionAthleteScoreDetail deleteDetail = new AthleteSelectionAthleteScoreDetail();
				athleteSelectionAthleteScoreDetail.setCreateTime(Calendar.getInstance().getTime());
				athleteSelectionAthleteScoreDetail.setUpdateTime(Calendar.getInstance().getTime());
				athleteSelectionAthleteScoreDetail.setCreateBy(username);
				athleteSelectionAthleteScoreDetail.setUpdateBy(username);
				
				athleteSelectionAthleteScoreDetail.setAthleteId(athleteId);
				athleteSelectionAthleteScoreDetail.setAthleteScoreId(athleteScoreId);
				athleteSelectionAthleteScoreDetail.setGroupId(groupId);
				athleteSelectionAthleteScoreDetail.setTestId(testId);
				athleteSelectionAthleteScoreDetail.setTestValue("0");
				athleteSelectionAthleteScoreDetail.setTestScore(Integer.valueOf(0));
				athleteSelectionAthleteScoreDetail.setIndexCode(indexId[i]);// 指标记录.
				list.add(athleteSelectionAthleteScoreDetail);
				
				// 删除明细.
				deleteDetail.setAthleteScoreId(athleteScoreId);
				deleteDetail.setAthleteId(athleteId);
				deleteDetail.setTestId(testId);
				deleteDetail.setGroupId(groupId);
				deleteDetail.setIndexCode(indexId[i]);
				indexList.add(deleteDetail);
			}
			athleteSelectionAthleteScoreDetailMapper.deleteIndexByIndexId(indexList);
			athleteSelectionAthleteScoreDetailService.saveBatch(list);
		} else {
			resultInfo = "组别指标信息不存在，请确认！";
		}
		
		return Result.ok(resultInfo);
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
			@RequestParam(name = "indexCode", required = false) String indexCode,
			@RequestParam(name = "testValue", required = true) String testValue) {
		// 运动员信息.
		JSONObject jsonObject = new JSONObject();
		@SuppressWarnings("rawtypes")
		HashMap athleteMap = (HashMap) athleteMapper.getAthleteAgeById(athleteId);
		if(athleteMap != null && !athleteMap.isEmpty()) {
			Integer score = athleteSelectionGroupIndexGradeMapper.getAthleteScoreByTestValue(groupId, indexCode,
					athleteMap.get("gender").toString(), Integer.valueOf(athleteMap.get("age").toString()),
					Double.parseDouble(testValue));
			if(score != null) {
				jsonObject = new JSONObject("{\"testScord\":" + score.intValue() + "}");
			} else {
				jsonObject = new JSONObject("{\"testScord\":" + 0 + "}");
			}	
		}

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
