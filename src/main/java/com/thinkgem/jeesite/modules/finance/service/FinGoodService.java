package com.thinkgem.jeesite.modules.finance.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.finance.dao.FinGoodDao;
import com.thinkgem.jeesite.modules.finance.entity.FinGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FinGoodService  extends CrudService<FinGoodDao,FinGood> {
    @Autowired
    FinGoodDao finGoodDao;
}
