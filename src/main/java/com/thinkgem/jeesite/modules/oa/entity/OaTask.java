/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 任务信息Entity
 * @author ctt
 * @version 2019-01-10
 */
public class OaTask extends DataEntity<OaTask> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 任务标题
	private String content;		// 任务内容
	private String files;		// 附件
	private String forwardFlag;		// 是否是转发，0：否，1，是
	private String completeFlag;		// 任务完成标记，&lsquo;0&rsquo;，未完成，&lsquo;1&rsquo;完成
	private String year;		// 年
	private String month;		// 月
	private String day;		// 日
	private List<OaTaskRecord> oaTaskRecordList = Lists.newArrayList();		// 子表列表
	private String receUserIds;   //接收人字符串拼接
	private String sendUserId;    //发送人id

	public OaTask() {
		super();
	}

	public OaTask(String id){
		super(id);
	}

	@Length(min=0, max=255, message="任务标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=2000, message="任务内容长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=2000, message="附件长度必须介于 0 和 2000 之间")
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
	@Length(min=0, max=1, message="是否是转发，0：否，1，是长度必须介于 0 和 1 之间")
	public String getForwardFlag() {
		return forwardFlag;
	}

	public void setForwardFlag(String forwardFlag) {
		this.forwardFlag = forwardFlag;
	}
	
	@Length(min=0, max=1, message="任务完成标记，&lsquo;0&rsquo;，未完成，&lsquo;1&rsquo;完成长度必须介于 0 和 1 之间")
	public String getCompleteFlag() {
		return completeFlag;
	}

	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}
	
	@Length(min=0, max=10, message="年长度必须介于 0 和 10 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=0, max=10, message="月长度必须介于 0 和 10 之间")
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	@Length(min=0, max=10, message="日长度必须介于 0 和 10 之间")
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public List<OaTaskRecord> getOaTaskRecordList() {
		return oaTaskRecordList;
	}

	public void setOaTaskRecordList(List<OaTaskRecord> oaTaskRecordList) {
		this.oaTaskRecordList = oaTaskRecordList;
	}

	public String getReceUserIds() {
		return receUserIds;
	}

	public void setReceUserIds(String receUserIds) {
		this.receUserIds = receUserIds;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
}