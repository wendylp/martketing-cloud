package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */

public class CampaignInfo {
	private Integer id;
	private String name;
	private Date startTime;
	private Date endTime;
	private Integer totalCount;
	private List<CampaignItemInfo> items = new ArrayList<CampaignItemInfo>();

	public CampaignInfo() {
	}

	public CampaignInfo(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public CampaignInfo(Integer id, String name, Date startTime, Date endTime) {
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<CampaignItemInfo> getItems() {
		return items;
	}

	public void setItems(List<CampaignItemInfo> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "CampaignInfo [id=" + id + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", totalCount=" + totalCount + ", items=" + items + "]";
	}

}
