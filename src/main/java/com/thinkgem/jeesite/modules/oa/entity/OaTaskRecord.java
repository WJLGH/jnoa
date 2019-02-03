/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 人员任务回复详情Entity
 * @author ctt
 * @version 2019-01-10
 */
public class OaTaskRecord extends DataEntity<OaTaskRecord> {
	
	private static final long serialVersionUID = 1L;
	private OaTask oaTask;		// 任务ID 父类
	private String title;		// 任务标题
	private String content;		// 任务内容
	private String files;		// 附件
	private User sendUser;		// 发送人
	private User receUser;		// 接受人
	private Date sendDate;		// 发送时间
	private String readFlag;		// 阅读标记
	private Date readDate;		// 阅读时间
	private String forwardFlag;		// 是否是转发
	private String completeFlag;		// 任务完成标记，&lsquo;0&rsquo;，未完成，&lsquo;1&rsquo;完成
	private String notFlag ;//未完成标记
	private String year;		// 年
	private String month;		// 月
	private String day;		// 日
	private List<OaTaskReply> oaTaskReplyList = Lists.newArrayList();		// 子表列表

	public OaTaskRecord() {
		super();
		this.notFlag = "3";
	}

	public OaTaskRecord(String id){
		super(id);
	}

	public OaTaskRecord(OaTask oaTask){
		this.oaTask = oaTask;
	}

	public String getNotFlag() {
		return notFlag;
	}

	public void setNotFlag(String notFlag) {
		this.notFlag = notFlag;
	}

	@Length(min=0, max=64, message="任务ID长度必须介于 0 和 64 之间")
	public OaTask getOaTask() {
		return oaTask;
	}

	public void setOaTask(OaTask oaTask) {
		this.oaTask = oaTask;
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
	
	public User getSendUser() {
		return sendUser;
	}

	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}
	
	public User getReceUser() {
		return receUser;
	}

	public void setReceUser(User receUser) {
		this.receUser = receUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	@Length(min=0, max=1, message="阅读标记长度必须介于 0 和 1 之间")
	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	
	@Length(min=0, max=1, message="是否是转发长度必须介于 0 和 1 之间")
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
	
	public List<OaTaskReply> getOaTaskReplyList() {
		return oaTaskReplyList;
	}

	public void setOaTaskReplyList(List<OaTaskReply> oaTaskReplyList) {
		this.oaTaskReplyList = oaTaskReplyList;
	}
}