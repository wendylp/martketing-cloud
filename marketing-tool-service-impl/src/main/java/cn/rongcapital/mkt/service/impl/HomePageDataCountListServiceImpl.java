package cn.rongcapital.mkt.service.impl;

import static cn.rongcapital.mkt.common.enums.HomePageDataCountListEnum.DATA_PARTY;
import static cn.rongcapital.mkt.common.enums.HomePageDataCountListEnum.FINISHED_ACTIVITY;
import static cn.rongcapital.mkt.common.enums.HomePageDataCountListEnum.IN_PROGRESS_ACTIVITY;
import static cn.rongcapital.mkt.common.enums.HomePageDataCountListEnum.SEGMENTATION_HEAD;
import static cn.rongcapital.mkt.common.enums.HomePageDataCountListEnum.TAG;
import static cn.rongcapital.mkt.common.enums.HomePageDataCountListEnum.WECHAT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.HomePageDataCountListService;
import cn.rongcapital.mkt.vo.out.HomePageDataCountListOut;

@Service
public class HomePageDataCountListServiceImpl implements HomePageDataCountListService {

    private static final int CHILD_LEVEL = 2;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private CustomTagDao customeTagDao;

    @Autowired
    private TaggroupDao taggroupDao;

    @Autowired
    private SegmentationHeadDao segmentationHeadDao;

    @Autowired
    private CampaignHeadDao campaignHeadDao;

    @Override
    public List<HomePageDataCountListOut> getDataCountList() {

        List<HomePageDataCountListOut> dataCountList = new ArrayList<>();

        // 获取data_party的数据
        HomePageDataCountListOut dataPartyCountListObj = new HomePageDataCountListOut();
        // 暂时注释掉代码, 不记得是哪个二货告诉我这个取的数据是要原始数据总量了.以后不白纸黑字的需求落地怎么行呢.
        // int dataPartyCount = dataPartyDao.selectTotalOriginalCount();
        int dataPartyCount = dataPartyDao.selectListCount(null);
        dataPartyCountListObj.setId(DATA_PARTY.getId());
        dataPartyCountListObj.setCount(dataPartyCount);
        dataPartyCountListObj.setName(DATA_PARTY.getName());
        dataPartyCountListObj.setLinkName(DATA_PARTY.getLinkName());

        dataCountList.add(dataPartyCountListObj);

        // 获取标签的数据
        HomePageDataCountListOut tagCountListObj = new HomePageDataCountListOut();
        CustomTag paramCustomTag = new CustomTag();
        paramCustomTag.setStatus((byte)0);
        int customTagCount = customeTagDao.selectListCount(paramCustomTag);
        Taggroup paramTaggroup = new Taggroup();
        paramTaggroup.setLevel(CHILD_LEVEL);
        // 个人觉得以后计算叶子节点的算法不是这样的
        // int taggroupCount = taggroupDao.selectListCount(paramTaggroup);
        // 看到没, 代码被注释了, 计算叶子节点的算法改了 !!
        int taggroupCount = taggroupDao.selectSystemTagCount();
        int tagCount = customTagCount + taggroupCount;
        tagCountListObj.setId(TAG.getId());
        tagCountListObj.setCount(tagCount);
        tagCountListObj.setName(TAG.getName());
        tagCountListObj.setLinkName(TAG.getLinkName());

        dataCountList.add(tagCountListObj);

        // 获取可触达用户的数据
        HomePageDataCountListOut wechatCountListObj = new HomePageDataCountListOut();
        DataParty homePageDataCountListDataParty = new DataParty();
        homePageDataCountListDataParty.setMdType(8);
        int notNullMobileCount = dataPartyDao.selectNotNullMobile();
        int wechatMemberCount = dataPartyDao.selectListCount(homePageDataCountListDataParty);
        int reachableUserCount = notNullMobileCount + wechatMemberCount;
        wechatCountListObj.setId(WECHAT.getId());
        wechatCountListObj.setCount(reachableUserCount);
        wechatCountListObj.setName(WECHAT.getName());
        wechatCountListObj.setLinkName(WECHAT.getLinkName());

        dataCountList.add(wechatCountListObj);

        // 获取细分人员的数据
        HomePageDataCountListOut segmentationHeadCountListObj = new HomePageDataCountListOut();
        SegmentationHead paramSegmentationHead = new SegmentationHead();
        paramSegmentationHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSegmentationHead.setPublishStatus(ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH);
        int countNotPublish = segmentationHeadDao.selectListCount(paramSegmentationHead);
        paramSegmentationHead.setPublishStatus(ApiConstant.SEGMENT_PUBLISH_STATUS_PUBLISH);
        int countPublish = segmentationHeadDao.selectListCount(paramSegmentationHead);
        int countAll = countNotPublish + countPublish;
        segmentationHeadCountListObj.setId(SEGMENTATION_HEAD.getId());
        segmentationHeadCountListObj.setCount(countAll);
        segmentationHeadCountListObj.setName(SEGMENTATION_HEAD.getName());
        segmentationHeadCountListObj.setLinkName(SEGMENTATION_HEAD.getLinkName());

        dataCountList.add(segmentationHeadCountListObj);

        // 获取已结束活动的数据
        HomePageDataCountListOut finishedCountListObj = new HomePageDataCountListOut();
        CampaignHead paramCampaignHead = new CampaignHead();
        paramCampaignHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramCampaignHead.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_FINISH);
        int finishCount = campaignHeadDao.selectListCount(paramCampaignHead);
        finishedCountListObj.setId(FINISHED_ACTIVITY.getId());
        finishedCountListObj.setCount(finishCount);
        finishedCountListObj.setName(FINISHED_ACTIVITY.getName());
        finishedCountListObj.setLinkName(FINISHED_ACTIVITY.getLinkName());

        dataCountList.add(finishedCountListObj);

        // 获取进行活动的数据
        HomePageDataCountListOut inProgressCountListObj = new HomePageDataCountListOut();
        paramCampaignHead.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH);
        int publishCount = campaignHeadDao.selectListCount(paramCampaignHead);
        paramCampaignHead.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS);
        int inProgressCount = campaignHeadDao.selectListCount(paramCampaignHead);
        int totalInProgressCount = publishCount + inProgressCount;
        inProgressCountListObj.setId(IN_PROGRESS_ACTIVITY.getId());
        inProgressCountListObj.setCount(totalInProgressCount);
        inProgressCountListObj.setName(IN_PROGRESS_ACTIVITY.getName());
        inProgressCountListObj.setLinkName(IN_PROGRESS_ACTIVITY.getLinkName());

        dataCountList.add(inProgressCountListObj);


        return dataCountList;
    }

}
