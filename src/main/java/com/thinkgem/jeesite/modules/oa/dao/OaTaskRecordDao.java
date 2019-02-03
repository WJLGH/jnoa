/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaTaskRecord;

/**
 * 人员任务回复详情DAO接口
 * @author ctt
 * @version 2019-01-10
 */
@MyBatisDao
public interface OaTaskRecordDao extends CrudDao<OaTaskRecord> {
    public int updateTsakinfo(OaTaskRecord oaTaskRecord);

    /**
    * @Description:    更新完成标记
    * @Author:         wfp
    * @CreateDate:     2019/1/15 13:16
    */
    public int updateFlag(OaTaskRecord oaTaskRecord);
}