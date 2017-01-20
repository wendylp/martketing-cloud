package cn.rongcapital.mkt.service;

import java.util.ArrayList;
import java.util.List;

import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by byf on 1/15/17.
 */
public interface CustomTagActionService {
    BaseOutput insertCustomTag();
    
    List<CustomTag> findCustomTagsByCategoryId(String categoryId);

    List<CustomTag> insertCustomTagListIntoDefaultCategory(ArrayList<String> customTags);

    List<CustomTag> findCustomTagTopList(Integer topType);
}
