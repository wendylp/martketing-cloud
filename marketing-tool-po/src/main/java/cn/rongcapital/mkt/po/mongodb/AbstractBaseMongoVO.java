package cn.rongcapital.mkt.po.mongodb;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by ethan on 16/7/8.
 */
public abstract class AbstractBaseMongoVO {

    private String id;

    private Integer mid;

    @Field("md_type")
    private Integer mdType;

    @Field("mapping_keyid")
    private String mappingKeyId;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getMdType() {
        return mdType;
    }

    public void setMdType(Integer mdType) {
        this.mdType = mdType;
    }

    public String getMappingKeyId() {
        return mappingKeyId;
    }

    public void setMappingKeyId(String mappingKeyId) {
        this.mappingKeyId = mappingKeyId;
    }
}
