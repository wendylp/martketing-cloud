package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.service.TagSystemFuzzyListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.TagSystemFuzzyListGetOut;

@Service
public class TagSystemFuzzyListGetServiceImpl implements TagSystemFuzzyListGetService {

    @Autowired
    private TagValueCountDao tagValueCountDao;

    /**
     * 根据页面输入值模糊查询标签，返回标签或者标签值 （全路径的标签或者标签值《带有标签类型：标签值，标签》分页
     * 
     * 接口：mkt.tag.system.fuzzy.list.get
     * 
     * @param tagName
     * @param index
     * @param size
     * @return
     * @author shuiyangyang
     * @date 2016-11-11
     */
    @Override
    public BaseOutput getTagSystemFuzzyList(String tagName, String choiceShow, Integer index, Integer size) {
        
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
        
        TagValueCount tagValueCountSelect = new TagValueCount();
        tagValueCountSelect.setTagValue(tagName);
        // choiceShow  0:返回标签值     1:返回标签名         其他:返回标签名和标签值
        if(StringUtils.isNotBlank(choiceShow) && (choiceShow.equals("0") || choiceShow.equals("1"))) {
            tagValueCountSelect.setIsTag(choiceShow);
        }
        tagValueCountSelect.setStartIndex((index - 1) * size);
        tagValueCountSelect.setPageSize(size);
        // 查询
        List<TagValueCount> tagValueCountLists =
                        tagValueCountDao.selectFuzzyTagValue(tagValueCountSelect);
        if (tagValueCountLists != null && tagValueCountLists.size() > 0) {
            // 设置数量
            result.setTotal(tagValueCountLists.size());
            for (TagValueCount tagValueCountList : tagValueCountLists) {
                // 设置输出
                TagSystemFuzzyListGetOut tagSystemFuzzyListGetOut =new TagSystemFuzzyListGetOut(tagValueCountList.getTagId(),
                                tagValueCountList.getTagName(), tagValueCountList.getTagValue(),
                                tagValueCountList.getTagPath(), tagValueCountList.getIsTag(),
                                tagValueCountList.getSearchMod(), tagValueCountList.getTagValueSeq());
                result.getData().add(tagSystemFuzzyListGetOut);
            }
            
            // 设置总数
            result.setTotalCount(tagValueCountDao.selectFuzzyTagValueCount(tagValueCountSelect));
        }
        return result;
    }

}
