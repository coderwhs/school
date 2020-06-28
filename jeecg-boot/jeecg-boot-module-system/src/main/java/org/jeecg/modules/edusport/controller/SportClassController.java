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
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.AthleteSportClass;
import org.jeecg.modules.edusport.entity.SportClass;
import org.jeecg.modules.edusport.service.ISportClassService;
import org.jeecg.modules.shiro.vo.DefContants;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;

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
 * @Description: 训练队表
 * @Author: jeecg-boot
 * @Date:   2019-12-13
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/sportClass")
@Slf4j
public class SportClassController extends JeecgController<SportClass, ISportClassService> {
	@Autowired
	private ISportClassService sportClassService;
	
	@Autowired
	private ISysUserService sysUserService;
	
	/**
	 * 分页列表查询
	 *
	 * @param sportClass
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SportClass sportClass,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		
		//1.获取用户
		String token = req.getHeader(DefContants.X_ACCESS_TOKEN);
		if(oConvertUtils.isEmpty(token)) {
			return Result.error("Token失效!");
		}
		
		QueryWrapper<SportClass> queryWrapper = QueryGenerator.initQueryWrapper(sportClass, req.getParameterMap());
		String userName = JwtUtil.getUsername(token);
		SysUser sysUser = sysUserService.getUserByName(userName);
	    if(sysUser == null) {
	    	return Result.error("Token无效!");
	    }
	    
    	// 教务人员, 看到所有训练队
    	if (1 == sysUser.getUserType()) {
    		// Skip
    		
    		// 教练员, 教练员只能看到所带班级运动员，只能看到自己所在队及
    	} else if (2 == sysUser.getUserType()) {
			StringBuffer coachSql = new StringBuffer();
			coachSql.append("select id from tb_edu_coach t where mobile = '");
			coachSql.append(userName);
			coachSql.append("'");
			queryWrapper.inSql("coach_id", coachSql.toString());
			
    		// 运动员, 运动员只能看到自己，只能看到自己所在队及所在队的教练
    	} else if (3 == sysUser.getUserType()) {
			StringBuffer athleteSql = new StringBuffer();
			athleteSql.append("select sport_class_id from tb_edu_athlete_sport_class where athlete_id in ");
			athleteSql.append("(select id from tb_edu_athlete t where mobile = '");
			athleteSql.append(userName);
			athleteSql.append("')");
			queryWrapper.inSql("id", athleteSql.toString());
    	}

		
		
		
		Page<SportClass> page = new Page<SportClass>(pageNo, pageSize);
		IPage<SportClass> pageList = sportClassService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param sportClass
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SportClass sportClass) {
		sportClassService.save(sportClass);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param sportClass
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SportClass sportClass) {
		sportClassService.updateById(sportClass);
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
		sportClassService.removeById(id);
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
		this.sportClassService.removeByIds(Arrays.asList(ids.split(",")));
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
		SportClass sportClass = sportClassService.getById(id);
		if(sportClass==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(sportClass);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sportClass
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SportClass sportClass) {
        return super.exportXls(request, sportClass, SportClass.class, "训练队表");
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
        return super.importExcel(request, response, SportClass.class);
    }

}
