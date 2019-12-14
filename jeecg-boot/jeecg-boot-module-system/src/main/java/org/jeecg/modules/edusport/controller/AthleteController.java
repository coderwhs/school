package org.jeecg.modules.edusport.controller;

import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.mapper.AthleteContestMapper;
import org.jeecg.modules.edusport.service.IAthleteContestService;
import org.jeecg.modules.edusport.service.IAthleteOtherTrianningInfoService;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.IAthleteSportScoreService;
import org.jeecg.modules.edusport.service.IAthleteTransportService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: 运动员信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/athlete")
@Slf4j
public class AthleteController extends JeecgController<Athlete, IAthleteService> {
	@Autowired
	private IAthleteService athleteService;
	@Resource
	private AthleteContestMapper athleteContestMapper;
	@Autowired
	private IAthleteContestService athleteContestService;
	@Autowired
	private IAthleteTransportService athleteTransportService;
	@Autowired
	private IAthleteSportScoreService athleteSportScoreService;
	@Autowired
	private IAthleteOtherTrianningInfoService athleteOtherTrianningInfoService;
	/**
	 * 分页列表查询
	 *
	 * @param athlete
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Athlete athlete,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Athlete> queryWrapper = QueryGenerator.initQueryWrapper(athlete, req.getParameterMap());
		Page<Athlete> page = new Page<Athlete>(pageNo, pageSize);
		IPage<Athlete> pageList = athleteService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param athlete
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Athlete athlete) {
		athleteService.save(athlete);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param athlete
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Athlete athlete) {
		athleteService.updateById(athlete);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	@Transactional
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
//		List<AthleteContest> athleteList = athleteContestMapper.getContestByAthleteId(id);
//		if(athleteList != null && athleteList.size() > 0) {
//			athleteList.forEach(System.out::println);
//		}
		// 运动员子表(运动员参赛记录、测试记录、输送记录、其他事项记录)一同删除。
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("athlete_id", id);
		athleteContestService.removeByMap(map);
		athleteTransportService.removeByMap(map);
		athleteSportScoreService.removeByMap(map);
		athleteOtherTrianningInfoService.removeByMap(map);
		athleteService.removeById(id);
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
		this.athleteService.removeByIds(Arrays.asList(ids.split(",")));
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
		Athlete athlete = athleteService.getById(id);
		if(athlete==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(athlete);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param athlete
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Athlete athlete) {
        return super.exportXls(request, athlete, Athlete.class, "运动员信息表");
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
        return super.importExcel(request, response, Athlete.class);
    }

}
