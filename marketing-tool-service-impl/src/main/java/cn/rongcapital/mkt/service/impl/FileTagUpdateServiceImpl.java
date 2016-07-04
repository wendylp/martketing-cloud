package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.OriginalDataArchPoint;
import cn.rongcapital.mkt.service.FileTagUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.FileTagUpdateIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private OriginalDataCustomerTagsDao originalDataCustomerTagsDao;
    @Autowired
    private OriginalDataArchPointDao originalDataArchPointDao;
    @Autowired
    private OriginalDataMemberDao originalDataMemberDao;
    @Autowired
    private OriginalDataLoginDao originalDataLoginDao;
    @Autowired
    private OriginalDataPaymentDao originalDataPaymentDao;
    @Autowired
    private OriginalDataShoppingDao originalDataShoppingDao;

    @Transactional
    @Override
    public BaseOutput updateFileTag(FileTagUpdateIn fileTagUpdateIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        if (validateTagNames(fileTagUpdateIn, baseOutput)) return baseOutput;

        String fileUnique = fileTagUpdateIn.getFileUnique();
        Integer fileType = Integer.valueOf(fileTagUpdateIn.getFileType());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("file_unique",fileTagUpdateIn.getFileUnique());
        List<Map<String,Object>> importRows = importDataHistoryDao.selectTotalRowsAndFileType(paramMap);         //0根据上传分fileUnique选出导入的人群数量

        if(importRows != null && importRows.size() > 0){
            addNewCustomTag(fileTagUpdateIn, importRows);             //将上传上来的标签名称列表以及上一步选出的总人群数量插入到custom_tag表中
            List<Long> mapIdList = getOriginalDataIdList(paramMap, fileType);      //2.根据fileUnique选出对应的original表中的idList
            //3将customName和idList插入到cumstomTagMapping表中
            List<Long> tagIds =  customTagDao.selectIdsByCustomTags(fileTagUpdateIn.getTag_names());
            if(tagIds != null && tagIds.size() > 0 && mapIdList != null && mapIdList.size() > 0){
                updateCustomTagMapping(fileType, mapIdList, tagIds);
                baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
                baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
            }
            // update status
            updateOriginalDataStatus(fileUnique, fileType);
        }else{
            baseOutput.setMsg("数据上传失败");
        }

        return baseOutput;
    }

    private void updateCustomTagMapping(Integer fileType, List<Long> mapIdList, List<Long> tagIds) {
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
    }

    private boolean validateTagNames(FileTagUpdateIn fileTagUpdateIn, BaseOutput baseOutput) {
        if(fileTagUpdateIn.getTag_names() == null && fileTagUpdateIn.getTag_names().size() <= 0){
            baseOutput.setMsg("用户没有上传标签");
            return true;
        }
        return false;
    }

    //1将上传上来的标签名称列表以及上一步选出的总人群数量插入到custom_tag表中
    private void addNewCustomTag(FileTagUpdateIn fileTagUpdateIn, List<Map<String, Object>> importRows) {
        Integer legalRows = (Integer) importRows.get(0).get("legal_rows");
        List<Map<String,Object>> insertCustomTagList = new ArrayList<Map<String,Object>>();
        for(String tagName : fileTagUpdateIn.getTag_names()){
            Map<String,Object> insertMap = new HashMap<String,Object>();
            insertMap.put("name", tagName);
            insertMap.put("cover_audience_count",legalRows);
            insertCustomTagList.add(insertMap);
        }
        customTagDao.batchInsert(insertCustomTagList);
    }

    //2.根据fileUnique选出对应的original表中的idList
    //Todo:以后如果要修改mappingIdList修改这个方法即可
    private List<Long> getOriginalDataIdList(Map<String, Object> paramMap, Integer fileType) {
        List<Long> mapIdList = null;
        switch (fileType){
            case 1:
                mapIdList = originalDataPopulationDao.selelctIdListByFileUnique(paramMap);
                break;
            case 2:
                mapIdList = originalDataCustomerTagsDao.selelctIdListByFileUnique(paramMap);
                break;
            case 3:
                mapIdList = originalDataArchPointDao.selelctIdListByFileUnique(paramMap);
                break;
            case 4:
                mapIdList = originalDataMemberDao.selelctIdListByFileUnique(paramMap);
                break;
            case 5:
                mapIdList = originalDataLoginDao.selelctIdListByFileUnique(paramMap);
                break;
            case 6:
                mapIdList = originalDataPaymentDao.selelctIdListByFileUnique(paramMap);
                break;
            case 7:
                mapIdList = originalDataShoppingDao.selelctIdListByFileUnique(paramMap);
                break;
        }
        return mapIdList;
    }

    private void updateOriginalDataStatus(String fileUnique, int fileType) {
        switch (fileType){
            case 1:
                originalDataPopulationDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                break;
            case 2:
                originalDataCustomerTagsDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                break;
            case 3:
                originalDataArchPointDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                break;
            case 4:
                originalDataMemberDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                break;
            case 5:
                originalDataLoginDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                break;
            case 6:
                originalDataPaymentDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                break;
            case 7:
                originalDataShoppingDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                break;
        }

    }
}
