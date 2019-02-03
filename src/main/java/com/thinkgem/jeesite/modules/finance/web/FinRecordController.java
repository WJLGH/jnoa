/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.finance.entity.FinRecord;
import com.thinkgem.jeesite.modules.finance.service.FinRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 明细记录Controller
 * @author wjl
 * @version 2019-02-02
 */
@Controller
@RequestMapping(value = "${adminPath}/finance/finRecord")
public class FinRecordController extends BaseController {

	@Autowired
	private FinRecordService finRecordService;
	
	@ModelAttribute
	public FinRecord get(@RequestParam(required=false) String id) {
		FinRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = finRecordService.get(id);
		}
		if (entity == null){
			entity = new FinRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("finance:finRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(FinRecord finRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FinRecord> page = finRecordService.findPage(new Page<FinRecord>(request, response), finRecord); 
		model.addAttribute("page", page);
		return "modules/finance/finRecordList";
	}

	@RequiresPermissions("finance:finRecord:view")
	@RequestMapping(value = "form")
	public String form(FinRecord finRecord, Model model) {
		model.addAttribute("finRecord", finRecord);
		return "modules/finance/finRecordForm";
	}

	@RequiresPermissions("finance:finRecord:edit")
	@RequestMapping(value = "save")
	public String save(FinRecord finRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, finRecord)){
			return form(finRecord, model);
		}
		finRecordService.save(finRecord);
		addMessage(redirectAttributes, "保存明细记录成功");
		return "redirect:"+Global.getAdminPath()+"/finance/finRecord/?repage";
	}
	
	@RequiresPermissions("finance:finRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(FinRecord finRecord, RedirectAttributes redirectAttributes) {
		finRecordService.delete(finRecord);
		addMessage(redirectAttributes, "删除明细记录成功");
		return "redirect:"+Global.getAdminPath()+"/finance/finRecord/?repage";
	}

}