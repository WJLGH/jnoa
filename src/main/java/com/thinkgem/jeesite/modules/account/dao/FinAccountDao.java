/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.FinAccount;

/**
 * 财务账户DAO接口
 * @author wjl
 * @version 2019-02-03
 */
@MyBatisDao
public interface FinAccountDao extends CrudDao<FinAccount> {
	
}