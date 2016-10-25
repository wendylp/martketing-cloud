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

    private String reserve1;//预留字段

    private String reserve2;

    private String reserve3;

    private String reserve4;
    
    public TagValueCount() {}

	public TagValueCount(String tagId, String tagName, String tagValue, Long valueCount) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = tagValue;
		this.valueCount = valueCount;
	}

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId == null ? null : tagId.trim();
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue == null ? null : tagValue.trim();
    }

    public Long getValueCount() {
        return valueCount;
    }

    public void setValueCount(Long valueCount) {
        this.valueCount = valueCount;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1 == null ? null : reserve1.trim();
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2 == null ? null : reserve2.trim();
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3 == null ? null : reserve3.trim();
    }

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4 == null ? null : reserve4.trim();
    }
}