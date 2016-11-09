package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialIn;

public interface SmsMaterialService {

	/**
	 * 增加素材
	 * @param smsMaterialIn
	 * @return
	 */
	public BaseOutput insertOrUpdateSmsMaterial(SmsMaterialIn smsMaterialIn);
	
	/**
	 * 删除素材
	 * @param id
	 * @return
	 */
	public BaseOutput deleteSmsMaterial(Integer id);
}
