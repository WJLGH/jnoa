package com.thinkgem.jeesite.modules.infc.web;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.finance.dao.FinGoodDao;
import com.thinkgem.jeesite.modules.finance.entity.FinGood;
import com.thinkgem.jeesite.modules.finance.entity.FinRecord;
import com.thinkgem.jeesite.modules.finance.service.FinGoodService;
import com.thinkgem.jeesite.modules.finance.service.FinRecordService;
import com.thinkgem.jeesite.modules.infc.entity.DataStatus;
import com.thinkgem.jeesite.modules.infc.entity.DataStatusList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 明细记录Controller
 *
 * @author wjl
 * @version 2019-02-02
 */
@Controller
@RequestMapping(value = "${adminPath}/infc/infcFinRecord")
public class InfcFinRecordController extends BaseController {

    @Autowired
    private FinRecordService finRecordService;
    @Autowired
    private FinGoodService finGoodService;
    @Autowired
    private FinGoodDao finGoodDao;

    /**
     * 一条明细的具体信息
     * 参数：id
     *
     * @author wjl
     * @date 2019/2/3 23:51
     */
    @ResponseBody
    @RequestMapping(value = "detailFinRecord", method = RequestMethod.GET)
    public String detailFinRecord(FinRecord finRecord, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        FinRecord entity = null;
        FinGood finGood = null;
        if (StringUtils.isNotBlank(id)) {
            finGood = finGoodService.get(id);
            entity = finRecordService.getSingle(finRecord);

        }
        if (entity == null) {
            entity = new FinRecord();
        }
        entity.setFinGood(finGood);
        Map<String, Object> map = getFinDetailMap(entity);
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
        String r = this.renderString(response, status);
        return r;
    }

    /**
     * 将对象映射为 FinRecord 详细信息的 Map
     *
     * @param entity
     * @return
     */
    private Map<String, Object> getFinDetailMap(FinRecord entity) {
        Map<String, Object> map = Maps.newHashMap();

        map.put("amount", entity.getAmount());
        map.put("reType", entity.getReType());
        map.put("busType", entity.getBusType());
        map.put("noteDate", new SimpleDateFormat("yyyy-MM-dd").format(entity.getNoteDate()));
        map.put("description", entity.getDescription());
        map.put("inId", entity.getInId());
        map.put("outId", entity.getOutId());
        map.put("dept", entity.getDept());
        FinGood finGood = entity.getFinGood();
        if (finGood != null) {
            Map<String, Object> good = Maps.newHashMap();
            good.put("goodName", finGood.getGoodName());
            good.put("price", finGood.getPrice());
            good.put("quantity", finGood.getQuantity());
            good.put("unit", finGood.getUnit());
            good.put("supplier", finGood.getSupplier());
            good.put("spAddress", finGood.getSpAddress());
            good.put("buyer", finGood.getBuyer());
            map.put("good", good);
        }
        return map;
    }

    /**
     * 接口参数 dateStr ：日期字符串
     * 年： 2019
     * 年月：2019-02
     * 年月日：2019-02-03
     *
     * @author wjl
     * @date 2019/2/3 23:50
     */
    @ResponseBody
    @RequestMapping(value = "listSumDate", method = RequestMethod.GET)
    public String listSumDate(FinRecord finRecord, HttpServletRequest request, HttpServletResponse response) {
//        String dateStr = request.getParameter("dateStr");
//        FinRecord finRecord = new FinRecord();
//        finRecord.setDateStr(dateStr);
        DataStatusList status = new DataStatusList();
        try {
            FinRecord dateSum = finRecordService.getDateSum(finRecord);
            Map<String, Object> mainData = getFinSumMap(dateSum);
            List<FinRecord> list = finRecordService.getDateSumList(finRecord);
            List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
            for (FinRecord entity : list) {
                Map<String, Object> map = getFinSumMap(entity);
                result.add(map);
            }
            status.setMainData(mainData);
            status.setData(result);
            status.setSuccess("true");
            status.setStatusMessage("ok");
        } catch (Exception e) {
            e.printStackTrace();
            status.setSuccess("false");
            status.setStatusMessage(e.getMessage());
        }
        String r = this.renderString(response, status);
        return r;
    }

    /**
     * 将对象映射为 按日期的汇总信息 Map
     *
     * @param dateSum
     * @return
     */
    private Map<String, Object> getFinSumMap(FinRecord dateSum) {
        Map<String, Object> mainData = new LinkedHashMap<String, Object>();
        mainData.put("dateStr", dateSum.getDateStr());
        mainData.put("year", dateSum.getYear());
        mainData.put("month", dateSum.getMonth());
        mainData.put("day", dateSum.getDay());
        mainData.put("inAmount", dateSum.getInAmount() != null ? dateSum.getInAmount() : 0);
        mainData.put("outAmount", dateSum.getOutAmount() != null ? dateSum.getOutAmount() : 0);
        mainData.put("amount", dateSum.getAmount() != null ? dateSum.getAmount() : 0);
        return mainData;
    }

