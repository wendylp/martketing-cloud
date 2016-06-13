package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignSwitchIn {
	
    private Integer id;
    
    private String nextItemId;
    
    private Byte drawType;//0:curveTriangle

    private String color;

	@JsonProperty("next_itemId")
	public String getNextItemId() {
		return nextItemId;
	}

	public void setNextItemId(String nextItemId) {
		this.nextItemId = nextItemId;
	}

	@JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("draw_type")
    public Byte getDrawType() {
		return drawType;
	}

	public void setDrawType(Byte drawType) {
		this.drawType = drawType;
	}

	@JsonProperty("color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }
}
