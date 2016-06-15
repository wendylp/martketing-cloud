package cn.rongcapital.mkt.po;

import java.math.BigDecimal;
import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class DataParty extends BaseQuery {
    private Integer id;

    private String mobile;

    private String name;

    private String gender;

    private Date birthday;

    private String provice;

    private String city;

    private String job;

    private BigDecimal monthlyIncome;

    private String memberLevel;

    private Integer memberPoints;

    private String source;

    private BigDecimal monthlyConsume;

    private Date lastLogin;

    private Boolean status;

    private Date createTime;

    private Date updateTime;

    private Integer batchId;

    private String mid;

    private Integer mdType;

    private String mappingKeyid;
    
    public DataParty(){}
    
    public DataParty(Integer index , Integer size){
        super(index, size);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice == null ? null : provice.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel == null ? null : memberLevel.trim();
    }

    public Integer getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public BigDecimal getMonthlyConsume() {
        return monthlyConsume;
    }

    public void setMonthlyConsume(BigDecimal monthlyConsume) {
        this.monthlyConsume = monthlyConsume;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public Integer getMdType() {
        return mdType;
    }

    public void setMdType(Integer mdType) {
        this.mdType = mdType;
    }

    public String getMappingKeyid() {
        return mappingKeyid;
    }

    public void setMappingKeyid(String mappingKeyid) {
        this.mappingKeyid = mappingKeyid == null ? null : mappingKeyid.trim();
    }
}
