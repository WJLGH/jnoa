/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.infc.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.infc.entity.DataStatus;
import com.thinkgem.jeesite.modules.infc.entity.DataStatusList;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

/**
* @Description:    用户信息接口
* @Author:         wfp
* @CreateDate:     2019/1/8 18:13
*/
@CrossOrigin
@Controller
@RequestMapping(value = "${adminPath}/infc/infcuser")
public class InfcUserController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UserDao userDao;

	/**
	* @Description:    用户个人信息接口
	* @Author:         wfp
	* @CreateDate:     2019/1/8 18:31
	*/
	@RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
	public String getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		DataStatus status = new DataStatus();
		Map<String, Object> data = Maps.newHashMap();
		String userName = request.getParameter("userName");  //获取传递参数 userName
		User user = systemService.getUserByLoginName(userName);
		if(user != null){
			status.setStatusMessage("ok");
			status.setSuccess("true");
			data.put("userId",user.getId());
			data.put("userName",user.getLoginName());
			data.put("name",user.getName());
			data.put("position",user.getPosition());
			data.put("officeId",user.getOffice().getId());
			data.put("officeName",user.getOffice().getName());
		}
		else{
			status.setStatusMessage("失败");
		}
		status.setData(data);
		return this.renderString(response,status);
	}

	/**
	* @Description:    获取部门及人员信息
	* @Author:         wfp
	* @CreateDate:     2019/1/8 18:32
	*/
    @RequestMapping(value = "getOfficeAndUser")
    public String getOfficeAndUser(HttpServletRequest request, HttpServletResponse response){
        DataStatusList status = new DataStatusList();
        List<Map<String, Object>> data = Lists.newArrayList();
        List<Office> list = officeService.findList(true);
        for(Office office: list){
            Map<String,Object> map = Maps.newHashMap();
            map.put("id", office.getId());
            map.put("userId",office.getId());
            map.put("pId", office.getParentId());
            map.put("name", office.getName());
            map.put("type", "部门");
            data.add(map);
            List<User> userList = systemService.findUserByOfficeId(office.getId());
            for (User user : userList){
                Map<String, Object> map2 = Maps.newHashMap();
                map2.put("id", IdGen.uuid());
                map2.put("userId",user.getId());
                map2.put("pId", office.getId());
                map2.put("name",user.getName());
                map2.put("type", "人员");
                data.add(map2);
            }
        }
        status.setSuccess("true");
        status.setStatusMessage("ok");
        status.setData(data);
        return this.renderString(response,status);
    }

    /**
    * @Description:    更新密码
    * @Author:         wfp
    * @CreateDate:     2019/1/10 21:07
    */
	@RequestMapping(value = "updatePassword",method = RequestMethod.GET)
	public String updatePassword(HttpServletRequest request, HttpServletResponse response) {
		DataStatus status = new DataStatus();
		String userName = request.getParameter("userName");   //获取传递参数 用户登录名
    	String oldPassword = request.getParameter("oldPassword");  //获取传递参数 旧密码
    	String newPassword = request.getParameter("newPassword");   //获取传递参数 新密码
		User user =systemService.getUserByLoginName(userName);
		if (SystemService.validatePassword(oldPassword, user.getPassword())){
			systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);  //更新密码
			status.setStatusMessage("ok");
			status.setSuccess("true");
		}
		else{
			status.setStatusMessage("旧密码错误");
		}
		return this.renderString(response,status);
	}



	/**
	 * @Description:    根据部门id获取本部门的人员信息列表
	 * @Author:         ctt
	 * @CreateDate:     2019/1/12 11:52
	 */
	@RequestMapping(value = "getUserByOffice",method = RequestMethod.GET)
	public String getUserInfoByOfficeId(User user,HttpServletRequest request, HttpServletResponse response){
		String officeId = request.getParameter("officeId");
		Office office = officeService.get(officeId);
		user.setOffice(office);
		List<User> list = userDao.findUserByOfficeId(user);
		DataStatusList status = new DataStatusList();
		if (list.size()>0){
			status.setStatusMessage("ok");
		}else {
			status.setStatusMessage("暂无数据");
		}
		status.setSuccess("true");
		List<Map<String, Object>> data = Lists.newArrayList();
		for (User user1:list){
			Map<String, Object> userinfo = Maps.newHashMap();
			userinfo.put("id",user1.getId());
			userinfo.put("name",user1.getName());
			data.add(userinfo);
		}
		status.setData(data);
		return this.renderString(response,status);

	}


	/**
	 * @Description:    获取全公司的部长人员列表
	 * @Author:         ctt
	 * @CreateDate:     2019/1/12 12：08
	 */

	@RequestMapping(value = "getOfficer",method = RequestMethod.GET)
	public String getOfficer(User user,HttpServletRequest request, HttpServletResponse response){
		user.setPosition("部长");
		List<User> list = userDao.findList(user);
		List<Map<String, Object>> data = Lists.newArrayList();
		for (User user1:list){
			Map<String, Object> userinfo = Maps.newHashMap();
			userinfo.put("id",user1.getId());
			userinfo.put("name",user1.getName());
			data.add(userinfo);
		}
		DataStatusList status = new DataStatusList();
		if (list.size()>0){
			status.setStatusMessage("ok");
		}else {
			status.setStatusMessage("暂无数据");
		}
		status.setSuccess("true");
		status.setData(data);
		return this.renderString(response,status);
	}
}
