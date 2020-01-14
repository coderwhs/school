package org.jeecg.modules.edusport.util;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.shiro.vo.DefContants;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserUtil {
	/**
	 * 取得当前登录用户.
	 * 
	 * @param request
	 * @return
	 */
	public static SysUser getSystemUser(HttpServletRequest request, ISysUserService sysUserService) {
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		SysUser user = new SysUser();
		if (!oConvertUtils.isEmpty(token)) {
			String username = JwtUtil.getUsername(token);
			System.out.println(">>> username: " + username);
			user = sysUserService.getUserByName(username);
		}

		return user;
	}

}
