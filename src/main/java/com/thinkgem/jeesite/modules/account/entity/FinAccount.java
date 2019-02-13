/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 财务账户Entity
 * @author wjl
 * @version 2019-02-03
 */
public class FinAccount extends DataEntity<FinAccount> {
	
	private static final long serialVersionUID = 1L;
	private String acType;		// 类型：
	private String cardNum;		// 卡号
	private String acName;		// 名称
	private String amount;		// 剩余金额
	private Double inAmount;	//收入金额
	private Double outAmount;	//支出金额
	
	public FinAccount() {
		super();
	}

	public FinAccount(String id){
		super(id);
	}

    public FinAccount(String acId, Double amount) {
		this.id = acId;
		this.amount = amount.toString();
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

	@Length(min=0, max=30, message="类型：长度必须介于 0 和 30 之间")
	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}
	
	@Length(min=0, max=30, message="卡号长度必须介于 0 和 30 之间")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}