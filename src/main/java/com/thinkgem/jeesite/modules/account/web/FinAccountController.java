/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.web;

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
import com.thinkgem.jeesite.modules.account.entity.FinAccount;
import com.thinkgem.jeesite.modules.account.service.FinAccountService;

/**
 * 财务账户Controller
 * @author wjl
 * @version 2019-02-03
 */
@Controller
@RequestMapping(value = "${adminPath}/account/finAccount")
public class FinAccountController extends BaseController {

	@Autowired
	private FinAccountService finAccountService;
	
	@ModelAttribute
	public FinAccount get(@RequestParam(required=false) String id) {
		FinAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = finAccountService.get(id);
		}
		if (entity == null){
			entity = new FinAccount();
		}
		return entity;
	}

	@RequiresPermissions("account:finAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(FinAccount finAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FinAccount> page = finAccountService.findPage(new Page<FinAccount>(request, response), finAccount);
		model.addAttribute("page", page);
		return "modules/account/finAccountList";
	}

	@RequiresPermissions("account:finAccount:view")
	@RequestMapping(value = "form")
	public String form(FinAccount finAccount, Model model) {
		model.addAttribute("finAccount", finAccount);
		return "modules/account/finAccountForm";
	}

	@RequiresPermissions("account:finAccount:edit")
	@RequestMapping(value = "save")
	public String save(FinAccount finAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, finAccount)){
			return form(finAccount, model);
		}
		finAccountService.save(finAccount);
		addMessage(redirectAttributes, "保存财务账户成功");
		return "redirect:"+Global.getAdminPath()+"/account/finAccount/?repage";
	}
	
	@RequiresPermissions("account:finAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(FinAccount finAccount, RedirectAttributes redirectAttributes) {
		finAccountService.delete(finAccount);
		addMessage(redirectAttributes, "删除财务账户成功");
		return "redirect:"+Global.getAdminPath()+"/account/finAccount/?repage";
	}

}