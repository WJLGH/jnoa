/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.account.entity.FinAccount;
import com.thinkgem.jeesite.modules.account.dao.FinAccountDao;

/**
 * 财务账户Service
 * @author wjl
 * @version 2019-02-03
 */
@Service
@Transactional(readOnly = true)
public class FinAccountService extends CrudService<FinAccountDao, FinAccount> {

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
	
}