package cn.rongcapital.mkt.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatMemberDao;
import cn.rongcapital.mkt.po.WechatAsset;
import cn.rongcapital.mkt.po.WechatAssetGroup;
import cn.rongcapital.mkt.po.WechatMember;
import cn.rongcapital.mkt.service.WechatAssetService;
import cn.rongcapital.mkt.vo.BaseOutput;
/**
 * 粉丝取消关注
 * @author lijinkai
 * @version 1.2  2016-9-9
 *
 */
@Service
public class WechatAssetServiceImpl implements WechatAssetService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private WechatAssetDao wechatAssetDao;
    
    @Autowired
    private WechatMemberDao wechatMemberDao;
    
    @Autowired
    private WechatAssetGroupDao wechatAssetGroupDao;
    
	/**
	 * 取消关注公众号
	 * @param wxCode 粉丝Code(wechat_member.wx_code)
	 * @param pubId 公众号ID(wechat_member.pub_id)
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput unfollow(String wxCode, String pubId) {
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		WechatMember wechatMember = new WechatMember();
		wechatMember.setWxCode(wxCode);
		wechatMember.setPubId(pubId);
		wechatMember.setStatus((byte) 0);
		List<WechatMember> wechatMemberList = wechatMemberDao.selectList(wechatMember);
		
		if(wechatMemberList != null && wechatMemberList.size() > 0){
			
			wechatMember = wechatMemberList.get(0);
			wechatMember.setStatus((byte) 1);//取消关注
			wechatMemberDao.updateById(wechatMember);
			
			//更新资产
			WechatAsset wechatAsset = new WechatAsset();
			wechatAsset.setWxAcct(wechatMember.getPubId());
			List<WechatAsset> wechatAssetList = wechatAssetDao.selectList(wechatAsset);
			
			if(wechatAssetList != null && wechatAssetList.size() > 0){
				
				wechatAsset = wechatAssetList.get(0);
				wechatAsset.setTotalCount(wechatAsset.getTotalCount() - 1);
				wechatAssetDao.updateById(wechatAsset);
			}
			
			//更新资产组
			WechatAssetGroup wechatAssetGroup = new WechatAssetGroup();
			wechatAssetGroup.setWxAcct(wechatMember.getPubId());
			wechatAssetGroup.setImportGroupId(Long.parseLong(wechatMember.getWxGroupId()));
			List<WechatAssetGroup> wechatAssetGroupList = wechatAssetGroupDao.selectList(wechatAssetGroup);
			
			if(wechatAssetGroupList != null && wechatAssetGroupList.size() > 0){
				
				wechatAssetGroup = wechatAssetGroupList.get(0);
				wechatAssetGroup.setMembers(wechatAssetGroup.getMembers() - 1);
				wechatAssetGroupDao.updateById(wechatAssetGroup);
			}

			logger.info(ApiErrorCode.SUCCESS.getMsg()+ "wxCode:　+"+wxCode + "pubId:" + pubId);
			
		}else{
			
			logger.info(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg()+ "wxCode:　+"+wxCode + "pubId:" + pubId);
			result.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
			return result;
			
		}

		return result;
	}

}
