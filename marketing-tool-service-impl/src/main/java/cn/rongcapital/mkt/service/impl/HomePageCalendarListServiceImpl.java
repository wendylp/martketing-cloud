package cn.rongcapital.mkt.service.impl;

import static cn.rongcapital.mkt.common.enums.CampaignHeadStatusEnum.CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.CampaignHeadStatusEnum;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.HomePageCalendarListService;
import cn.rongcapital.mkt.vo.out.HomePageCalendarData;
import cn.rongcapital.mkt.vo.out.HomePageCalendarListOut;

@Service
public class HomePageCalendarListServiceImpl implements HomePageCalendarListService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CampaignHeadDao campaignHeadDao;

	@Override
	public HomePageCalendarListOut getCalendarList(String date) {
		HomePageCalendarListOut result = new HomePageCalendarListOut();
		List<HomePageCalendarData> homePageCalendarDatas = new ArrayList<>();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// Calendar now = Calendar.getInstance();
			// Calendar startTime = Calendar.getInstance();
			// Calendar endTime = Calendar.getInstance();
			//
			// if (!StringUtils.isEmpty(date)) {
			// try {
			// now.setTime(simpleDateFormat.parse(date));
			// } catch (ParseException e) {
			// logger.error("活动日历传送了错误的格式 : {}", date);
			// }
			// }

			// 初始化下开始结束时间
			// initParamTime(startTime, endTime, now);
			// Map<String, Date> paramMap = new HashMap<>();
			// paramMap.put("startTime", startTime.getTime());
			// paramMap.put("endTime", endTime.getTime());
			// List<CampaignHead> campaignHeads =
			// campaignHeadDao.selectInProgressandPrepareStatusCampaignHead(paramMap);

			// wangweiqiang update 2016-09-18
			List<CampaignHead> campaignHeads = campaignHeadDao.selectCampaignHeadListBySearchDate(date);

			if (!CollectionUtils.isEmpty(campaignHeads)) {
				for (CampaignHead campaignHead : campaignHeads) {
					HomePageCalendarData paramHomePageCalendarData = new HomePageCalendarData();
					if (campaignHead.getCreateTime() == null) {
						continue;
					}
					paramHomePageCalendarData.setActiveDay(simpleDateFormat.format(campaignHead.getCreateTime()));
					paramHomePageCalendarData.setStatus(campaignHead.getPublishStatus());
					paramHomePageCalendarData.setStatusDescription(
							CampaignHeadStatusEnum.getStatusByCode(campaignHead.getPublishStatus()));

					homePageCalendarDatas.add(paramHomePageCalendarData);
				}
			}

			homePageCalendarDatas = filterCalendarList(homePageCalendarDatas);
			result.setCalendarData(homePageCalendarDatas);
			result.setToday(simpleDateFormat.format(new Date()));
		} catch (Exception e) {
			logger.error("统计出当月日历日被客户标记当月定时的活动，按启动时间算方法出现异常:" + e.getMessage());
		}

		return result;
	}

	// 将开始结束时间分别定为本月开始时间(本月1号0点0分0秒),下个月的开始时间(下月1号0点0分0秒)
	private void initParamTime(Calendar startTime, Calendar endTime, Calendar now) {
		startTime.setTime(now.getTime());
		endTime.setTime(now.getTime());

		startTime.set(startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), 1, 0, 0, 0);
		endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
		startTime.set(Calendar.MILLISECOND, 0);
		endTime.set(Calendar.MILLISECOND, 0);
	}

	// 将数据按日期排序, 同一天有运行中与发布的数据, 优先显示运行中的数据
	private List<HomePageCalendarData> filterCalendarList(List<HomePageCalendarData> homePageCalendarDatas) {
		// 无数据则返回
		if (CollectionUtils.isEmpty(homePageCalendarDatas)) {
			return new ArrayList<>();
		}

		// 按日期排序
		Map<String, HomePageCalendarData> resultMap = new HashMap<>();
		for (HomePageCalendarData homePageCalendarData : homePageCalendarDatas) {
			// 正在执行的数据优先显示
			HomePageCalendarData tmpHomePageCalendarData = resultMap.get(homePageCalendarData.getActiveDay());
			if (tmpHomePageCalendarData == null) {
				resultMap.put(homePageCalendarData.getActiveDay(), homePageCalendarData);
			} else if (tmpHomePageCalendarData != null
					&& homePageCalendarData.getStatus() == CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS.getCode()) {
				resultMap.remove(homePageCalendarData.getActiveDay());
				resultMap.put(homePageCalendarData.getActiveDay(), homePageCalendarData);
			}
		}

		Collection<HomePageCalendarData> finalResultCollection = resultMap.values();

		homePageCalendarDatas = new ArrayList<>();
		homePageCalendarDatas.addAll(finalResultCollection);

		return homePageCalendarDatas;
	}

}
