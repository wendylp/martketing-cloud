package cn.rongcapital.mkt.service;


public interface CustomTagDeleteService {

    /**
     * mkt.tag.custom.delete
     * 
     * @功能简述 : 删除某个自定义标签
     * @author wangyi
     * @param method
     * @param userToken
     * @param tag_id
     * @return
     */
	public Object deleteCustomTag(String method, String userToken, Integer tag_id);

}
