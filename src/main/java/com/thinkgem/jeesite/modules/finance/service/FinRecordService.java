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
 *
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

    /**
     * 获取账户的收支记录列表
     *
     * @author wjl
     * @date 2019/2/13 9:57
     */
    public List<FinRecord> findListByAccount(FinAccount finAccount) {
        return finRecordDao.findListByAccount(finAccount);
    }

    /**
     * 获取交易类型的汇总信息列表
     *
     * @author wjl
     * @date 2019/2/13 9:57
     */
    public List<FinRecord> getBusTypsList(FinRecord finRecord) {
        return finRecordDao.getBusTypsList(finRecord);
    }

    /**
     * 获取一个时间段之内的记录列表
     *
     * @author wjl
     * @date 2019/2/13 9:58
     */
    public List<FinRecord> findListBeginToEnd(FinRecord finRecord) {
        return finRecordDao.findListBeginToEnd(finRecord);
    }

    public Page<FinRecord> findPage(Page<FinRecord> page, FinRecord finRecord) {
        return super.findPage(page, finRecord);
    }

    /**
     * 添加一条记录信息 并且相应的修改账户的剩余金额
     *
     * @author wjl
     * @date 2019/2/13 9:55
     */
    @Transactional(readOnly = false)
    public void save(FinRecord finRecord, boolean isUpdate) {
        if (finRecord == null) {
            return;
        }
        if (isUpdate) {
            FinRecord oldRecord =  finRecordDao.get(finRecord);
            undoUpdateAccount(oldRecord);
        }
        //添加一条记录
        super.save(finRecord);
        //完成账户的剩余金额的修改
        String inId = finRecord.getInId();
        String outId = finRecord.getOutId();
        //记录金额的绝对值
        Double amount = Math.abs(Double.parseDouble(finRecord.getAmount()));
        if (inId != null && !"".equals(inId)) {
            finAccountService.updateAmountById(inId, amount, 1);
        }
        if (outId != null && !"".equals(outId)) {
            finAccountService.updateAmountById(outId, amount, -1);
        }
    }
    @Transactional(readOnly = false)
    public   void undoUpdateAccount(FinRecord oldRecord) {
        String inId = oldRecord.getInId();
        String outId = oldRecord.getOutId();
        Double amount = Math.abs(Double.parseDouble(oldRecord.getAmount()));
        if (inId != null && !"".equals(inId)) {
            finAccountService.updateAmountById(inId, amount, -1);
        }
        if (outId != null && !"".equals(outId)) {
            finAccountService.updateAmountById(outId, amount, 1);
        }
    }

    @Transactional(readOnly = false)
    public boolean delete(FinRecord finRecord) {
        super.delete(finRecord);
        return false;
    }

    /**
     * 获取 记录时间的汇总信息列表
     *
     * @author wjl
     * @date 2019/2/13 9:54
     */
    public List<FinRecord> getDateSumList(FinRecord finRecord) {
        return finRecordDao.getDateSumList(finRecord);
    }

    /**
     * 获取记录时间的汇总信息
     *
     * @author wjl
     * @date 2019/2/13 9:54
     */
    public FinRecord getDateSum(FinRecord finRecord) {
        return finRecordDao.getDateSum(finRecord);
    }

    /**
     * 获取  记录类型的汇总信息
     *
     * @author wjl
     * @date 2019/2/13 9:53
     */
    public FinRecord getAllInAndOut(FinRecord finRecord) {
        return finRecordDao.getAllInAndOut(finRecord);
    }

    /**
     * 根据多个查询条件获取列表
     *
     * @author wjl
     * @date 2019/2/13 9:52
     */
    public List<FinRecord> finListByEntity(FinRecord entityParams) {
        List<FinRecord> list = null;
        String acId = entityParams.getAcId();
        String acName = entityParams.getAcName();
        if (acId != null || acName != null) {
            FinAccount finAccount = new FinAccount();
            finAccount.setId(acId);
            finAccount.setAcName(acName);
            list = finRecordDao.findListByAccount(finAccount);
        } else {
            list = finRecordDao.finListByEntity(entityParams);
        }
        return list;
    }

    /**
     * 查询一条记录的具体信息
     *
     * @author wjl
     * @date 2019/2/13 9:52
     */
    public FinRecord getSingle(FinRecord finRecord) {
        return finRecordDao.findSingle(finRecord);
    }
}