/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.infc.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.infc.entity.DataStatus;
import com.thinkgem.jeesite.modules.infc.entity.DataStatusList;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyDao;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyRecordDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.entity.OaNotifyRecord;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通知通告Controller
 * @author  ctt
 * @version 2014-05-16
 */
@CrossOrigin
@Controller
@RequestMapping(value = "${adminPath}/infc/infcOaNotify")
public class InfcOaNotifyController extends BaseController {

	@Autowired
	private OaNotifyService oaNotifyService;
	@Autowired
	private OaNotifyDao oaNotifyDao;
	@Autowired
	private OaNotifyRecordDao oaNotifyRecordDao;

	/**
	 * 通告详情
	 */
	@ResponseBody
	@RequestMapping(value = "notifyDetails",method = RequestMethod.GET)
	public String get(HttpServletRequest request, HttpServletResponse response) {
		String oanotifyId = request.getParameter("notifyId");
		OaNotify entity = null;
		if (StringUtils.isNotBlank(oanotifyId)){
			entity = oaNotifyService.get(oanotifyId);
		}
		if (entity == null){
			entity = new OaNotify();
		}
		Map<String,Object> map = Maps.newHashMap();
		entity = oaNotifyService.getRecordList(entity);
		List<OaNotifyRecord> list = entity.getOaNotifyRecordList();
		map.put("title", entity.getTitle());
		map.put("content", entity.getContent());
		map.put("files", entity.getFiles());
		map.put("imgPath1",entity.getImgPath1());
		map.put("imgPath2",entity.getImgPath2());
		map.put("imgPath3",entity.getImgPath3());
		map.put("urgentFlag", entity.getUrgentFlag());
		map.put("send_user_name",UserUtils.get(entity.getCreateBy().getId()).getName());
		DateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		String reTime = format.format(entity.getCreateDate());
		map.put("send_date",reTime);
		//List<Map<String, Object>> listdata = Lists.newArrayList();
		//for (int i=0;i<list.size();i++){
		//	Map<String,Object> map2 = Maps.newHashMap();
		//	OaNotifyRecord oaNotifyRecord1 = oaNotifyRecordDao.get(list.get(i).getId());
		//	map2.put("userid",oaNotifyRecord1.getReceUserId());
		//	User user = UserUtils.get(oaNotifyRecord1.getReceUserId());
		//	String username = user.getName();
		//	map2.put("username",username);
         //   listdata.add(map2);
		//}
		//map.put("listdata", listdata);
		DataStatus status = new DataStatus();
		status.setSuccess("true");
		status.setStatusMessage("ok");
		status.setData(map);
		return this.renderString(response,status);
	}


	/**
	 * 我发布的通知通告列表
	 */
	@RequestMapping(value = "publishNotifyList",method = RequestMethod.GET)
	public String list(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		String userid = request.getParameter("userId");//当前用户
		User user = UserUtils.get(userid);
		oaNotify.setCreateBy(user);
		List<OaNotify> oaNotifyList = oaNotifyDao.findList(oaNotify);
		DataStatusList status = new DataStatusList();
		status.setSuccess("true");

		if (oaNotifyList.size()>0){
			status.setStatusMessage("ok");
		}else {
			status.setStatusMessage("暂无数据");
		}
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for(OaNotify oaNotify1 : oaNotifyList){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", oaNotify1.getId());
			map.put("title", oaNotify1.getTitle());
			//map.put("content", oaNotify1.getContent());
			//map.put("files", oaNotify1.getFiles());
			map.put("urgentFlag", oaNotify1.getUrgentFlag());
			map.put("send_user_name",UserUtils.get(oaNotify1.getCreateBy().getId()).getName());
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
			String reTime = format.format(oaNotify1.getCreateDate());
			map.put("send_date",reTime);
			mapList.add(map);
		}
		status.setData(mapList);
		return this.renderString(response,status);
	}

	/**
	 * 我的通知列表
	 */
	@RequestMapping(value = "selfList",method = RequestMethod.GET)
	public String selfList(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response) {
		//手机端传来当前用户的id  create_by，然后查询出本用户接收到的通知通告
		String userid = request.getParameter("userId");
		User user = UserUtils.get(userid);
		oaNotify.setCurrentUser(user);
		oaNotify.setSelf(true);//查询出我接收到的信息
		List<OaNotify> oaNotifyList = oaNotifyDao.findList(oaNotify);
		DataStatusList status = new DataStatusList();
		status.setSuccess("true");
		if (oaNotifyList.size()>0){
			status.setStatusMessage("ok");
		}else {
			status.setStatusMessage("暂无数据");
		}
		status.setStatusMessage("ok");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for(OaNotify oaNotify1 : oaNotifyList){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", oaNotify1.getId());
			map.put("title", oaNotify1.getTitle());
//			map.put("content", oaNotify1.getContent());
//			map.put("files", oaNotify1.getFiles());
			map.put("urgentFlag", oaNotify1.getUrgentFlag());
			map.put("send_user_name",UserUtils.get(oaNotify1.getCreateBy().getId()).getName());
			DateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
			String reTime = format.format(oaNotify1.getCreateDate());
			map.put("send_date",reTime);
			mapList.add(map);
		}
		status.setData(mapList);
		return this.renderString(response,status);
	}


}