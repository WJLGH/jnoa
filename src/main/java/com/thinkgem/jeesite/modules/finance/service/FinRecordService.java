/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.entity.FinAccount;
import com.thinkgem.jeesite.modules.account.service.FinAccountService;
import com.thinkgem.jeesite.modules.finance.dao.FinRecordDao;
import com.thinkgem.jeesite.modules.finance.entity.FinRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 明细记录Service
 * @author wjl
 * @version 2019-02-02
 */
@Service
@Transactional(readOnly = true)
public class FinRecordService extends CrudService<FinRecordDao, FinRecord> {

	@Autowired
	FinRecordDao finRecordDao;
	@Autowired
	FinAccountService finAccountService;
	public FinRecord get(String id) {
		return super.get(id);
	}
	
	public List<FinRecord> findList(FinRecord finRecord) {
		return super.findList(finRecord);
	}

	public List<FinRecord> findListByAccount(FinAccount finAccount) {
		return finRecordDao.findListByAccount(finAccount);
	}
	public List<FinRecord> getBusTypsList(FinRecord finRecord) {
		 return finRecordDao.getBusTypsList(finRecord);
	}

	public List<FinRecord> findListBeginToEnd(FinRecord finRecord) {
		return finRecordDao.findListBeginToEnd(finRecord);
	}
	public Page<FinRecord> findPage(Page<FinRecord> page, FinRecord finRecord) {
		return super.findPage(page, finRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(FinRecord finRecord) {
		if(finRecord == null) {
			return ;
		}

		//添加一条记录
		super.save(finRecord);

		//完成账户的剩余金额的修改
		String inId = finRecord.getInId();
		String outId = finRecord.getOutId();
		//记录金额的绝对值
		Double amount = Math.abs(Double.parseDouble(finRecord.getAmount()));
		if(inId != null && !"".equals(inId)) {
			finAccountService.updateAmountById(inId,amount,1);
		}
		if(outId != null && !"".equals(outId)) {
			finAccountService.updateAmountById(outId,amount,-1);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(FinRecord finRecord) {
		super.delete(finRecord);
	}

	public List<FinRecord> getDateSumList(FinRecord finRecord) {
		return finRecordDao.getDateSumList(finRecord);
	}
	public FinRecord getDateSum(FinRecord finRecord) {
		return finRecordDao.getDateSum(finRecord);
	}

    public FinRecord getReTypeSum(FinRecord finRecord) {
		return finRecordDao.getReTypeSum(finRecord);
    }

	public List<FinRecord> finListByEntity(FinRecord entityParams) {
		List<FinRecord> list = null;
		String acId = entityParams.getAcId();
		String acName = entityParams.getAcName();
		if(acId != null || acName!=null) {
			FinAccount finAccount = new FinAccount();
			finAccount.setId(acId);
			finAccount.setAcName(acName);
			list = finRecordDao.findListByAccount(finAccount);
		} else {
			list = finRecordDao.finListByEntity(entityParams);
		}
		return list;
	}
}