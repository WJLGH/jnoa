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
	private String dept; //部门



	private String dateStr; // 客户端发过来的日期字符串
	private String startDate;
	private String endDate;
	private String year;
	private String month;
	private String day;
	private Double inAmount;
	private Double outAmount;
	private String acId;
	private String acName;

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public String getAcId() {
		return acId;
	}

	public void setAcId(String acId) {
		this.acId = acId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Double getInAmount() {
		return inAmount;
	}

	public void setInAmount(Double inAmount) {
		this.inAmount = inAmount;
	}

	public Double getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(Double outAmount) {
		this.outAmount = outAmount;
	}

	public void setYMD() {

		String[] split = dateStr.split("-");
		int cnt = split.length;
		switch (cnt) {
			case 1:
				year = split[0];
				break;
			case 2:
				year = split[0];
				month = split[1];
				break;
			case 3:
				year = split[0];
				month = split[1];
				day = split[2];
				break;
		}
	}
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
		setYMD();
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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