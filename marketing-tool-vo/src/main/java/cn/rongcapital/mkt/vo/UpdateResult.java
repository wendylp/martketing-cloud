package cn.rongcapital.mkt.vo;

@javax.xml.bind.annotation.XmlRootElement
public class UpdateResult extends BaseOutput{
	
	UpdateResult(){}
	
	public UpdateResult(int code, String msg){
	  super(code,msg);
	}
  
}

