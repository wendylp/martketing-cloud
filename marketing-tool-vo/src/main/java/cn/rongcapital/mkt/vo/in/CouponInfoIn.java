package cn.rongcapital.mkt.vo.in;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CouponInfoIn {

    @JsonProperty("id")
    private Long id;
    @NotEmpty
    @JsonProperty("title")
    private String title;
    @NotEmpty
    @JsonProperty("source_code")
    private String source_code;
    @JsonProperty("rule")
    private String rule;
    @NotNull
    @JsonProperty("stock_total")
    private Integer stock_total;
    @NotNull
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("channel_code")
    private String channel_code;
    @NotNull
    @JsonProperty("start_time")
    private Date start_time;
    @NotNull
    @JsonProperty("end_time")
    private Date end_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource_code() {
        return source_code;
    }

    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getStock_total() {
        return stock_total;
    }

    public void setStock_total(Integer stock_total) {
        this.stock_total = stock_total;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getChannel_code() {
        return channel_code;
    }

    public void setChannel_code(String channel_code) {
        this.channel_code = channel_code;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
    
}