    /**
     * 每种明细的金额汇总
     * 参数：reType
     *
     * @author wjl
     * @date 2019/2/3 23:52
     */
    @ResponseBody
    @RequestMapping(value = "listSumBusType", method = RequestMethod.GET)
    public String listSumBusType(HttpServletRequest request, HttpServletResponse response) {
        String reType = request.getParameter("reType");
        FinRecord finRecord = new FinRecord();
        finRecord.setReType(reType);
        DataStatusList status = new DataStatusList();
        try {
            FinRecord reTypeSum = finRecordService.getAllInAndOut(finRecord);
            Map<String, Object> mainData = getMainSumMap(reTypeSum);
            List<FinRecord> list = finRecordService.getBusTypsList(finRecord);
            List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
            for (FinRecord entity : list) {
                Map<String, Object> map = getFinBusTypeMap(entity);
                result.add(map);
            }
            status.setMainData(mainData);
            status.setData(result);
            status.setSuccess("true");
            status.setStatusMessage("ok");
        } catch (Exception e) {
            e.printStackTrace();
            status.setSuccess("false");
            status.setStatusMessage(e.getMessage());
        }
        String r = this.renderString(response, status);
        return r;
    }

    private Map<String, Object> getMainSumMap(FinRecord entity) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("dept", entity.getDept());
        map.put("inAmount", entity.getInAmount());
        map.put("outAmount", entity.getOutAmount());
        return map;
    }

    /**
     * 将对象映射为交易类型的信息汇总对象
     *
     * @param entity
     * @return
     */
    private Map<String, Object> getFinBusTypeMap(FinRecord entity) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("reType", entity.getReType());
        map.put("busType", entity.getBusType());
        map.put("amount", entity.getAmount());
        return map;
    }

    /**
     * 某个时间段内的明细信息
     * 参数：startDate
     * endDate
     *
     * @author wjl
     * @date 2019/2/3 23:53
     */
    @ResponseBody
    @RequestMapping(value = "listFinRecord", method = RequestMethod.GET)
    public String listFinRecord(FinRecord entityParams, HttpServletRequest request, HttpServletResponse response) {
        DataStatusList status = new DataStatusList();
        try {
            List<FinRecord> list = finRecordService.finListByEntity(entityParams);
            List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
            for (FinRecord entity : list) {
                Map<String, Object> map = getFinItemMap(entity);
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
        String r = this.renderString(response, status);
        return r;
    }

    /**
     * 将对象映射简化后的列表元素（Item)对象
     *
     * @param entity
     * @return
     */
    private Map<String, Object> getFinItemMap(FinRecord entity) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", entity.getId());
        map.put("amount", entity.getAmount());
        map.put("reType", entity.getReType());
        map.put("noteDate", new SimpleDateFormat("yyyy-MM-dd").format(entity.getNoteDate()));
        map.put("description", entity.getDescription());
        return map;
    }
    //请求方法为post,返回数据为json格式
    // produces属性可以设置返回数据的类型以及编码 这样就可以替代 RequestBody
    //@RequestBody   请求对象实体类 通过@requestBody可以将请求体中的JSON字符串绑定到相应的bean上

    /**
     * 添加一条明细信息
     * 参数 ：FinRecord
     *
     * @author wjl
     * @date 2019/2/3 23:54
     */
    @ResponseBody
    @RequestMapping(value = "addFinRecord", method = RequestMethod.POST)
    public String addFinRecord(@RequestBody FinRecord finRecord, HttpServletRequest request, HttpServletResponse response) {

        FinGood finGood = finRecord.getFinGood();
        DataStatus status = new DataStatus();
        boolean isUpdate = false;
        if (null != finRecord.getId()) {
            isUpdate = true;
        }
        try {
            finRecordService.save(finRecord,isUpdate);
            if (finGood != null) {
                FinGood oldfinGood = finGoodDao.findByRecord(finRecord);
                finGood.setRe(finRecord);
                if (oldfinGood != null) {
                    finGoodDao.update(finGood);
                } else {
                    finGoodService.save(finGood);
                }

            }
            status.setSuccess("true");
            status.setStatusMessage("ok");
        } catch (Exception e) {
            e.printStackTrace();
            status.setSuccess("false");
            status.setStatusMessage("新增明细失败");
        }
        String r = this.renderString(response, status);
        return r;
    }

    @ResponseBody
    @RequestMapping(value = "deleteFinRecord", method = RequestMethod.GET)
    public String delete(FinRecord finRecord, HttpServletRequest request, HttpServletResponse response) {
        DataStatus status = new DataStatus();
        try {
            finRecordService.delete(finRecord);
            FinGood finGood = new FinGood(finRecord.getId());
            finGood.setRe(finRecord);
            finGoodService.delete(finGood);
            status.setSuccess("true");
            status.setStatusMessage("ok");
        } catch (Exception e) {
            e.printStackTrace();
            status.setSuccess("false");
            status.setStatusMessage("删除明细失败");
        }
        String r = this.renderString(response, status);
        return r;

    }
}