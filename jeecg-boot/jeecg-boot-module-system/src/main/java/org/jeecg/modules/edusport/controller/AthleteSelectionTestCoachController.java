package org.jeecg.modules.edusport.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupIndexGrade;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupRating;
import org.jeecg.modules.edusport.entity.AthleteSelectionTestAthleteScore;
import org.jeecg.modules.edusport.entity.AthleteSelectionTestAthleteScoreDetail;
import org.jeecg.modules.edusport.entity.AthleteSelectionTestCoach;
import org.jeecg.modules.edusport.service.IAthleteSelectionGroupIndexGradeService;
import org.jeecg.modules.edusport.service.IAthleteSelectionGroupRatingService;
import org.jeecg.modules.edusport.service.IAthleteSelectionTestAthleteScoreDetailService;
import org.jeecg.modules.edusport.service.IAthleteSelectionTestAthleteScoreService;
import org.jeecg.modules.edusport.service.IAthleteSelectionTestCoachService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.MapExcelConstants;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecgframework.poi.excel.view.JeecgMapExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

 /**
 * @Description: 运动员选材测试教练表
 * @Author: jeecg-boot
 * @Date:   2020-02-20
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/athleteSelectionTestCoach")
@Slf4j
public class AthleteSelectionTestCoachController extends JeecgController<AthleteSelectionTestCoach, IAthleteSelectionTestCoachService> {
	@Autowired
	private IAthleteSelectionTestCoachService athleteSelectionTestCoachService;
	@Autowired
	private IAthleteSelectionTestAthleteScoreService athleteSelectionTestAthleteScoreService;
	@Autowired
	private IAthleteSelectionTestAthleteScoreDetailService athleteSelectionTestAthleteScoreDetailService;
	@Autowired
	private IAthleteSelectionGroupIndexGradeService athleteSelectionGroupIndexGradeService;
	@Autowired
	private IAthleteSelectionGroupRatingService athleteSelectionGroupRatingService;
	
	/**
	 * 分页列表查询
	 *
	 * @param athleteSelectionTestCoach
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AthleteSelectionTestCoach athleteSelectionTestCoach,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AthleteSelectionTestCoach> queryWrapper = QueryGenerator.initQueryWrapper(athleteSelectionTestCoach, req.getParameterMap());
		
//		StringBuffer selectSql = new StringBuffer();
//		selectSql.append("SELECT ");
//		selectSql.append("	CONCAT_WS(', '");
//		selectSql.append("		, CONCAT('不合格(', sum(case test_grade when 1 then 1 else 0 end), ')')");
//		selectSql.append("		, CONCAT('合格(', sum(case test_grade when 1 then 1 else 0 end), ')')");
//		selectSql.append("		, CONCAT('良好(', sum(case test_grade when 1 then 1 else 0 end), ')')");
//		selectSql.append("		, CONCAT('优秀(', sum(case test_grade when 1 then 1 else 0 end), ')')");
//		selectSql.append("	) AS test_grade ");
//		selectSql.append("	, GROUP_CONCAT(athlete_id) AS athlete_id");
//		selectSql.append("FROM tb_edu_athlete_selection_test_coach t1 ");
//		selectSql.append("LEFT JOIN tb_edu_athlete_selection_test_athlete_score t2 ON t2.test_coach_id = t1.id	");
//		selectSql.append("GROUP BY t1.id");
//		queryWrapper.select(selectSql.toString());
		
		Page<AthleteSelectionTestCoach> page = new Page<AthleteSelectionTestCoach>(pageNo, pageSize);
		IPage<AthleteSelectionTestCoach> pageList = athleteSelectionTestCoachService.page(page, queryWrapper);
		if (pageList.getTotal() > 0) {
			for (int i = 0; i < pageList.getRecords().size(); i++) {
				AthleteSelectionTestCoach testCoach = pageList.getRecords().get(i);
				// 统计测试运动员及成绩评分等级
				StringBuffer selectSql = new StringBuffer();
				selectSql.append("	CONCAT_WS(', '");
				selectSql.append("		, CONCAT('不合格(', convert(sum(case test_rating when 1 then 1 else 0 end), char), ')')");
				selectSql.append("		, CONCAT('合格(', convert(sum(case test_rating when 2 then 1 else 0 end), char), ')')");
				selectSql.append("		, CONCAT('良好(', convert(sum(case test_rating when 3 then 1 else 0 end), char), ')')");
				selectSql.append("		, CONCAT('优秀(', convert(sum(case test_rating when 4 then 1 else 0 end), char), ')')");
				selectSql.append("	) AS test_rating ");
				selectSql.append("	, GROUP_CONCAT(athlete_id) AS athlete_id");
				
				AthleteSelectionTestAthleteScore testAthleteScore = athleteSelectionTestAthleteScoreService.getOne(
						new QueryWrapper<AthleteSelectionTestAthleteScore>().select(selectSql.toString()).eq("test_coach_id", testCoach.getId()).groupBy("test_coach_id"));
				if (testAthleteScore != null) {
					testCoach.setAthleteIds(testAthleteScore.getAthleteId());
					testCoach.setTestRatings(testAthleteScore.getTestRating());
					log.info("testCoach: {}", testCoach);
					pageList.getRecords().set(i, testCoach);
				}
			}
		}
		log.info("pageList: {}", pageList);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param athleteSelectionTestCoach
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AthleteSelectionTestCoach athleteSelectionTestCoach) {
		athleteSelectionTestCoachService.save(athleteSelectionTestCoach);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionTestCoach
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AthleteSelectionTestCoach athleteSelectionTestCoach) {
		athleteSelectionTestCoachService.updateById(athleteSelectionTestCoach);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   添加
	 *
	 * @param jsonObject
	 * @return
	 */
	@PutMapping(value = "/editBatch")
	@Transactional
	public Result<?> editBatch(@RequestBody JSONObject jsonObject) {
		log.info("editBatch.jsonObject: {}", jsonObject);
		log.info("editBatch.AthleteSelectionTestCoach: {}", jsonObject.toJavaObject(AthleteSelectionTestCoach.class));
		log.info("editBatch.getJSONArray.testAthleteScoreList: {}", jsonObject.getJSONArray("testAthleteScoreList").toJavaList(AthleteSelectionTestAthleteScore.class));
		
		AthleteSelectionTestCoach testCoach = jsonObject.toJavaObject(AthleteSelectionTestCoach.class);
		ArrayList<AthleteSelectionTestAthleteScore> testAthleteScoreList = 
				(ArrayList<AthleteSelectionTestAthleteScore>) jsonObject.getJSONArray("testAthleteScoreList").toJavaList(AthleteSelectionTestAthleteScore.class);
		
		if (testCoach != null && testCoach.getId() != null) {
			String groupId = testCoach.getGroupId();
			String indexCodes = testCoach.getIndexCodes();
			
			// 获取测试等级评定标准
			ArrayList<AthleteSelectionGroupRating> groupRatingList = 
					(ArrayList<AthleteSelectionGroupRating>) athleteSelectionGroupRatingService.
					list(new QueryWrapper<AthleteSelectionGroupRating>().eq("group_id", groupId).orderByAsc("min_data"));
			if (groupRatingList == null) {
				Result.error("测试等级评定标准未设定！");
			}
			// 获取测试指标评分标准
			ArrayList<AthleteSelectionGroupIndexGrade> groupIndexGradeList = 
					(ArrayList<AthleteSelectionGroupIndexGrade>) athleteSelectionGroupIndexGradeService.
					list(new QueryWrapper<AthleteSelectionGroupIndexGrade>().eq("group_id", groupId).
							inSql("index_code", "'" + indexCodes.replace(",", "', '") + "'"));
			if (groupIndexGradeList == null) {
				Result.error("测试指标评分标准未匹配！");
			}
			for (int i = 0; i < groupIndexGradeList.size(); i++) {
				AthleteSelectionGroupIndexGrade groupIndexGrade = groupIndexGradeList.get(i);
				for (AthleteSelectionGroupRating groupRating : groupRatingList) {
					// 测试等级评定标准的【最小分值】是从小到大有序排列的
					if (groupIndexGrade.getScore() >= groupRating.getMinData() 
							&& groupIndexGrade.getScore() <= groupRating.getMaxData()) {
						groupIndexGrade.setRating(groupRating.getRating());
						groupIndexGradeList.set(i, groupIndexGrade);
						break;
					}
				}
			}
			
			// 计算测试运动员总成绩及明细成绩对应的评定等级，并更新
			ArrayList<AthleteSelectionTestAthleteScoreDetail> testAthleteScoreDetailUpdateList = new ArrayList<AthleteSelectionTestAthleteScoreDetail>();
			if (testAthleteScoreList != null) {
				for (int i = 0; i < testAthleteScoreList.size(); i++) {
					AthleteSelectionTestAthleteScore testAthleteScore = testAthleteScoreList.get(i);
					ArrayList<AthleteSelectionTestAthleteScoreDetail> testAthleteScoreDetailList = 
							testAthleteScore.getTestAthleteScoreDetailList();
					testAthleteScore.setScore(0);
					
					// 计算测试运动员指标成绩等级
					if (testAthleteScoreDetailList != null) {
						for (AthleteSelectionTestAthleteScoreDetail testAthleteScoreDetail : testAthleteScoreDetailList) {
							testAthleteScoreDetail.setTestRating("");
							testAthleteScoreDetail.setTestScore(0);
							
							for (AthleteSelectionGroupIndexGrade groupIndexGrade : groupIndexGradeList) {
								if (groupIndexGrade.getIndexCode().equals(testAthleteScoreDetail.getIndexCode()) 
										&& groupIndexGrade.getGender().equals(testAthleteScore.getGender())
										&& groupIndexGrade.getStartAge() <= testAthleteScore.getAge()
										&& groupIndexGrade.getEndAge() >= testAthleteScore.getAge()
										&& groupIndexGrade.getMinData().compareTo(testAthleteScoreDetail.getTestValue()) < 1
										&& groupIndexGrade.getMaxData().compareTo(testAthleteScoreDetail.getTestValue()) > -1) {
									
									// 将计算结果更新到测试运动员成绩明细，并加入保存集合
									testAthleteScoreDetail.setTestRating(groupIndexGrade.getRating());
									testAthleteScoreDetail.setTestScore(groupIndexGrade.getScore());
									
									// 更新测试运动员总成绩
									int score = testAthleteScore.getScore() == null ? 0 : testAthleteScore.getScore();
									score += groupIndexGrade.getScore() == null ? 0 : groupIndexGrade.getScore();
									testAthleteScore.setScore(score);
									
									break;
								}
							}
							
							testAthleteScoreDetailUpdateList.add(testAthleteScoreDetail);
						}
					}
					
					// 计算测试远动员总成绩对应的等级评定
					for (AthleteSelectionGroupRating groupRating : groupRatingList) {
						// 测试等级评定标准的【最小分值】是从小到大有序排列的
						double avgScore = testAthleteScore.getScore()/testAthleteScoreDetailList.size();
						// 测试等级评定标准的【最小分值】是从小到大有序排列的
						if (avgScore >= groupRating.getMinData() 
								&& avgScore <= groupRating.getMaxData()) {
							testAthleteScore.setTestRating(groupRating.getRating());
							break;
						}
					}
					
					// 将计算结果更新到测试运动员成绩集合
					testAthleteScoreList.set(i, testAthleteScore);
				}
			}
			
			// 更新测试教练表
			athleteSelectionTestCoachService.updateById(testCoach);
			// 更新测试运动员成绩及明细表
			athleteSelectionTestAthleteScoreService.updateBatchById(testAthleteScoreList);
			athleteSelectionTestAthleteScoreDetailService.updateBatchById(testAthleteScoreDetailUpdateList);
		}
		
		return Result.ok("添加成功！");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		athleteSelectionTestCoachService.removeById(id);
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
		this.athleteSelectionTestCoachService.removeByIds(Arrays.asList(ids.split(",")));
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
		AthleteSelectionTestCoach athleteSelectionTestCoach = athleteSelectionTestCoachService.getById(id);
		if(athleteSelectionTestCoach==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(athleteSelectionTestCoach);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param athleteSelectionTestCoach
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, @RequestParam(name="testCoachId", required=true) String testCoachId ) {
    	// 获取教练测试表数据
		// 统计测试运动员及成绩评分等级
		StringBuffer testCoachSelectSql = new StringBuffer();
		testCoachSelectSql.append("	  ( SELECT test_name FROM tb_edu_athlete_selection_test WHERE id = tb_edu_athlete_selection_test_coach.test_id) AS test_name ");
		testCoachSelectSql.append("	, ( SELECT coach_name FROM tb_edu_coach WHERE id = tb_edu_athlete_selection_test_coach.coach_id) AS coach_name ");
		testCoachSelectSql.append("	, ( SELECT sport_name FROM tb_edu_sport WHERE sport_code = tb_edu_athlete_selection_test_coach.sport_code) AS sport_name ");
		testCoachSelectSql.append("	, ( SELECT GROUP_CONCAT(DISTINCT(event_name)) FROM tb_edu_sport_event WHERE  FIND_IN_SET(event_code, tb_edu_athlete_selection_test_coach.event_codes) GROUP BY sport_code) AS event_name ");
		testCoachSelectSql.append("	, tb_edu_athlete_selection_test_coach.*");
    	AthleteSelectionTestCoach testCoach = athleteSelectionTestCoachService.getOne(
    			new QueryWrapper<AthleteSelectionTestCoach>().select(testCoachSelectSql.toString()).eq("id", testCoachId).last("LIMIT 1"));
    	
		if(testCoach == null) {
			return null;
		}
		// 无测试指标将无下载内容
		if(StringUtils.isBlank(testCoach.getIndexCodes())) {
			return null;
		}
		
    	// 获取测试运动员成绩及明细数据
		StringBuffer testAthleteScoreSelectSql = new StringBuffer();
		testAthleteScoreSelectSql.append("	  ( SELECT athlete_name FROM tb_edu_athlete WHERE id = tb_edu_athlete_selection_test_athlete_score.athlete_id) AS athlete_name ");
		testAthleteScoreSelectSql.append("	, ( SELECT s.item_text FROM sys_dict_item s WHERE s.dict_id  ");
		testAthleteScoreSelectSql.append("	      = (SELECT id FROM sys_dict WHERE dict_code = 'sex' AND s.item_value = tb_edu_athlete_selection_test_athlete_score.gender) ");
		testAthleteScoreSelectSql.append("	  ) AS gender_name  ");
		testAthleteScoreSelectSql.append("	, ( SELECT s.item_text FROM sys_dict_item s WHERE s.dict_id  ");
		testAthleteScoreSelectSql.append("	      = (SELECT id FROM sys_dict WHERE dict_code = 'edu_grade' AND s.item_value = tb_edu_athlete_selection_test_athlete_score.grade) ");
		testAthleteScoreSelectSql.append("	  ) AS grade_name  ");
		testAthleteScoreSelectSql.append("	, tb_edu_athlete_selection_test_athlete_score.* ");
		ArrayList<AthleteSelectionTestAthleteScore> testAthleteScoreList = 
				(ArrayList<AthleteSelectionTestAthleteScore>) athleteSelectionTestAthleteScoreService.list(
						new QueryWrapper<AthleteSelectionTestAthleteScore>().select(testAthleteScoreSelectSql.toString())
						.eq("test_coach_id", testCoachId).orderByAsc("CONVERT(athlete_name USING GBK)"));
		
		if(testAthleteScoreList == null) {
			return null;
		}
		if (testAthleteScoreList != null) {
			for (int i = 0; i < testAthleteScoreList.size(); i++) {
				AthleteSelectionTestAthleteScore testAthleteScore = testAthleteScoreList.get(i);
				
				// 获取测试运动员成绩明细表
				StringBuffer testAthleteScoreDetailSelectSql = new StringBuffer();
				testAthleteScoreDetailSelectSql.append("	  ( SELECT cn_name FROM tb_edu_athlete_selection_index WHERE index_code = tb_edu_athlete_selection_test_athlete_score_detail.index_code) AS index_name ");
				testAthleteScoreDetailSelectSql.append("	, tb_edu_athlete_selection_test_athlete_score_detail.* ");
				ArrayList<AthleteSelectionTestAthleteScoreDetail> testAthleteScoreDetailList = 
						(ArrayList<AthleteSelectionTestAthleteScoreDetail>) athleteSelectionTestAthleteScoreDetailService.list(
								new QueryWrapper<AthleteSelectionTestAthleteScoreDetail>().select(testAthleteScoreDetailSelectSql.toString())
								.eq("athlete_score_id", testAthleteScore.getId()).orderByAsc("index_code") );
				testAthleteScore.setTestAthleteScoreDetailList(testAthleteScoreDetailList);
				testAthleteScoreList.set(i, testAthleteScore);
			}
		}
    	
		log.info("exportXls.athleteSelectionTestCoach: {}", testCoach);
		log.info("exportXls.testAthleteScoreList: {}", testAthleteScoreList);
		
		/*** 设置表头样式 Start ***/
		// 测试教练 
		List<ExcelExportEntity> testCoachFiledsList = new ArrayList<>();
		testCoachFiledsList.add(new ExcelExportEntity("文档编号", "testCoachId"));
		testCoachFiledsList.add(new ExcelExportEntity("测试编号", "testId"));
		testCoachFiledsList.add(new ExcelExportEntity("测试日期", "testDate"));
		
		// 测试运动员
		ExcelExportEntity testAthleteScoreEntity = new ExcelExportEntity("测试运动员", "testAthleteScore");
		List<ExcelExportEntity> testAthleteScoreFiledsList = new ArrayList<>();
		testAthleteScoreFiledsList.add(new ExcelExportEntity("成绩编号", "athleteId"));
		testAthleteScoreFiledsList.add(new ExcelExportEntity("运动员", "athleteName"));
		testAthleteScoreFiledsList.add(new ExcelExportEntity("性别", "gender"));
		testAthleteScoreFiledsList.add(new ExcelExportEntity("年级", "grade"));
		testAthleteScoreFiledsList.add(new ExcelExportEntity("出生年月", "birthDate"));
		testAthleteScoreFiledsList.add(new ExcelExportEntity("年龄", "age"));
		testAthleteScoreFiledsList.add(new ExcelExportEntity("等级评定", "testRating"));
		testAthleteScoreFiledsList.add(new ExcelExportEntity("总成绩", "totalScore"));
		testAthleteScoreEntity.setList(testAthleteScoreFiledsList);
		testCoachFiledsList.add(testAthleteScoreEntity);
		
		// 测试指标成绩
		ExcelExportEntity testAthleteScoreDetailEntity = new ExcelExportEntity("测试指标", "testAthleteScoreDetail");
		List<ExcelExportEntity> testAthleteScoreDetailFiledsList = new ArrayList<>();
		ArrayList<AthleteSelectionTestAthleteScoreDetail> testAthleteScoreDetailList = testAthleteScoreList.get(0).getTestAthleteScoreDetailList();
		for (AthleteSelectionTestAthleteScoreDetail testAthleteScoreDetail : testAthleteScoreDetailList) {
			testAthleteScoreDetailFiledsList.add(new ExcelExportEntity("" + testAthleteScoreDetail.getIndexName() + "-" + testAthleteScoreDetail.getIndexCode(), testAthleteScoreDetail.getIndexCode()));
		}
		testAthleteScoreDetailEntity.setList(testAthleteScoreDetailFiledsList);
		testCoachFiledsList.add(testAthleteScoreDetailEntity);
		/*** 设置表头样式 End ***/
		
		
		/*** 设置导出教练及运动员成绩数据 Start ***/
		List<Map<String, Object>> testCoachExportList = new ArrayList<>();
		HashMap<String, Object> testCoachRow = new HashMap<String, Object>();
		// 测试教练 
		testCoachRow.put("testId", testCoach.getTestId());
		testCoachRow.put("testCoachId", testCoach.getId());
		testCoachRow.put("testDate", DateUtils.formatDate(testCoach.getTestDate()));
		
		List<Map<String,Object>> testAthleteScoreRowList = new ArrayList<>();
		List<Map<String,Object>> testAthleteScoreDetailRowList = new ArrayList<>();
		for (AthleteSelectionTestAthleteScore testAthleteScore: testAthleteScoreList) {
			// 测试运动员
			HashMap<String,Object> testAthleteScoreRow= new HashMap<String,Object>();
			testAthleteScoreRow.put("athleteId", testAthleteScore.getId());
			testAthleteScoreRow.put("athleteName", testAthleteScore.getAthleteName());
			testAthleteScoreRow.put("gender", testAthleteScore.getGenderName());
			testAthleteScoreRow.put("grade", testAthleteScore.getGradeName());
			testAthleteScoreRow.put("birthDate", DateUtils.formatDate(testAthleteScore.getBirthDate()).substring(0, 7));
			testAthleteScoreRow.put("age", testAthleteScore.getAge());
			testAthleteScoreRow.put("testRating", testAthleteScore.getTestRating());
			testAthleteScoreRow.put("totalScore", testAthleteScore.getScore());
			
			testAthleteScoreRowList.add(testAthleteScoreRow);
			
			// 测试指标成绩
			HashMap<String, Object> testAthleteScoreDetailRow= new HashMap<String,Object>();
			for (AthleteSelectionTestAthleteScoreDetail testAthleteScoreDetail: testAthleteScore.getTestAthleteScoreDetailList()) {
				testAthleteScoreDetailRow.put(testAthleteScoreDetail.getIndexCode(), testAthleteScoreDetail.getTestValue());
			}
			testAthleteScoreDetailRowList.add(testAthleteScoreDetailRow);
		}
		testCoachRow.put("testAthleteScore", testAthleteScoreRowList);
		testCoachRow.put("testAthleteScoreDetail", testAthleteScoreDetailRowList);
		
		testCoachExportList.add(testCoachRow);
		/*** 设置导出教练及运动员成绩数据 End ***/

		// Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgMapExcelView());
        mv.addObject(MapExcelConstants.FILE_NAME, testCoach.getTestName()); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(MapExcelConstants.PARAMS, new ExportParams(testCoach.getTestName() + "报表", 
        		"测试项目：" + testCoach.getSportName() + "    测试小项：" + testCoach.getEventName() + "        教练员：" + testCoach.getCoachName(), 
        		testCoach.getTestName()));
        mv.addObject(MapExcelConstants.ENTITY_LIST, testCoachFiledsList);
        mv.addObject(MapExcelConstants.MAP_LIST, testCoachExportList);
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
            params.setHeadRows(2);
            params.setNeedSave(true);
            try {
            	
            	// 导入Excel结构
            	/** 
            	[
            	    {
            	        测试编号=1230419257633099777, 
            	        文档编号=40288be4706bc39201706bc9023f002a, 
            	        测试运动员_性别=女, 
            	        测试指标_足背高-010124=1.1000000000, 
            	        测试指标_肺活量-0201=1.5000000000, 
            	        测试日期=2020-02-22, 
            	        测试指标_大腿长-010114=10.0000000000, 
            	        测试运动员_成绩编号=2c948a826e456ec6016e45f166520001, 
            	        测试指标_下肢长-010109=10.0000000000, 
            	        测试运动员_年龄=14, 
            	        测试指标_胸围-010303=1.4000000000, 
            	        测试运动员_运动员=何静, 
            	        测试运动员_年级=一年级, 
            	        测试运动员_等级评定=1, 
            	        测试运动员_出生年月=2005-11, 
            	        测试指标_踝宽-010209=1.3000000000, 
            	        测试运动员_总成绩=180, 
            	        测试指标_足宽-010208=1.2000000000
            	    }, 
            	    {
            	        测试运动员_性别=女, 
            	        测试指标_足背高-010124=2.1000000000, 
            	        测试指标_肺活量-0201=2.5000000000, 
            	        测试指标_大腿长-010114=16.0000000000, 
            	        测试运动员_成绩编号=2c948a826e456ec6016e45f166520005, 
            	        测试指标_下肢长-010109=16.0000000000, 
            	        测试运动员_年龄=14, 
            	        测试指标_胸围-010303=2.4000000000, 
            	        测试运动员_运动员=金紫薇, 
            	        测试运动员_年级=三年级, 
            	        测试运动员_等级评定=1, 
            	        测试运动员_出生年月=2005-11, 
            	        测试指标_踝宽-010209=2.3000000000, 
            	        测试运动员_总成绩=160, 
            	        测试指标_足宽-010208=2.2000000000
            	    }
            	]
            	*/
            	
            	
            	// 测试编号
            	String testId = "";
            	// 文档编号
            	String testCoachId = "";
            	// 测试日期
            	Date testDate = null;
            	// 成绩编号
            	String athleteScoreId = "";
            	
            	List<HashMap<String, Object>> list = ExcelImportUtil.importExcel(file.getInputStream(), Map.class, params);
            	for (HashMap<String, Object> rowMap : list) {
            		if (null != rowMap.get("测试编号")) {
            			testId = rowMap.get("测试编号").toString();
            		};
        			if (null != rowMap.get("文档编号")) {
        				testCoachId = rowMap.get("文档编号").toString();
            		};
            		if (null != rowMap.get("测试日期")) {
            			testDate = DateUtils.str2Date(rowMap.get("测试日期").toString(), DateUtils.date_sdf);
            		}
            		if (null != rowMap.get("测试运动员_成绩编号")) {
            			if (StringUtils.isBlank(athleteScoreId)) {
            				athleteScoreId = "'" + rowMap.get("测试运动员_成绩编号").toString() + "'";
            			} else {
            				athleteScoreId += ", '" + rowMap.get("测试运动员_成绩编号").toString() + "'";
            			}
            		}
            		
            	}
            	
            	if (StringUtils.isBlank(testId)) {
            		return Result.error("文件导入失败！【测试编号】未填写！");
            	}
            	if (StringUtils.isBlank(testCoachId)) {
            		return Result.error("文件导入失败！【文档编号】未填写！");
            	}
            	if (testDate == null) {
            		return Result.error("文件导入失败！【测试日期】未填写！");
            	}
            	if (StringUtils.isBlank(athleteScoreId)) {
            		return Result.error("文件导入失败！无【测试运动员】记录！");
            	}
            	
            	// 匹配更新的测试教练
            	AthleteSelectionTestCoach testCoach = athleteSelectionTestCoachService.getOne(
            			new QueryWrapper<AthleteSelectionTestCoach>().eq("id", testCoachId).last("LIMIT 1"));
            	if (testCoach == null) {
            		return Result.error("文件导入失败！未匹配到该项教练的测试成绩！");
            	}
            	testCoach.setTestDate(testDate);
            	testCoach.setStatus("已导入");
            	
            	
            	// 获取测试等级评定标准
    			ArrayList<AthleteSelectionGroupRating> groupRatingList = 
    					(ArrayList<AthleteSelectionGroupRating>) athleteSelectionGroupRatingService.
    					list(new QueryWrapper<AthleteSelectionGroupRating>().eq("group_id", testCoach.getGroupId()).orderByAsc("min_data"));
    			if (groupRatingList == null) {
    				Result.error("测试等级评定标准未设定！");
    			}
    			// 获取测试指标评分标准
    			ArrayList<AthleteSelectionGroupIndexGrade> groupIndexGradeList = 
    					(ArrayList<AthleteSelectionGroupIndexGrade>) athleteSelectionGroupIndexGradeService.
    					list(new QueryWrapper<AthleteSelectionGroupIndexGrade>().eq("group_id", testCoach.getGroupId()).
    							inSql("index_code", "'" + testCoach.getIndexCodes().replace(",", "', '") + "'"));
    			if (groupIndexGradeList == null) {
    				Result.error("测试指标评分标准未匹配！");
    			}
    			for (int i = 0; i < groupIndexGradeList.size(); i++) {
    				AthleteSelectionGroupIndexGrade groupIndexGrade = groupIndexGradeList.get(i);
    				for (AthleteSelectionGroupRating groupRating : groupRatingList) {
    					// 测试等级评定标准的【最小分值】是从小到大有序排列的
    					if (groupIndexGrade.getScore() >= groupRating.getMinData() 
    							&& groupIndexGrade.getScore() <= groupRating.getMaxData()) {
    						groupIndexGrade.setRating(groupRating.getRating());
    						groupIndexGradeList.set(i, groupIndexGrade);
    						break;
    					}
    				}
    			}
    			
            	// 获取待更新的测试运动员成绩表
    			StringBuffer testAthleteScoreSelectSql = new StringBuffer();
    			testAthleteScoreSelectSql.append("	  ( SELECT athlete_name FROM tb_edu_athlete WHERE id = tb_edu_athlete_selection_test_athlete_score.athlete_id) AS athlete_name ");
    			testAthleteScoreSelectSql.append("	, ( SELECT s.item_text FROM sys_dict_item s WHERE s.dict_id  ");
    			testAthleteScoreSelectSql.append("	      = (SELECT id FROM sys_dict WHERE dict_code = 'sex' AND s.item_value = tb_edu_athlete_selection_test_athlete_score.gender) ");
    			testAthleteScoreSelectSql.append("	  ) AS gender_name  ");
    			testAthleteScoreSelectSql.append("	, ( SELECT s.item_text FROM sys_dict_item s WHERE s.dict_id  ");
    			testAthleteScoreSelectSql.append("	      = (SELECT id FROM sys_dict WHERE dict_code = 'edu_grade' AND s.item_value = tb_edu_athlete_selection_test_athlete_score.grade) ");
    			testAthleteScoreSelectSql.append("	  ) AS grade_name  ");
    			testAthleteScoreSelectSql.append("	, tb_edu_athlete_selection_test_athlete_score.* ");
            	ArrayList<AthleteSelectionTestAthleteScore> testAthleteScoreList = 
        				(ArrayList<AthleteSelectionTestAthleteScore>) athleteSelectionTestAthleteScoreService.list(
        						new QueryWrapper<AthleteSelectionTestAthleteScore>().select(testAthleteScoreSelectSql.toString())
        						.eq("test_coach_id", testCoachId).inSql("id", athleteScoreId).orderByAsc("CONVERT(athlete_name USING GBK)"));
            	
            	// 获取并计算测试运动员总成绩及明细成绩对应的评定等级，并更新
    			ArrayList<AthleteSelectionTestAthleteScoreDetail> testAthleteScoreDetailUpdateList = new ArrayList<AthleteSelectionTestAthleteScoreDetail>();
    			
            	if (testAthleteScoreList != null) {
        			for (int i = 0; i < testAthleteScoreList.size(); i++) {
        				AthleteSelectionTestAthleteScore testAthleteScore = testAthleteScoreList.get(i);
        				
        				// 获取待更新的测试运动员成绩明细表
        				// 获取测试运动员成绩明细表
        				StringBuffer testAthleteScoreDetailSelectSql = new StringBuffer();
        				testAthleteScoreDetailSelectSql.append("	  ( SELECT cn_name FROM tb_edu_athlete_selection_index WHERE index_code = tb_edu_athlete_selection_test_athlete_score_detail.index_code) AS index_name ");
        				testAthleteScoreDetailSelectSql.append("	, tb_edu_athlete_selection_test_athlete_score_detail.* ");
        				ArrayList<AthleteSelectionTestAthleteScoreDetail> testAthleteScoreDetailList = 
        						(ArrayList<AthleteSelectionTestAthleteScoreDetail>) athleteSelectionTestAthleteScoreDetailService.list(
        								new QueryWrapper<AthleteSelectionTestAthleteScoreDetail>().select(testAthleteScoreDetailSelectSql.toString())
        								.eq("athlete_score_id", testAthleteScore.getId()).orderByAsc("index_code") );
        				if (testAthleteScoreDetailList != null) {
        					testAthleteScore.setScore(0);
        					
        					for (int j = 0; j < testAthleteScoreDetailList.size(); j++) {
        						AthleteSelectionTestAthleteScoreDetail testAthleteScoreDetail = testAthleteScoreDetailList.get(j);
        						testAthleteScoreDetail.setTestRating("");
								testAthleteScoreDetail.setTestScore(0);
								
        						// 将Excel文档中测试指标的值设置到对应的待更新明细表
        						for (HashMap<String, Object> rowMap : list) {
        							String assertAthleteScoreId = rowMap.get("测试运动员_成绩编号").toString();
        							String key = "测试指标_" + testAthleteScoreDetail.getIndexName() + "-" + testAthleteScoreDetail.getIndexCode();
        							boolean keyExist = rowMap.keySet().contains(key);
        							if (keyExist && testAthleteScoreDetail.getAthleteScoreId().equals(assertAthleteScoreId)) {
        								BigDecimal  testValue =  new BigDecimal(rowMap.get(key).toString());
        								testAthleteScoreDetail.setTestValue(testValue);
        								
        								break;
        							}
        						}
        						
        						// 计算测试运动员指标成绩等级
        						for (AthleteSelectionGroupIndexGrade groupIndexGrade : groupIndexGradeList) {
    								if (groupIndexGrade.getIndexCode().equals(testAthleteScoreDetail.getIndexCode()) 
    										&& groupIndexGrade.getGender().equals(testAthleteScore.getGender())
    										&& groupIndexGrade.getStartAge() <= testAthleteScore.getAge()
    										&& groupIndexGrade.getEndAge() >= testAthleteScore.getAge()
    										&& groupIndexGrade.getMinData().compareTo(testAthleteScoreDetail.getTestValue()) < 1
    										&& groupIndexGrade.getMaxData().compareTo(testAthleteScoreDetail.getTestValue()) > -1) {
    									
    									// 将计算结果更新到测试运动员成绩明细，并加入保存集合
    									testAthleteScoreDetail.setTestRating(groupIndexGrade.getRating());
    									testAthleteScoreDetail.setTestScore(groupIndexGrade.getScore());
    									
    									// 更新测试运动员总成绩
    									int score = testAthleteScore.getScore() == null ? 0 : testAthleteScore.getScore();
    									score += groupIndexGrade.getScore() == null ? 0 : groupIndexGrade.getScore();
    									testAthleteScore.setScore(score);
    									
    									break;
    								}
    							}
        						
        						testAthleteScoreDetailUpdateList.add(testAthleteScoreDetail);
//        						testAthleteScoreDetailList.set(j, testAthleteScoreDetail);
        					}
        				}
        				
        				// 计算测试远动员总成绩对应的等级评定
    					for (AthleteSelectionGroupRating groupRating : groupRatingList) {
    						// 测试等级评定标准的【最小分值】是从小到大有序排列的
    						double avgScore = testAthleteScore.getScore()/testAthleteScoreDetailList.size();
    						// 测试等级评定标准的【最小分值】是从小到大有序排列的
    						if (avgScore >= groupRating.getMinData() 
    								&& avgScore <= groupRating.getMaxData()) {
    							testAthleteScore.setTestRating(groupRating.getRating());
    							break;
    						}
    					}
    					
        				testAthleteScoreList.set(i, testAthleteScore);
        			}
        		}
            	
            	// 更新测试教练表
    			athleteSelectionTestCoachService.updateById(testCoach);
    			// 更新测试运动员成绩及明细表
    			athleteSelectionTestAthleteScoreService.updateBatchById(testAthleteScoreList);
    			athleteSelectionTestAthleteScoreDetailService.updateBatchById(testAthleteScoreDetailUpdateList);
                
                return Result.ok("文件导入成功！");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败：" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.error("文件导入失败！");
    }

}
