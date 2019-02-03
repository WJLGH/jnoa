/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.Date;

import com.thinkgem.jeesite.common.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.entity.OaNotifyRecord;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyDao;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyRecordDao;

/**
 * 通知通告Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OaNotifyService extends CrudService<OaNotifyDao, OaNotify> {

	@Autowired
	private OaNotifyDao oaNotifyDao;

	@Autowired
	private OaNotifyRecordDao oaNotifyRecordDao;

	public OaNotify get(String id) {
		OaNotify entity = dao.get(id);
		return entity;
	}
	
	/**
	 * 获取通知发送记录
	 * @param oaNotify
	 * @return
	 */
	public OaNotify getRecordList(OaNotify oaNotify) {
		oaNotify.setOaNotifyRecordList(oaNotifyRecordDao.findList(new OaNotifyRecord(oaNotify)));
		return oaNotify;
	}
	
	public Page<OaNotify> find(Page<OaNotify> page, OaNotify oaNotify) {
		oaNotify.setPage(page);
		page.setList(dao.findList(oaNotify));
		return page;
	}
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify) {
		return dao.findCount(oaNotify);
	}
	
	@Transactional(readOnly = false)
	public void save(OaNotify oaNotify) {
		super.save(oaNotify);
		
		// 更新发送接受人记录
		oaNotifyRecordDao.deleteByOaNotifyId(oaNotify.getId());
		if (oaNotify.getOaNotifyRecordList().size() > 0){
			oaNotifyRecordDao.insertAll(oaNotify.getOaNotifyRecordList());
		}
	}
	
	/**
	 * 更新阅读状态
	 */
	@Transactional(readOnly = false)
	public void updateReadFlag(OaNotify oaNotify) {
		OaNotifyRecord oaNotifyRecord = new OaNotifyRecord(oaNotify);
		oaNotifyRecord.setUser(oaNotifyRecord.getCurrentUser());
		oaNotifyRecord.setReadDate(new Date());
		oaNotifyRecord.setReadFlag("1");
		oaNotifyRecordDao.update(oaNotifyRecord);
	}

	/**
	 * @Description:    保存来自接口的通知
	 * @Author:         wfp
	 * @CreateDate:     2019/1/9 1:29
	 */
	@Transactional(readOnly = false)
	public void saveByInfc(OaNotify oaNotify, String userIds) {
		oaNotify.setId(IdGen.uuid());
		oaNotifyDao.insert(oaNotify);
		String []userId = userIds.split(",");
		if(userId.length>0){
			for(int i=0;i<userId.length;i++){
				OaNotifyRecord oaNotifyRecord = new OaNotifyRecord();
				oaNotifyRecord.setId(IdGen.uuid());
				oaNotifyRecord.setOaNotify(oaNotify);
				oaNotifyRecord.setTitle(oaNotify.getTitle());
				oaNotifyRecord.setContent(oaNotify.getContent());
				oaNotifyRecord.setFiles(oaNotify.getFiles());
				oaNotifyRecord.setReceUserId(userId[i]);
				oaNotifyRecord.setSendUserId(oaNotify.getCreateBy().getId());
				oaNotifyRecord.setUrgentFlag(oaNotify.getUrgentFlag());
				oaNotifyRecord.setImgPath1(oaNotify.getImgPath1());
				oaNotifyRecord.setImgPath2(oaNotify.getImgPath2());
				oaNotifyRecord.setImgPath3(oaNotify.getImgPath3());
				oaNotifyRecordDao.insert(oaNotifyRecord);
			}
		}

		// 更新发送接受人记录
//		oaNotifyRecordDao.deleteByOaNotifyId(oaNotify.getId());
//		if (oaNotify.getOaNotifyRecordList().size() > 0){
//			oaNotifyRecordDao.insertAll(oaNotify.getOaNotifyRecordList());
//		}
	}
}