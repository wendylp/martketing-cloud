package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignSwitchOut {
	
    private String nextItemId;
    
    private Byte drawType;//0:curveTriangle

    private String color;

    @JsonProperty("next_item_id")
    public String getNextItemId() {
		return nextItemId;
	}

	public void setNextItemId(String nextItemId) {
		this.nextItemId = nextItemId;
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
