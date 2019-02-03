/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.finance.entity.FinRecord;
import com.thinkgem.jeesite.modules.finance.dao.FinRecordDao;

/**
 * 明细记录Service
 * @author wjl
 * @version 2019-02-02
 */
@Service
@Transactional(readOnly = true)
public class FinRecordService extends CrudService<FinRecordDao, FinRecord> {

	public FinRecord get(String id) {
		return super.get(id);
	}
	
	public List<FinRecord> findList(FinRecord finRecord) {
		return super.findList(finRecord);
	}
	
	public Page<FinRecord> findPage(Page<FinRecord> page, FinRecord finRecord) {
		return super.findPage(page, finRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(FinRecord finRecord) {
		super.save(finRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(FinRecord finRecord) {
		super.delete(finRecord);
	}
	
}