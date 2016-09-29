package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class SegmentationBody extends BaseQuery {
	private Integer id;

	private Integer headId;

	private String tagGroupId;

	private String tagId;

	private Byte exclude;

	private Integer groupIndex;

	private Byte status;

	private Date createTime;

	private Date updateTime;

	private Integer groupSeq;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHeadId() {
		return headId;
	}

	public void setHeadId(Integer headId) {
		this.headId = headId;
	}

	public String getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(String tagGroupId) {
		this.tagGroupId = tagGroupId == null ? null : tagGroupId.trim();
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId == null ? null : tagId.trim();
	}

	public Byte getExclude() {
		return exclude;
	}

	public void setExclude(Byte exclude) {
		this.exclude = exclude;
	}

	public Integer getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}
}
