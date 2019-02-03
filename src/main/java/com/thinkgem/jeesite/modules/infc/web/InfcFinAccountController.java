/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.infc.web;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.entity.FinAccount;
import com.thinkgem.jeesite.modules.account.service.FinAccountService;
import com.thinkgem.jeesite.modules.finance.entity.FinRecord;
import com.thinkgem.jeesite.modules.finance.service.FinRecordService;
import com.thinkgem.jeesite.modules.infc.entity.DataStatusList;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 财务账户Controller
 * @author wjl
 * @version 2019-02-03
 */
@Controller
@RequestMapping(value = "${adminPath}/infc/infcFinAccount")
public class InfcFinAccountController extends BaseController {

    @Autowired
    private FinAccountService finAccountService;

    @Autowired
    private FinRecordService finRecordService;

//	@ModelAttribute
//	public FinAccount get(@RequestParam(required=false) String id) {
//		FinAccount entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = finAccountService.get(id);
//		}
//		if (entity == null){
//			entity = new FinAccount();
//		}
//		return entity;
//	}

    @ResponseBody
    @RequestMapping(value = "listFinRecord",method = RequestMethod.GET)
    public String listFinRecord(HttpServletRequest request, HttpServletResponse response) {
        String acName = request.getParameter("acName");
        String id = request.getParameter("id");
        FinAccount finAccount = new FinAccount();
        finAccount.setId(id);
        finAccount.setAcName(acName);
        DataStatusList status = new DataStatusList();
        try {
            List<FinRecord> list = finRecordService.findListByAccount(finAccount);
            List<Map<String,Object>> result = new LinkedList<Map<String, Object>>();
            for(FinRecord entity : list) {
                Map<String,Object> map = Maps.newHashMap();
                map.put("id",entity.getId());
                map.put("amount",entity.getAmount());
                map.put("reType",entity.getReType());
                map.put("noteDate",new SimpleDateFormat("yyyy-MM-dd").format(entity.getNoteDate()));
                map.put("description",entity.getDescription());
                result.add(map);
            }
            status.setData(result);
            status.setSuccess("true");
            status.setStatusMessage("ok");
        } catch (Exception e) {
            e.printStackTrace();
            status.setSuccess("false");
            status.setStatusMessage(e.getMessage());
        }
        String r = this.renderString(response,status);
        return r;
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