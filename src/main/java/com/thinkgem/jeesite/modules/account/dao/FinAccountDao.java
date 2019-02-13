/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.account.entity.FinAccount;

import java.util.List;

/**
 * 财务账户DAO接口
 * @author wjl
 * @version 2019-02-03
 */
@MyBatisDao
public interface FinAccountDao extends CrudDao<FinAccount> {

    /** 根据账户的id修改账户的剩余金额
     * @param finAccount
     */
    void updateAmountById(FinAccount finAccount);
    /**
     * 获取账户列表
     * @author wjl
     * @date 2019/2/13 10:00
     */
    List<FinAccount> findAllAccount();

    List<FinAccount> getAccountSum(FinAccount finAccount);
}