/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.FinAccount;
import com.thinkgem.jeesite.modules.finance.entity.FinRecord;

import java.util.List;

/**
 * 明细记录DAO接口
 * @author wjl
 * @version 2019-02-02
 */
@MyBatisDao
public interface FinRecordDao extends CrudDao<FinRecord> {
	List<FinRecord> findListBeginToEnd(FinRecord finRecord);

    List<FinRecord> findListByAccount(FinAccount finAccount);

    List<FinRecord> findListByBusType(FinRecord finRecord);

    List<FinRecord> getBusTypsList(FinRecord finRecord);

    List<FinRecord> getDateSumList(FinRecord finRecord);
}