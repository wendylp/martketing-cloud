package cn.rongcapital.mkt.material.coupon.vo.in;

import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MaterialCouponStockTotalIn extends BaseInput{
	
	@NotNull
	@JsonProperty("id")
	private Long id;
	
    @NotNull
    @JsonProperty("stock_total")
    private Integer stockTotal;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStockTotal() {
		return stockTotal;
	}

	public void setStockTotal(Integer stockTotal) {
		this.stockTotal = stockTotal;
	}
    
}
