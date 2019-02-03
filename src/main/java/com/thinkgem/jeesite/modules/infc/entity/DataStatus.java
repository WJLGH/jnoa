package com.thinkgem.jeesite.modules.infc.entity;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class DataStatus {

    private String success;        //是否成功
    private String statusMessage;  //状态信息
//  private List<Object> data = Lists.newArrayList();	;            //数据集
    private Map<String, Object> data = Maps.newHashMap();       //数据

    public DataStatus(){
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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
