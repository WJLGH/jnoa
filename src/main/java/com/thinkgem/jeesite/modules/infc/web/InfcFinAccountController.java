/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.infc.web;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.account.entity.FinAccount;
import com.thinkgem.jeesite.modules.account.service.FinAccountService;
import com.thinkgem.jeesite.modules.finance.entity.FinRecord;
import com.thinkgem.jeesite.modules.finance.service.FinRecordService;
import com.thinkgem.jeesite.modules.infc.entity.DataStatusList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 返回该账户下的所有记录信息列表
     * @author wjl
     * @date 2019/2/13 9:51
     */
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
    /**
     * 账户列表，返回所有的账户id和名称
     * @author wjl
     * @date 2019/2/13 9:50
     */
    @ResponseBody
    @RequestMapping(value = "allAccount",method = RequestMethod.GET)
    public String allAccount(HttpServletRequest request, HttpServletResponse response) {
        DataStatusList status = new DataStatusList();
        try  {
            List<FinAccount> list = finAccountService.findAllAccount();
            List<Map<String,Object>> result = new LinkedList<Map<String, Object>>();
            for(FinAccount  entity : list) {
                Map<String,Object> map = Maps.newHashMap();
                map.put("id",entity.getId()) ;
                map.put("acName",entity.getAcName());
                result.add(map);
            }
            status.setData(result);
            status.setSuccess("true");
            status.setStatusMessage("ok");
        }catch (Exception e) {
            e.printStackTrace();
            status.setSuccess("false");
            status.setStatusMessage(e.getMessage());
        }
        String r = this.renderString(response,status);
        return r;
    }

    /**
     * 按账户汇总信息列表
     * @author wjl
     * @date 2019/2/13 20:41
     */
    @ResponseBody
    @RequestMapping(value = "listSumAccount",method = RequestMethod.GET)
    public String listSumAccount(FinAccount finAccount,HttpServletRequest request,HttpServletResponse response) {
        DataStatusList status = new DataStatusList();
        try  {
            List<FinAccount> list = finAccountService.getAccountSum(finAccount);
            List<Map<String,Object>> result = new LinkedList<Map<String, Object>>();
            for(FinAccount  entity : list) {
                Map<String,Object> map = Maps.newHashMap();
                map.put("id",entity.getId()) ;
                map.put("acName",entity.getAcName());
                map.put("inAmount",String.format("%.2f",entity.getInAmount()));
                map.put("outAmount",String.format("%.2f",entity.getOutAmount()));
                map.put("amount",entity.getAmount());
                result.add(map);
            }
            status.setData(result);
            status.setSuccess("true");
            status.setStatusMessage("ok");
        }catch (Exception e) {
            e.printStackTrace();
            status.setSuccess("false");
            status.setStatusMessage(e.getMessage());
        }
        String r = this.renderString(response,status);
        return r;
    }
}