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
	
}