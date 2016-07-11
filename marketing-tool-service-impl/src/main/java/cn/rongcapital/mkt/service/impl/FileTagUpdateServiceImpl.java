package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.enums.TaskNameEnum;
import cn.rongcapital.mkt.common.enums.TaskTypeEnum;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.dao.ImportDataHistoryDao;
import cn.rongcapital.mkt.dao.OriginalDataArchPointDao;
import cn.rongcapital.mkt.dao.OriginalDataCustomerTagsDao;
import cn.rongcapital.mkt.dao.OriginalDataLoginDao;
import cn.rongcapital.mkt.dao.OriginalDataMemberDao;
import cn.rongcapital.mkt.dao.OriginalDataPaymentDao;
import cn.rongcapital.mkt.dao.OriginalDataPopulationDao;
import cn.rongcapital.mkt.dao.OriginalDataShoppingDao;
import cn.rongcapital.mkt.job.service.base.TaskManager;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.service.FileTagUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.FileTagUpdateIn;

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
    @Autowired
    private TaskManager taskManager;
    
    @Transactional
    @Override
    public BaseOutput updateFileTag(FileTagUpdateIn fileTagUpdateIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
        String fileUnique = fileTagUpdateIn.getFileUnique();
        ImportDataHistory importDataHistory = importDataHistoryDao.queryByFileUnique(fileUnique);
        Integer fileType = importDataHistory.getFileType();
        Integer legalRows = importDataHistory.getLegalRows();
        if(legalRows != null && legalRows.intValue() > 0){
            if (hasTagNames(fileTagUpdateIn)) {
                addNewCustomTag(fileTagUpdateIn, legalRows);             //将上传上来的标签名称列表以及上一步选出的总人群数量插入到custom_tag表中
                List<Long> mapIdList = getOriginalDataIdList(importDataHistory.getFileUnique(), fileType);      //2.根据fileUnique选出对应的original表中的idList
                //3将customName和idList插入到cumstomTagMapping表中
                List<Long> tagIds =  customTagDao.selectIdsByCustomTags(fileTagUpdateIn.getTag_names());
                if(tagIds != null && tagIds.size() > 0 && mapIdList != null && mapIdList.size() > 0){
                    insertCustomTagMapping(fileType, mapIdList, tagIds);
                }
            } else {
                baseOutput.setMsg("用户没有上传标签");
            }

            updateOriginalDataStatus(fileUnique, fileType);
            importDataHistory.setStatus(Byte.valueOf((byte)0));
            importDataHistory.setImportEndTime(new Date(System.currentTimeMillis()));
            importDataHistoryDao.updateById(importDataHistory);
        }else{
            baseOutput.setMsg("数据上传失败");
        }

        return baseOutput;
    }

    private void insertCustomTagMapping(Integer fileType, List<Long> mapIdList, List<Long> tagIds) {
        List<Map<String,Object>> insertCustomTagMapList = new ArrayList<Map<String,Object>>();
        for(Long tagId : tagIds){
            for(Long mapId : mapIdList){
                Map<String,Object> insertCustomTagMapMap = new HashMap<String,Object>();
                insertCustomTagMapMap.put("tag_id",tagId);
                insertCustomTagMapMap.put("map_id",mapId);
                insertCustomTagMapMap.put("type","3");
                insertCustomTagMapList.add(insertCustomTagMapMap);
            }
        }
        customTagMapDao.batchInsert(insertCustomTagMapList);
    }

    private boolean hasTagNames(FileTagUpdateIn fileTagUpdateIn) {
        if(fileTagUpdateIn.getTag_names() == null || fileTagUpdateIn.getTag_names().size() < 1){
            return false;
        }
        return true;
    }

    //1将上传上来的标签名称列表以及上一步选出的总人群数量插入到custom_tag表中
    private void addNewCustomTag(FileTagUpdateIn fileTagUpdateIn, Integer legalRows) {
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
    private List<Long> getOriginalDataIdList(String fileUnique, Integer fileType) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("file_unique", fileUnique);
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
                taskManager.initFrontTask(TaskNameEnum.POPULATION, "originalDataPopulationServiceImpl", TaskTypeEnum.DISPLAY);
                break;
            case 2:
                originalDataCustomerTagsDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                taskManager.initFrontTask(TaskNameEnum.CUSTOMER_TAGS, "originalDataCustomTagScheduleServiceImpl", TaskTypeEnum.DISPLAY);
                break;
            case 3:
                originalDataArchPointDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                taskManager.initFrontTask(TaskNameEnum.ARCH_POINT, "originalDataArchPointScheduleServiceImpl", TaskTypeEnum.DISPLAY);
                break;
            case 4:
                originalDataMemberDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                taskManager.initFrontTask(TaskNameEnum.MEMBER, "originalDataMemberScheduleServiceImpl", TaskTypeEnum.DISPLAY);
                break;
            case 5:
                originalDataLoginDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                taskManager.initFrontTask(TaskNameEnum.LOGIN, "originalDataLoginScheduleServiceImpl", TaskTypeEnum.DISPLAY);
                break;
            case 6:
                originalDataPaymentDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                taskManager.initFrontTask(TaskNameEnum.PAYMENT, "originalDataPaymentScheduleServiceImpl", TaskTypeEnum.DISPLAY);
                break;
            case 7:
                originalDataShoppingDao.updateStatusByFileUnique(fileUnique, StatusEnum.ACTIVE.getStatusCode());
                taskManager.initFrontTask(TaskNameEnum.SHOPPING, "originalDataShoppingScheduleServiceImpl", TaskTypeEnum.DISPLAY);
                break;
        }
    }
}
