/**
 * 
 */
package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
public interface WeixinAnalysisChdataSummaryService {
	BaseOutput getAnalysisChdataSummary(String wxName, String chCode, String startDate, String endDate);
}
