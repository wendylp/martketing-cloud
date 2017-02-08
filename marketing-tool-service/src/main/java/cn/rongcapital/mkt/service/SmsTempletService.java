package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.common.exception.NoWriteablePermissionException;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsTempletOut;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletCloneIn;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletIn;

public interface SmsTempletService {
	
	/**
	 * @param userId
	 * @param index
	 * @param size
	 * @param channelType
	 * @param type
	 * @param name
	 * @return
	 */
	public SmsTempletOut smsTempletList(String userId,Integer index,Integer size,String channelType,String type,String name,String content);
	
	/**
	 * @param smsTempletIn
	 * @return
	 */
	public BaseOutput saveOrUpdateSmsTemplet(SmsTempletIn smsTempletIn) throws NoWriteablePermissionException;
	
	/**
	 * 验证模板是否可以被删除或者编辑
	 * @param id
	 * @return
	 */
	public boolean smsTempletValidate(Integer id);
	
	/**
	 * 返回结果的组装
	 * @param dataList
	 * @return
	 */
	public List<SmsTemplet> setSmsTempletView(List<SmsTemplet> dataList);
	
	/**
	 * 短信模板克隆
	 * @param dataList
	 * @return
	 */
	public BaseOutput smsTempletClone(SmsTempletCloneIn clone);
	
	/**
	 * 当前资源查询分享给了哪些组织、及分享时间
	 * @param resourceId
	 * @param orgId
	 * @param oprType
	 * @return
	 */
	public BaseOutput getOrgs(long resourceId, long orgId, String oprType, int index, int size);
	
	/**
	 * 当前被分享资源查询是从哪个组织分享来的、及分享时间；
	 * @param resourceId
	 * @param orgId
	 * @param oprType
	 * @return
	 */
	public BaseOutput getOrg(long resourceId, long orgId, String oprType);
	
}
