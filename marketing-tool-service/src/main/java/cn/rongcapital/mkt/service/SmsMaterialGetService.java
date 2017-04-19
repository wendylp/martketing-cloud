package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by byf on 11/9/16.
 */
public interface SmsMaterialGetService {
	BaseOutput getSmsMaterialById(Long id);

	BaseOutput getSmsMaterialListByKeyword(Integer orgId, Boolean firsthand, String searchWord, Integer channelType, Integer smsType, Integer index,
			Integer size);

	BaseOutput getSmsMaterialCount(Integer orgId, Boolean firsthand, Integer channelType);

	/**
	 * @功能简述:查询未占用的短信素材
	 * 
     * @param:orgId 组织ID
     * @param:firsthand 是否是资源拥有者
	 * @param:channelType 短信通道类型
	 * @param:smsMaterialName 短信素材名称
	 * @return:BaseOutput
	 */
	BaseOutput getSmsMaterialByStatus(Integer orgId, Boolean firsthand, Integer channelType, String smsMaterialName);

	/**
	 * @功能简述:根据素材id查询选中的短信素材是否被占用
	 * 
	 * @param:smsMaterialId 短信素材id
	 * @return:BaseOutput
	 */
	BaseOutput getMaterialStatusByMaterialId(String smsMaterialId);
}
