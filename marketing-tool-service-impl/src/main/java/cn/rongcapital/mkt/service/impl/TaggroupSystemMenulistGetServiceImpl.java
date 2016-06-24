/*************************************************
 * @功能简述: 获取系统标签组列表
 * @see MktApi
 * @author: nianjun
 * @version: 1.0 @date：2016-06-24
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

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

    private static final int ZERO_LEVEL = 0;

    private static final long TOP_LEVEL = -1L;

    @Autowired
    TaggroupDao TaggroupDao;

    // @Override
    // public BaseOutput getTaggroupSystemMenulist(String method, String userToken, Integer index,
    // Integer size) {
    // BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
    // ApiErrorCode.SUCCESS.getMsg(),
    // ApiConstant.INT_ZERO, null);
    // Taggroup param = new Taggroup();
    // param.setStartIndex(index);
    // param.setPageSize(size);
    // List<Map<String, Object>> groupList = TaggroupDao.selectTaggroupSystemMenulist(param);
    // if (CollectionUtils.isNotEmpty(groupList)) {
    // baseOutput.setTotal(groupList.size());
    // for (Map<String, Object> groupMap : groupList) {
    // if (!groupMap.containsKey("tag_group_id")) {
    // groupMap.put("tag_group_id", "");
    // groupMap.put("tag_group_name", "");
    // }
    // if (!groupMap.containsKey("tag_group_pid")) {
    // groupMap.put("tag_group_pid", "");
    // groupMap.put("tag_group_pname", "");
    // }
    // baseOutput.getData().add(groupMap);
    // }
    // }
    //
    // return baseOutput;
    // }

    @Override
    public BaseOutput getTaggroupSystemMenulist(String method, String userToken, Integer index, Integer size) {

        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        List<Taggroup> taggroups = TaggroupDao.selectList(null);
        List<List<Taggroup>> rawResultList = new ArrayList<>();

        // 1. 先获取到所有的最底层的叶子节点.我们可以这样理解,有多少叶子节点,就有多少种组合
        List<Taggroup> leafNodes = getLeafNodes(taggroups);

        if (CollectionUtils.isEmpty(leafNodes)) {
            return baseOutput;
        } else {
            // 通过每一个叶子节点获取到所有的叶子节点到root的组合
            for (Taggroup leafNode : leafNodes) {
                List<Taggroup> leafChain = new ArrayList<>();
                leafChain = getGroupList(leafNode, taggroups);
                rawResultList.add(leafChain);
            }
        }

        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < rawResultList.size(); i++) {
            List<Taggroup> tmpList = rawResultList.get(i);
            StringBuilder selectionName = new StringBuilder();
            // list中保存的是从叶子节点到根节点的组合,要倒着取值,从根节点取到叶子节点
            for (int j = tmpList.size() - 1; j > -1; j--) {
                selectionName.append(tmpList.get(j).getName());
                if (j > 0) {
                    selectionName.append(" - ");
                }
            }

            resultList.add(selectionName.toString());
        }

        baseOutput.getData().addAll(resultList);
        baseOutput.setTotalCount(resultList.size());

        return baseOutput;
    }

    // 获取所有叶子节点
    private List<Taggroup> getLeafNodes(List<Taggroup> trees) {
        List<Taggroup> leafNodes = new ArrayList<>();
        if (CollectionUtils.isEmpty(trees)) {
            // 只有根节点
            return leafNodes;
        } else {
            for (Taggroup taggroup : trees) {
                if (taggroup.getLevel().equals(ZERO_LEVEL)) {
                    leafNodes.add(taggroup);
                }
            }
        }

        return leafNodes;
    }

    // 根据叶子节点获取该叶子节点到树根的整个序列
    private List<Taggroup> getGroupList(Taggroup leafNode, List<Taggroup> trees) {
        List<Taggroup> taggroups = new ArrayList<>();
        taggroups.add(leafNode);
        Taggroup firstParent = getParentNode(leafNode, trees);
        while (!firstParent.getParentGroupId().equals(TOP_LEVEL)) {
            taggroups.add(firstParent);
            firstParent = getParentNode(firstParent, trees);
        }
        taggroups.add(firstParent);

        return taggroups;
    }

    // 获取节点的父节点
    private Taggroup getParentNode(Taggroup leafNode, List<Taggroup> trees) {
        for (Taggroup taggroup : trees) {
            if (leafNode.getParentGroupId().compareTo(Long.valueOf(taggroup.getId())) == 0) {
                return taggroup;
            }
        }

        return null;
    }
}
