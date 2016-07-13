package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.CustomTagOriginalDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.enums.TaskNameEnum;
import cn.rongcapital.mkt.common.enums.TaskTypeEnum;
import cn.rongcapital.mkt.job.service.base.TaskManager;
import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.service.FileTagUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.FileTagUpdateIn;
import org.springframework.util.CollectionUtils;

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
    private CustomTagOriginalDataMapDao customTagOriginalDataMapDao;
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
                //Todo:1.这里还需要把status给扔进去，先将status置为1表示无效
                if(!addNewCustomTag(fileTagUpdateIn)){
                    baseOutput.setCode(ApiErrorCode.DB_ERROR.getCode());
                    baseOutput.setMsg("自定义标签名称重复，新建标签失败");
                    return baseOutput;
                }
                //Todo:2.将上传数据通过fileUnique选出不同的手机号
                List<String> dataIdentifierList = getOriginalIdentifierList(importDataHistory.getFileUnique(), fileType);
                //Todo:3.选择本次上传的customId，然后与上一步选出的数据唯一标识一起存入customTagOriginalMap表中，将status置为0
                List<Long> tagIds =  customTagDao.selectIdsByCustomTags(fileTagUpdateIn.getTag_names());
                if(!CollectionUtils.isEmpty(dataIdentifierList) && !CollectionUtils.isEmpty(tagIds)){
                    insertCustomTagOriginalDataMapping(fileType, dataIdentifierList, tagIds);
                }
            } else {
                baseOutput.setMsg("用户没有上传标签");
            }

            updateOriginalDataStatus(fileUnique, fileType);
            importDataHistory.setStatus(Byte.valueOf((byte)0));
            importDataHistory.setImportEndTime(new Date(System.currentTimeMillis()));
            importDataHistory.setName(getTaskName(fileType));
            importDataHistoryDao.updateById(importDataHistory);
        }else{
            baseOutput.setMsg("数据上传失败");
        }
        return baseOutput;
    }

    private String getTaskName(Integer fileType) {
        switch (fileType){
            case 1:
                return TaskNameEnum.POPULATION.getDescription();
            case 2:
                return TaskNameEnum.CUSTOMER_TAGS.getDescription();
            case 3:
                return TaskNameEnum.ARCH_POINT.getDescription();
            case 4:
                return TaskNameEnum.MEMBER.getDescription();
            case 5:
                return TaskNameEnum.LOGIN.getDescription();
            case 6:
                return TaskNameEnum.PAYMENT.getDescription();
            case 7:
                return TaskNameEnum.SHOPPING.getDescription();
        }
        return "异常任务";
    }

    private void insertCustomTagOriginalDataMapping(Integer fileType, List<String> dataIdentifierList, List<Long> tagIds) {
        for(Long tagId : tagIds){
            for(String  dataIdentifier: dataIdentifierList){
                CustomTagOriginalDataMap customTagOriginalDataMap = new CustomTagOriginalDataMap();
                customTagOriginalDataMap.setStatus(ApiConstant.CUSTOM_TAG_ORIGINAL_DATA_MAP_VALIDATE);
                customTagOriginalDataMap.setDataUniqueIdentifier(dataIdentifier);
                customTagOriginalDataMap.setTagId(tagId.intValue());
                customTagOriginalDataMap.setOriginalDataType(fileType);
                customTagOriginalDataMapDao.insert(customTagOriginalDataMap);
            }
        }
    }

    private boolean hasTagNames(FileTagUpdateIn fileTagUpdateIn) {
        if(fileTagUpdateIn.getTag_names() == null || fileTagUpdateIn.getTag_names().size() < 1){
            return false;
        }
        return true;
    }

    //1将上传上来的标签名称列表以及上一步选出的总人群数量插入到custom_tag表中
    private boolean addNewCustomTag(FileTagUpdateIn fileTagUpdateIn) {
        List<CustomTag> customTagList = new ArrayList<CustomTag>();
        for(String tagName : fileTagUpdateIn.getTag_names()){
            CustomTag customTag = new CustomTag();
            customTag.setName(tagName);
            Integer count = customTagDao.selectListCount(customTag);
            if(count != null && count > 0){
                return false;
            }
            customTag.setStatus(ApiConstant.CUSTOM_TAG_INVALIDATE);
            customTagList.add(customTag);
        }

        if(!CollectionUtils.isEmpty(customTagList)){
            for(CustomTag customTag : customTagList){
                customTagDao.insert(customTag);
            }
        }
        return true;
    }

    //2.根据fileUnique选出对应的original表中的idList
    //Todo:以后如果要修改mappingIdList修改这个方法即可
    private List<String> getOriginalIdentifierList(String fileUnique, Integer fileType) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("file_unique", fileUnique);
        List<String> identifierList = null;
        switch (fileType){
            case 1:
                identifierList = originalDataPopulationDao.selelctIdentifierListByFileUnique(paramMap);
                break;
            case 2:
                identifierList = originalDataCustomerTagsDao.selelctIdentifierListByFileUnique(paramMap);
                break;
            case 3:
                identifierList = originalDataArchPointDao.selelctIdentifierListByFileUnique(paramMap);
                break;
            case 4:
                identifierList = originalDataMemberDao.selelctIdentifierListByFileUnique(paramMap);
                break;
            case 5:
                identifierList = originalDataLoginDao.selelctIdentifierListByFileUnique(paramMap);
                break;
            case 6:
                identifierList = originalDataPaymentDao.selelctIdentifierListByFileUnique(paramMap);
                break;
            case 7:
                identifierList = originalDataShoppingDao.selelctIdentifierListByFileUnique(paramMap);
                break;
        }
        return identifierList;
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
