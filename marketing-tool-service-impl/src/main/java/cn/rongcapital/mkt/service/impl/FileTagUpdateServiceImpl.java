package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.OriginalDataArchPoint;
import cn.rongcapital.mkt.service.FileTagUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.FileTagUpdateIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-6-25.
 */
@Service
public class FileTagUpdateServiceImpl implements FileTagUpdateService {

    @Autowired
    private ImportDataHistoryDao importDataHistoryDao;
    @Autowired
    private CustomTagDao customTagDao;
    @Autowired
    private CustomTagMapDao customTagMapDao;
    @Autowired
    private OriginalDataPopulationDao originalDataPopulationDao;

    @Override
    public BaseOutput updateFileTag(FileTagUpdateIn fileTagUpdateIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        if(fileTagUpdateIn.getTag_names() == null && fileTagUpdateIn.getTag_names().size() <= 0){
            baseOutput.setMsg("用户没有上传标签");
            return baseOutput;
        }
        //0根据上传分fileUnique选出导入的人群数量
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("file_unique",fileTagUpdateIn.getFileUnique());
        List<Map<String,Object>> importRows = importDataHistoryDao.selectTotalRowsAndFileType(paramMap);
        //1将上传上来的标签名称列表以及上一步选出的总人群数量插入到custom_tag表中
        if(importRows != null && importRows.size() > 0){
            Integer legalRows = (Integer) importRows.get(0).get("legal_rows");
            Integer fileType = (Integer) importRows.get(0).get("file_type");
            List<Map<String,Object>> insertCustomTagList = new ArrayList<Map<String,Object>>();
            for(String tagName : fileTagUpdateIn.getTag_names()){
                Map<String,Object> insertMap = new HashMap<String,Object>();
                insertMap.put("name", tagName);
                insertMap.put("cover_audience_count",legalRows);
                insertCustomTagList.add(insertMap);
            }
            customTagDao.batchInsert(insertCustomTagList);
            //Todo:2.根据fileUnique选出对应的original表中的idList
            List<Long> mapIdList = null;
            switch (fileType){
                case 1:
                    mapIdList = originalDataPopulationDao.selelctIdListByFileUnique(paramMap);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
            //Todo:3将customName和idList插入到cumstomTagMapping表中
            List<Long> tagIds = customTagDao.selectIdsByCustomTags(fileTagUpdateIn.getTag_names());
            if(tagIds != null && tagIds.size() > 0 && mapIdList != null && mapIdList.size() > 0){
                List<Map<String,Object>> insertCustomTagMapList = new ArrayList<Map<String,Object>>();
                for(Long tagId : tagIds){
                    for(Long mapId : mapIdList){
                        Map<String,Object> insertCustomTagMapMap = new HashMap<String,Object>();
                        insertCustomTagMapMap.put("tag_id",tagId);
                        insertCustomTagMapMap.put("map_id",mapId);
                        insertCustomTagMapMap.put("type",fileType);
                        insertCustomTagMapList.add(insertCustomTagMapMap);
                    }
                }
                customTagMapDao.batchInsert(insertCustomTagMapList);
                baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
                baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            }
        }else{
            baseOutput.setMsg("数据上传失败");
            return baseOutput;
        }
        return baseOutput;
    }
}
