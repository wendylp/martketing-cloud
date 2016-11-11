package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CityDic extends BaseQuery{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8954648249184622184L;

	private Integer cityId;

    private Integer provinceId;

    private Integer cityCode;

    private String cityNamec;

    private String cityNamee;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityNamec() {
        return cityNamec;
    }

    public void setCityNamec(String cityNamec) {
        this.cityNamec = cityNamec == null ? null : cityNamec.trim();
    }

    public String getCityNamee() {
        return cityNamee;
    }

    public void setCityNamee(String cityNamee) {
        this.cityNamee = cityNamee == null ? null : cityNamee.trim();
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
}