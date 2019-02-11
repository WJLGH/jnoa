/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.dao.FinAccountDao;
import com.thinkgem.jeesite.modules.account.entity.FinAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 财务账户Service
 * @author wjl
 * @version 2019-02-03
 */
@Service
@Transactional(readOnly = true)
public class FinAccountService extends CrudService<FinAccountDao, FinAccount> {

	@Autowired
	FinAccountDao finAccountDao;
	public FinAccount get(String id) {
		return super.get(id);
	}
	
	public List<FinAccount> findList(FinAccount finAccount) {
		return super.findList(finAccount);
	}
	
	public Page<FinAccount> findPage(Page<FinAccount> page, FinAccount finAccount) {
		return super.findPage(page, finAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(FinAccount finAccount) {
		super.save(finAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(FinAccount finAccount) {
		super.delete(finAccount);
	}



	/**
	 * 根据账户ID 修改账户的剩余金额，
	 * flag 是标记变量：
	 * 	1  代表在原来账户基础上增加
	 * 	-1 代表在原来账户基础上减少
	 * @param acId
	 * @param amount
	 * @param flag
	 */
	@Transactional(readOnly = false)
    public void updateAmountById(String acId, Double amount, int flag) {
    	FinAccount finAccount = new FinAccount(acId,amount*flag);
    	finAccountDao.updateAmountById(finAccount);
    }
}