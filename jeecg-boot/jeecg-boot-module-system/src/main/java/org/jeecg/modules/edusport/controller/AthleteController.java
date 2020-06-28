package org.jeecg.modules.edusport.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.mapper.AthleteContestMapper;
import org.jeecg.modules.edusport.service.IAthleteContestService;
import org.jeecg.modules.edusport.service.IAthleteOtherTrianningInfoService;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.IAthleteSportScoreService;
import org.jeecg.modules.edusport.service.IAthleteTransportService;
import org.jeecg.modules.shiro.vo.DefContants;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
	
	@Autowired
	private ISysUserService sysUserService;
	
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
		
		//1.获取用户
		String token = req.getHeader(DefContants.X_ACCESS_TOKEN);
		if(oConvertUtils.isEmpty(token)) {
			return Result.error("Token失效!");
		}
		
		QueryWrapper<Athlete> queryWrapper = QueryGenerator.initQueryWrapper(athlete, req.getParameterMap());
		String userName = JwtUtil.getUsername(token);
		SysUser sysUser = sysUserService.getUserByName(userName);
	    if(sysUser == null) {
	    	return Result.error("Token无效!");
	    }
	    
    	// 教务人员, 看到所有运动员
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
    		queryWrapper.eq("mobile", userName);
    	}
	    
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
    
	/**
	 *  开通及解冻账号
	 *
	 * @param athlete
	 * @return
	 */
	@PutMapping(value = "/signUp")
	public Result<?> athleteSignUp(@RequestBody Athlete athlete) {
		String selectedRoles = "";
		String selectedDeparts = "";
		
		if (athlete == null || StringUtils.isEmpty(athlete.getMobile())) {
			return Result.error("请先设置手机号！");
		}
		
		SysUser user = sysUserService.getUserByName(athlete.getMobile());
		if (user == null) {
			user = new SysUser();
			
			user.setUsername(athlete.getMobile());
			user.setUserType(3); //运动员账号
			user.setPassword("123456");
			user.setWorkNo("");
			user.setRealname(athlete.getAthleteName());
			user.setBirthday(athlete.getBirthDate());
			user.setPhone(athlete.getMobile());
			user.setSex(athlete.getGender() == null? null : Integer.parseInt(athlete.getGender()));
			user.setCreateTime(new Date());//设置创建时间
			
			String salt = oConvertUtils.randomGen(8);
			user.setSalt(salt);
			String passwordEncode = PasswordUtil.encrypt(user.getUsername(), user.getPassword(), salt);
			user.setPassword(passwordEncode);
			user.setStatus(1);
			user.setDelFlag("0");

			sysUserService.addUserWithRole(user, selectedRoles);
            sysUserService.addUserWithDepart(user, selectedDeparts);
            
            return Result.ok("开通账号成功!");
		} else {
			user.setStatus(1);
			this.sysUserService.update(new SysUser().setStatus(user.getStatus()),
				new UpdateWrapper<SysUser>().lambda().eq(SysUser::getId,user.getId()));
			return Result.ok("账号解冻成功!");
		}   
	}
	

	/**
	 *  冻结账号
	 *
	 * @param athlete
	 * @return
	 */
	@PutMapping(value = "/signLock")
	public Result<?> signLock(@RequestBody Athlete athlete) {	
		SysUser user = sysUserService.getUserByName(athlete.getMobile());
		if (user != null) {
			user.setStatus(2); //冻结账号
			this.sysUserService.update(new SysUser().setStatus(user.getStatus()),
				new UpdateWrapper<SysUser>().lambda().eq(SysUser::getId,user.getId()));
			return Result.ok("账号冻结成功!");
			
		} else {
			return Result.error("未找到对应数据");
		}
	}

    

}
