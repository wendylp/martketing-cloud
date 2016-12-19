package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.mongo.MongoSystemCustomTagTreeDao;
import cn.rongcapital.mkt.po.mongodb.SystemCustomTagTree;
import cn.rongcapital.mkt.service.TagCustomTaxonomyShowSetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagCustomTaxonomyShowSetIn;

@Service
public class TagCustomTaxonomyShowSetServiceImpl implements TagCustomTaxonomyShowSetService {

    private static final Integer LEVEL_ONE = 1;
    private static final Boolean IS_SHOW = true;
    private static final Boolean NOT_SHOW = false;
    private static final int MAX_SIZE = 6;

    @Autowired
    MongoSystemCustomTagTreeDao systemCustomTagTreeDao;

    /**
     * 功能描述：设置系统标签一级分类优先显示接口
     * 
     * 接口：mkt.tag.custom.taxonomy.show.set
     * 
     * @param body
     * @param securityContext
     * @return
     * @Date 2016.12.13
     * @author shuiyangyang
     */
    @Override
    public BaseOutput tagCustomTaxonomyShowSet(TagCustomTaxonomyShowSetIn body, SecurityContext securityContext) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);



        List<String> tagTreeIdLists = body.getTagTreeId();

        if (CollectionUtils.isNotEmpty(tagTreeIdLists)) {
            // 重置is_show的状态
            systemCustomTagTreeDao.updateIsShowByLevel(LEVEL_ONE, NOT_SHOW);

            int size = tagTreeIdLists.size();
            size = size > MAX_SIZE ? MAX_SIZE : size;

            List<String> newTagTreeIdLists = new ArrayList<String>();
            for (int i = 0; i < size; i++) {
                newTagTreeIdLists.add(tagTreeIdLists.get(i));
            }

            // 更新数据
            int updateCount = systemCustomTagTreeDao.updateIsShowByLevelTagTreeTd(LEVEL_ONE, newTagTreeIdLists, IS_SHOW);
            result.setTotal(updateCount);
        }

        return result;
    }

}
