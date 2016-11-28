package cn.rongcapital.mkt.po;
import cn.rongcapital.mkt.po.base.BaseQuery;

/*************************************************
 * @功能简述: 标签数量统计实体
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/10/24
 * @复审人:
 *************************************************/
public class TagValueCount extends BaseQuery{
	
	private static final long serialVersionUID = 1L;

    private String tagId;	//标签ID

    private String tagName;	//标签名称	

    private String tagValue;//标签值

    private Long valueCount;//标签值数量

    private String tagValueSeq;	//标签值顺序

    private String tagPath;	//标签路径

    private String isTag;	//是否是标签，0-标签，1-标签值
    
    private Integer searchMod;	//搜索标识
    
    
    public TagValueCount() {}


	public TagValueCount(String tagId, String tagName, String tagValue, Long valueCount, String tagValueSeq,
			String tagPath, String isTag, Integer searchMod) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = tagValue;
		this.valueCount = valueCount;
		this.tagValueSeq = tagValueSeq;
		this.tagPath = tagPath;
		this.isTag = isTag;
		this.searchMod = searchMod;
	}


	public String getTagId() {
		return tagId;
	}


	public void setTagId(String tagId) {
		this.tagId = tagId;
	}


	public String getTagName() {
		return tagName;
	}


	public void setTagName(String tagName) {
		this.tagName = tagName;
	}


	public String getTagValue() {
		return tagValue;
	}


	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}


	public Long getValueCount() {
		return valueCount;
	}


	public void setValueCount(Long valueCount) {
		this.valueCount = valueCount;
	}


	public String getTagValueSeq() {
		return tagValueSeq;
	}


	public void setTagValueSeq(String tagValueSeq) {
		this.tagValueSeq = tagValueSeq;
	}


	public String getTagPath() {
		return tagPath;
	}


	public void setTagPath(String tagPath) {
		this.tagPath = tagPath;
	}


	public String getIsTag() {
		return isTag;
	}


	public void setIsTag(String isTag) {
		this.isTag = isTag;
	}


	public Integer getSearchMod() {
		return searchMod;
	}


	public void setSearchMod(Integer searchMod) {
		this.searchMod = searchMod;
	}


    @Override
    public String toString() {
        return "TagValueCount [tagId=" + tagId + ", tagName=" + tagName + ", tagValue=" + tagValue
                        + ", valueCount=" + valueCount + ", tagValueSeq=" + tagValueSeq
                        + ", tagPath=" + tagPath + ", isTag=" + isTag + ", searchMod=" + searchMod
                        + "]";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((isTag == null) ? 0 : isTag.hashCode());
        result = prime * result + ((searchMod == null) ? 0 : searchMod.hashCode());
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
        result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
        result = prime * result + ((tagPath == null) ? 0 : tagPath.hashCode());
        result = prime * result + ((tagValue == null) ? 0 : tagValue.hashCode());
        result = prime * result + ((tagValueSeq == null) ? 0 : tagValueSeq.hashCode());
        result = prime * result + ((valueCount == null) ? 0 : valueCount.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TagValueCount other = (TagValueCount) obj;
        if (isTag == null) {
            if (other.isTag != null)
                return false;
        } else if (!isTag.equals(other.isTag))
            return false;
        if (searchMod == null) {
            if (other.searchMod != null)
                return false;
        } else if (!searchMod.equals(other.searchMod))
            return false;
        if (tagId == null) {
            if (other.tagId != null)
                return false;
        } else if (!tagId.equals(other.tagId))
            return false;
        if (tagName == null) {
            if (other.tagName != null)
                return false;
        } else if (!tagName.equals(other.tagName))
            return false;
        if (tagPath == null) {
            if (other.tagPath != null)
                return false;
        } else if (!tagPath.equals(other.tagPath))
            return false;
        if (tagValue == null) {
            if (other.tagValue != null)
                return false;
        } else if (!tagValue.equals(other.tagValue))
            return false;
        if (tagValueSeq == null) {
            if (other.tagValueSeq != null)
                return false;
        } else if (!tagValueSeq.equals(other.tagValueSeq))
            return false;
        if (valueCount == null) {
            if (other.valueCount != null)
                return false;
        } else if (!valueCount.equals(other.valueCount))
            return false;
        return true;
    }
	
	
	
}