package cn.rongcapital.mkt.po;


import cn.rongcapital.mkt.po.base.BaseQuery;
import java.util.Date;
/*************************************************
 * @功能简述: 自定义标签与物料关系映射pojo
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2017/1/18
 * @复审人:
 *************************************************/
public class CustomTagMaterialMap extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private Long id;

    private String customTagId;		//标签ID

    private String customTagName;	//标签名称

    private String materialCode;	//物料code

    private String materialType;	//物料类型

    private Integer status;			//状态
    
    private Date createTime;		//创建时间

    private String reserve1;		//备用字段

    private String reserve2;		//备用字段

    private String reserve3;		//备用字段

    private String reserve4;		//备用字段
    
    
    public CustomTagMaterialMap() {
	}
    

	public CustomTagMaterialMap(String customTagId, String customTagName, String materialCode, String materialType,
			Integer status, Date createTime) {
		this.customTagId = customTagId;
		this.customTagName = customTagName;
		this.materialCode = materialCode;
		this.materialType = materialType;
		this.status = status;
		this.createTime = createTime;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomTagId() {
        return customTagId;
    }

    public void setCustomTagId(String customTagId) {
        this.customTagId = customTagId == null ? null : customTagId.trim();
    }

    public String getCustomTagName() {
        return customTagName;
    }

    public void setCustomTagName(String customTagName) {
        this.customTagName = customTagName == null ? null : customTagName.trim();
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType == null ? null : materialType.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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