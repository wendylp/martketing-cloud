/*************************************************
 * @功能简述: 获取系统标签组列表
 * @see MktApi
 * @author: nianjun
 * @version: 1.0 @date：2016-06-24
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.TaggroupSystemMenulistGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaggroupSystemMenulistGetServiceImpl implements TaggroupSystemMenulistGetService {

    private static final long TOP_LEVEL = -1L;

    @Autowired
    TaggroupDao TaggroupDao;

    @Override
    public BaseOutput getTaggroupSystemMenulist(String method, String userToken, Integer index, Integer size) {

        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        // 所有的数据 , 相当于一颗多叉树
        List<Taggroup> trees = TaggroupDao.selectList(null);

        // 获取所有的top级别的节点
        List<Taggroup> topTaggroups = getTopTaggroup(trees);
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 获取一级节点的子节点
        if (CollectionUtils.isEmpty(topTaggroups)) {
            return baseOutput;
        } else {
            for (int i = 0; i < topTaggroups.size(); i++) {
                Taggroup topTaggroup = topTaggroups.get(i);
                Map<String, Object> selectMap = new HashMap<>();
                List<Map<String, Object>> childNodes = new ArrayList<>();
                selectMap.put("select_name", topTaggroup.getName());
                selectMap.put("id", topTaggroup.getId());
                List<Taggroup> subNodes = getChildNodeByCurrentNode(topTaggroup, trees);
                selectMap.put("child_count", subNodes.size());
                for (Taggroup taggroup : subNodes) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("select_name", taggroup.getName());
                    map.put("id", taggroup.getId());
                    childNodes.add(map);
                }
                selectMap.put("child_nodes", childNodes);
                resultList.add(selectMap);
            }
        }

        baseOutput.getData().addAll(resultList);
        baseOutput.setTotalCount(resultList.size());

        return baseOutput;
    }

    // 获取所有顶级节点,即parentId为-1的节点
    private List<Taggroup> getTopTaggroup(List<Taggroup> trees) {
        List<Taggroup> resultList = new ArrayList<>();

        if (CollectionUtils.isEmpty(trees)) {
            return resultList;
        } else {
            for (Taggroup taggroup : trees) {
                if (taggroup.getParentGroupId() == TOP_LEVEL) {
                    resultList.add(taggroup);
                }
            }
        }

        return resultList;
    }

    // 根据当前节点获取所有子节点
    private List<Taggroup> getChildNodeByCurrentNode(Taggroup node, List<Taggroup> trees) {

        List<Taggroup> resultList = new ArrayList<>();

        if (node == null || CollectionUtils.isEmpty(trees)) {
            return resultList;
        }

        for (Taggroup taggroup : trees) {
            if (taggroup.getParentGroupId().equals(Long.valueOf(node.getId()))) {
                resultList.add(taggroup);
            }
        }

        return resultList;
    }

}
