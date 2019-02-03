/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 明细记录Entity
 * @author wjl
 * @version 2019-02-02
 */
public class FinRecord extends DataEntity<FinRecord> {
	
	private static final long serialVersionUID = 1L;
	private String reType;		// 记录类型：
	private String busType;		// 交易类型：
	private String amount;		// 交易金额
	private String description;		// 描述
	private String inId;		// 收入账户
	private String outId;		// 支出账户
	private Date noteDate;		// 记录时间
	private String dateStr; // 客户端发过来的日期字符串

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}


	public FinRecord() {
		super();
	}

	public FinRecord(String id){
		super(id);
	}

	@Length(min=0, max=30, message="记录类型：长度必须介于 0 和 30 之间")
	public String getReType() {
		return reType;
	}

	public void setReType(String reType) {
		this.reType = reType;
	}
	
	@Length(min=0, max=36, message="交易类型：长度必须介于 0 和 36 之间")
	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=200, message="描述长度必须介于 0 和 200 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=36, message="收入账户长度必须介于 0 和 36 之间")
	public String getInId() {
		return inId;
	}

	public void setInId(String inId) {
		this.inId = inId;
	}
	
	@Length(min=0, max=36, message="支出账户长度必须介于 0 和 36 之间")
	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}
	
}