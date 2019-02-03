package com.thinkgem.jeesite.modules.infc.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class DataStatusList {
    private String success;        //是否成功
    private String statusMessage;  //状态信息
    //  private List<Object> data = Lists.newArrayList();	;            //数据集
    private List<Map<String, Object>> data = Lists.newArrayList();     //数据

    public DataStatusList(){
        this.success = "false";
        this.statusMessage = "失败";
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }
}
