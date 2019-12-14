package org.jeecg.modules.edusport.controller;

import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.edusport.entity.Dorm;
import org.jeecg.modules.edusport.service.IDormAthleteLeaveService;
import org.jeecg.modules.edusport.service.IDormAthleteLivingService;
import org.jeecg.modules.edusport.service.IDormService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Description: 宿舍信息表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/dorm")
@Slf4j
public class DormController extends JeecgController<Dorm, IDormService> {
	@Autowired
	private IDormService dormService;
	@Autowired
	private IDormAthleteLeaveService dormAthleteLeaveService;
	@Autowired
	private IDormAthleteLivingService dormAthleteLivingService;
	
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
	 * @param dorm
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Dorm dorm) {
		dormService.save(dorm);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param dorm
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Dorm dorm) {
		dormService.updateById(dorm);
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
		// 宿舍管理子表(运动员住宿表、运动员请假表)一同删除。
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dorm_id", id);
		dormAthleteLeaveService.removeByMap(map);
		dormAthleteLivingService.removeByMap(map);
		dormService.removeById(id);
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
		this.dormService.removeByIds(Arrays.asList(ids.split(",")));
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
		Dorm dorm = dormService.getById(id);
		if(dorm==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(dorm);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dorm
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Dorm dorm) {
        return super.exportXls(request, dorm, Dorm.class, "宿舍信息表");
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
        return super.importExcel(request, response, Dorm.class);
    }

}
