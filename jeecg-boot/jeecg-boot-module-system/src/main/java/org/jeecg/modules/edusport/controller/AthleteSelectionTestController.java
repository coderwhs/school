package org.jeecg.modules.edusport.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.entity.AthleteSelectionGroupIndexGrade;
import org.jeecg.modules.edusport.entity.AthleteSelectionTest;
import org.jeecg.modules.edusport.entity.AthleteSelectionTestAthleteScore;
import org.jeecg.modules.edusport.entity.AthleteSelectionTestAthleteScoreDetail;
import org.jeecg.modules.edusport.entity.AthleteSelectionTestCoach;
import org.jeecg.modules.edusport.service.IAthleteSelectionTestAthleteScoreDetailService;
import org.jeecg.modules.edusport.service.IAthleteSelectionTestAthleteScoreService;
import org.jeecg.modules.edusport.service.IAthleteSelectionTestCoachService;
import org.jeecg.modules.edusport.service.IAthleteSelectionTestService;
import org.jeecg.modules.edusport.service.IAthleteService;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

 /**
 * @Description: 运动员选材测试表
 * @Author: jeecg-boot
 * @Date:   2020-02-18
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/athleteSelectionTest")
@Slf4j
public class AthleteSelectionTestController extends JeecgController<AthleteSelectionTest, IAthleteSelectionTestService> {
	@Autowired
	private IAthleteSelectionTestService athleteSelectionTestService;
	@Autowired
	private IAthleteSelectionTestCoachService athleteSelectionTestCoachService;
	@Autowired
	private IAthleteSelectionTestAthleteScoreService athleteSelectionTestAthleteScoreService;
	@Autowired
	private IAthleteSelectionTestAthleteScoreDetailService athleteSelectionTestAthleteScoreDetailService;
	@Autowired
	private IAthleteService athleteService;
	
	/**
	 * 分页列表查询
	 *
	 * @param athleteSelectionTest
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AthleteSelectionTest athleteSelectionTest,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AthleteSelectionTest> queryWrapper = QueryGenerator.initQueryWrapper(athleteSelectionTest, req.getParameterMap());
		queryWrapper.orderByAsc("publish_date", "test_name", "group_id", "sport_code");
		Page<AthleteSelectionTest> page = new Page<AthleteSelectionTest>(pageNo, pageSize);
		IPage<AthleteSelectionTest> pageList = athleteSelectionTestService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param athleteSelectionTest
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AthleteSelectionTest athleteSelectionTest) {
		athleteSelectionTestService.save(athleteSelectionTest);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionTest
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AthleteSelectionTest athleteSelectionTest) {
		athleteSelectionTestService.updateById(athleteSelectionTest);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   添加
	 *
	 * @param athleteSelectionGroup
	 * @return
	 */
	@PostMapping(value = "/addByStep")
	public Result<?> addByStep(@RequestBody JSONObject jsonObject) {
		AthleteSelectionTest athleteSelectionTest = new AthleteSelectionTest();
		athleteSelectionTestService.save(athleteSelectionTest);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athleteSelectionGroup
	 * @return
	 */
	@PutMapping(value = "/editByStep")
	@Transactional
	public Result<?> editByStep(@RequestBody JSONObject jsonObject) {
		log.info("editByStep.jsonObject: {}", jsonObject);

		// 运动员选材测试表
		AthleteSelectionTest athleteSelectionTest = new AthleteSelectionTest();
		athleteSelectionTest.setTestName(jsonObject.getString("testName"));
		athleteSelectionTest.setPublishDate(jsonObject.getDate("publishDate"));
		athleteSelectionTest.setGroupId(jsonObject.getString("groupId"));
		log.info("editByStep.athleteSelectionTest: {}", athleteSelectionTest.toString());
		
		String testId = jsonObject.getString("id");
		String groupId = jsonObject.getString("groupId");
		String sportCode = jsonObject.getString("sportCode");
		String sportEventCodes = jsonObject.getString("eventCodes");
		String groupIndexCodes = jsonObject.getString("indexCodes");
		String coachIds = jsonObject.getString("coachIds");
		String athleteIds = jsonObject.getString("athleteIds");
		// 测试指标
		String[] groupIndexCodeList = groupIndexCodes.split(",");
		
		// 查询待删除教练员和测试运动员
		AthleteSelectionTestCoach removeTestCoach = athleteSelectionTestCoachService.getOne(new QueryWrapper<AthleteSelectionTestCoach>().select("group_concat(id) as id").eq("test_id", testId).last("LIMIT 1"));
		if (removeTestCoach != null) {
			AthleteSelectionTestAthleteScore removeTestAthleteScore = athleteSelectionTestAthleteScoreService.getOne(new QueryWrapper<AthleteSelectionTestAthleteScore>().select("group_concat(id) as id").inSql("test_coach_id", "'"+removeTestCoach.getId().replace(",", "', '")+"'"));
			
			// 删除已有教练员、测试运动员总成绩及明细记录
			athleteSelectionTestCoachService.remove(new QueryWrapper<AthleteSelectionTestCoach>().eq("test_id", testId));
			if (removeTestAthleteScore != null) {
				athleteSelectionTestAthleteScoreService.remove(new QueryWrapper<AthleteSelectionTestAthleteScore>().inSql("test_coach_id", "'"+removeTestCoach.getId().replace(",", "', '")+"'"));
				athleteSelectionTestAthleteScoreDetailService.remove(new QueryWrapper<AthleteSelectionTestAthleteScoreDetail>().inSql("athlete_score_id", "'"+removeTestAthleteScore.getId().replace(",", "', '")+"'"));
			}
		}

		// 查询待更新测试教练及运动员
		ArrayList<AthleteSelectionTestCoach> testCoachList = new ArrayList<AthleteSelectionTestCoach>();
		ArrayList<AthleteSelectionTestAthleteScore> testAthleteScoreList = new ArrayList<AthleteSelectionTestAthleteScore>();
		ArrayList<AthleteSelectionTestAthleteScoreDetail> testAthleteScoreDetailList = new ArrayList<AthleteSelectionTestAthleteScoreDetail>();

		ArrayList<Athlete> athleteCoachList = (ArrayList<Athlete>) athleteService.list(new QueryWrapper<Athlete>().select("coach_id", "group_concat(id) as id").inSql("id", "'"+athleteIds.replace(",", "', '")+"'").groupBy("coach_id"));
		for (Athlete athleteCoach : athleteCoachList) {
			// 测试教练表
			String coachId = athleteCoach.getCoachId();
			AthleteSelectionTestCoach testCoach = new AthleteSelectionTestCoach();
			String testCoachId = UUIDGenerator.generate();
			testCoach.setId(testCoachId);
			testCoach.setTestId(testId);
			testCoach.setCoachId(coachId);
			testCoach.setGroupId(groupId);
			testCoach.setSportCode(sportCode);
			testCoach.setEventCodes(sportEventCodes);
			testCoach.setIndexCodes(groupIndexCodes);
			testCoachList.add(testCoach);
			
			
			ArrayList<Athlete> subAthleteList = (ArrayList<Athlete>) athleteService.list(new QueryWrapper<Athlete>().inSql("id", "'"+athleteCoach.getId().replace(",", "', '")+"'" ));
			for (Athlete subAthlete : subAthleteList) {
				// 测试运动员总成绩表
				AthleteSelectionTestAthleteScore testAthleteScore = new AthleteSelectionTestAthleteScore();
				String testAthleteScoreId = UUIDGenerator.generate();
				testAthleteScore.setId(testAthleteScoreId);
				testAthleteScore.setTestCoachId(testCoachId);
				testAthleteScore.setAthleteId(subAthlete.getId());
				testAthleteScore.setGender(subAthlete.getGender());
				testAthleteScore.setGrade(subAthlete.getGrade());
				testAthleteScore.setBirthDate(subAthlete.getBirthDate());
				testAthleteScore.setTestRating("");
				try {
					testAthleteScore.setAge(DateUtils.getAge(subAthlete.getBirthDate()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				testAthleteScoreList.add(testAthleteScore);
				
				// 测试运动员成绩明细表
				for (String groupIndexCode : groupIndexCodeList) {
					AthleteSelectionTestAthleteScoreDetail athleteScoreDetail = new AthleteSelectionTestAthleteScoreDetail();
					athleteScoreDetail.setId(UUIDGenerator.generate());
					athleteScoreDetail.setAthleteScoreId(testAthleteScoreId);
					athleteScoreDetail.setGroupId(groupId);
					athleteScoreDetail.setIndexCode(groupIndexCode);
					athleteScoreDetail.setTestRating("");
					
					testAthleteScoreDetailList.add(athleteScoreDetail);
				}
			}
		}
		
		athleteSelectionTestCoachService.saveBatch(testCoachList);
		athleteSelectionTestAthleteScoreService.saveBatch(testAthleteScoreList);
		athleteSelectionTestAthleteScoreDetailService.saveBatch(testAthleteScoreDetailList);
		
//		athleteSelectionTestService.updateById(athleteSelectionTest);
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
		athleteSelectionTestService.removeById(id);
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
		this.athleteSelectionTestService.removeByIds(Arrays.asList(ids.split(",")));
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
		AthleteSelectionTest athleteSelectionTest = athleteSelectionTestService.getById(id);
		if(athleteSelectionTest==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(athleteSelectionTest);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param athleteSelectionTest
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AthleteSelectionTest athleteSelectionTest) {
        return super.exportXls(request, athleteSelectionTest, AthleteSelectionTest.class, "运动员选材测试表");
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
        return super.importExcel(request, response, AthleteSelectionTest.class);
    }

}
