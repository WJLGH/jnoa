package com.thinkgem.jeesite.modules.infc.web;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.finance.entity.FinRecord;
import com.thinkgem.jeesite.modules.finance.service.FinRecordService;
import com.thinkgem.jeesite.modules.infc.entity.DataStatus;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 明细记录Controller
 * @author wjl
 * @version 2019-02-02
 */
@Controller
@RequestMapping(value = "${adminPath}/infc/infcFinRecord")
public class InfcFinRecordController extends BaseController {

    @Autowired
    private FinRecordService finRecordService;

//    @ModelAttribute
//    public FinRecord get(@RequestParam(required=false) String id) {
//        FinRecord entity = null;
//        if (StringUtils.isNotBlank(id)){
//            entity = finRecordService.get(id);
//        }
//        if (entity == null){
//            entity = new FinRecord();
//        }
//        return entity;
//    }

    @ResponseBody
    @RequestMapping(value = "detailFinRecord",method = RequestMethod.GET)
    public String get(HttpServletRequest request, HttpServletResponse response) {
        String oanotifyId = request.getParameter("id");
        FinRecord entity = null;
        if (StringUtils.isNotBlank(oanotifyId)){
            entity = finRecordService.get(oanotifyId);
        }
        if (entity == null){
            entity = new FinRecord();
        }
        Map<String,Object> map = Maps.newHashMap();

        map.put("amount",entity.getAmount());
        map.put("reType",entity.getReType());
        map.put("busType",entity.getBusType());
        map.put("noteDate",new SimpleDateFormat("yyyy-MM-dd").format(entity.getNoteDate()));
        map.put("description",entity.getDescription());
        map.put("inId",entity.getInId());
        map.put("outId",entity.getOutId());
//      使用Utils 将 对象 变为map 失败，json串为空
//        try {
//            map = PropertyUtils.describe(entity);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        DataStatus status = new DataStatus();
        status.setSuccess("true");
        status.setStatusMessage("ok");
        status.setData(map);
        String r = this.renderString(response,status);
        return r;
    }

    //请求方法为post,返回数据为json格式
    // produces属性可以设置返回数据的类型以及编码 这样就可以替代 RequestBody
    //@RequestBody   请求对象实体类 通过@requestBody可以将请求体中的JSON字符串绑定到相应的bean上
    @ResponseBody
    @RequestMapping(value = "addFinRecord",method = RequestMethod.POST)
    public String list(@RequestBody FinRecord finRecord, HttpServletRequest request, HttpServletResponse response) {

        DataStatus status = new DataStatus();

        try {
            finRecordService.save(finRecord);
            status.setSuccess("true");
            status.setStatusMessage("ok");
        } catch (Exception e) {
            e.printStackTrace();
            status.setSuccess("false");
            status.setStatusMessage("新增明细失败");
        }
        String r = this.renderString(response,status);
        return r;
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