/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.finance.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.finance.entity.FinGood;
import com.thinkgem.jeesite.modules.finance.entity.FinRecord;

/**
 * 明细记录DAO接口
 * @author wjl
 * @version 2019-03-03
 */
@MyBatisDao
public interface FinGoodDao extends CrudDao<FinGood> {

    FinGood findByRecord(FinRecord finRecord);
}