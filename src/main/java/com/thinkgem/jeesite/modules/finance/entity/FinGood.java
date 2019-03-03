/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 明细记录Entity
 * @author wjl
 * @version 2019-03-03
 */
public class FinGood extends DataEntity<FinGood> {
	
	private static final long serialVersionUID = 1L;
	private FinRecord re;		// 是fin_record中的id 父类
	private String goodName;		// 商品名称
	private Double price;		// 单价
	private Double quantity;		// 数量
	private String unit;		// 单位
	private String supplier;		// 供应商
	private String spAddress;		// 供应地址
	private String buyer;		// 采购人
	
	public FinGood() {
		super();
	}

	public FinGood(String id){
		super(id);
	}

	public FinGood(FinRecord re){
		this.re = re;
	}

	@Length(min=0, max=36, message="是fin_record中的id长度必须介于 0 和 36 之间")
	public FinRecord getRe() {
		return re;
	}

	public void setRe(FinRecord re) {
		this.re = re;
	}
	
	@Length(min=0, max=100, message="商品名称长度必须介于 0 和 100 之间")
	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	@Length(min=0, max=20, message="单位长度必须介于 0 和 20 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=64, message="供应商长度必须介于 0 和 64 之间")
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	@Length(min=0, max=300, message="供应地址长度必须介于 0 和 300 之间")
	public String getSpAddress() {
		return spAddress;
	}

	public void setSpAddress(String spAddress) {
		this.spAddress = spAddress;
	}
	
	@Length(min=0, max=64, message="采购人长度必须介于 0 和 64 之间")
	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
}