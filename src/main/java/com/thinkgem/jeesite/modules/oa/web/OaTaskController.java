/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaTask;
import com.thinkgem.jeesite.modules.oa.service.OaTaskService;

/**
 * 任务信息Controller
 * @author ctt
 * @version 2019-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaTask")
public class OaTaskController extends BaseController {

	@Autowired
	private OaTaskService oaTaskService;
	
	@ModelAttribute
	public OaTask get(@RequestParam(required=false) String id) {
		OaTask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaTaskService.get(id);
		}
		if (entity == null){
			entity = new OaTask();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaTask:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaTask oaTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaTask> page = oaTaskService.findPage(new Page<OaTask>(request, response), oaTask); 
		model.addAttribute("page", page);
		return "modules/oa/oaTaskList";
	}

	@RequiresPermissions("oa:oaTask:view")
	@RequestMapping(value = "form")
	public String form(OaTask oaTask, Model model) {
		model.addAttribute("oaTask", oaTask);
		return "modules/oa/oaTaskForm";
	}

	@RequiresPermissions("oa:oaTask:edit")
	@RequestMapping(value = "save")
	public String save(OaTask oaTask, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaTask)){
			return form(oaTask, model);
		}
		oaTaskService.save(oaTask);
		addMessage(redirectAttributes, "保存任务信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaTask/?repage";
	}
	
	@RequiresPermissions("oa:oaTask:edit")
	@RequestMapping(value = "delete")
	public String delete(OaTask oaTask, RedirectAttributes redirectAttributes) {
		oaTaskService.delete(oaTask);
		addMessage(redirectAttributes, "删除任务信息成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaTask/?repage";
	}

}