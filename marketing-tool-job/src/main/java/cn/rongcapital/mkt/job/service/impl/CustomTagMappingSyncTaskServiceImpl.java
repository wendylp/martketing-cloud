package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-7-13.
 */
@Service
public class CustomTagMappingSyncTaskServiceImpl implements TaskService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPartyDao dataPartyDao;
    @Autowired
    private CustomTagOriginalDataMapDao customTagOriginalDataMapDao;
    @Autowired
    private CustomTagMapDao customTagMapDao;
    @Autowired
    private CustomTagDao customTagDao;

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
    private DataPopulationDao dataPopulationDao;
    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;
    @Autowired
    private DataArchPointDao dataArchPointDao;
    @Autowired
    private DataMemberDao dataMemberDao;
    @Autowired
    private DataLoginDao dataLoginDao;
    @Autowired
    private DataPaymentDao dataPaymentDao;
    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Autowired
    private KeyidMapBlockDao keyidMapBlockDao;

    @Override
    public void task(Integer taskId) {
        //1.选取状态有效的tagOriginal表中的数据
        CustomTagOriginalDataMap paramCustomTagOriginalDataMap = new CustomTagOriginalDataMap();
        paramCustomTagOriginalDataMap.setStatus(ApiConstant.CUSTOM_TAG_ORIGINAL_DATA_MAP_VALIDATE);
        paramCustomTagOriginalDataMap.setPageSize(Integer.MAX_VALUE);
        List<CustomTagOriginalDataMap> customTagOriginalDataMapList = customTagOriginalDataMapDao.selectList(paramCustomTagOriginalDataMap);

        //1.获取尚未完全同步的标签
        List<Integer> unhandledCustomTagIdList = customTagOriginalDataMapDao.selectDintinctUnhandleIdList(paramCustomTagOriginalDataMap);
        //2.打标签
        if(CollectionUtils.isEmpty(customTagOriginalDataMapList)) return;
        for(CustomTagOriginalDataMap customTagOriginalDataMap : customTagOriginalDataMapList){
            Integer keyId = null;
            List<KeyidMapBlock> uniqueFieldList = null;
            //Todo：在这里将符合条件的数据选出来，加入customTapMapList，然后将这些数据插入后，跟新相应的customTagOriginalTag的status值
            switch (customTagOriginalDataMap.getOriginalDataType()){
                case TypeConstant.POPULATION_TYPE:
                    //1.根据ID获取相应的Original表中对应的的数据
                    OriginalDataPopulation originalDataPopulation = new OriginalDataPopulation();
                    originalDataPopulation.setId(customTagOriginalDataMap.getOriginalDataId());
                    List<OriginalDataPopulation> originalDataPopulationList = originalDataPopulationDao.selectList(originalDataPopulation);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap, originalDataPopulationList)) break;
                    originalDataPopulation = originalDataPopulationList.get(0);
                    logger.info("tagInfo: " + originalDataPopulation.getBitmap());
                    if(originalDataPopulation.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;

                    //2.获取该条数据的唯一标识，然后将相应的值赋予相应的data表中
                    DataPopulation dataPopulation = new DataPopulation();
                    dataPopulation.setBitmap(originalDataPopulation.getBitmap());
                    uniqueFieldList = getUniqueFieldList(originalDataPopulation.getBitmap());
                    logger.info("tagInfo uniqueFieldList size: " + (uniqueFieldList == null? 0:uniqueFieldList.size()));
                    if(CollectionUtils.isEmpty(uniqueFieldList)) break;
                    copyOriginUniqueValueToData(originalDataPopulation, dataPopulation, uniqueFieldList);
                    logger.info("tagInfo copy unique field end");
                    //3根据唯一标识选取相应的dataPopulation
                    List<DataPopulation> dataPopulationList = dataPopulationDao.selectList(dataPopulation);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap,dataPopulationList)) break;
                    dataPopulation = dataPopulationList.get(0);
                    logger.info("tagInfo dataId" + dataPopulation.getId());
                    if(dataPopulation.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;
                    keyId = dataPopulation.getKeyid();
                    logger.info("tagInfo keyId:" + keyId);
                    break;
                case TypeConstant.CUSTOMER_TAGS_TYPE:
                    //1.根据ID获取相应的Original表中对应的的数据
                    OriginalDataCustomerTags originalDataCustomerTags = new OriginalDataCustomerTags();
                    originalDataCustomerTags.setId(customTagOriginalDataMap.getOriginalDataId());
                    List<OriginalDataCustomerTags> originalDataCustomerTagsList = originalDataCustomerTagsDao.selectList(originalDataCustomerTags);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap, originalDataCustomerTagsList)) break;
                    originalDataCustomerTags = originalDataCustomerTagsList.get(0);
                    logger.info("tagInfo: " + originalDataCustomerTags.getBitmap());
                    if(originalDataCustomerTags.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;

                    //2.获取该条数据的唯一标识，然后将相应的值赋予相应的data表中
                    DataCustomerTags dataCustomerTags = new DataCustomerTags();
                    dataCustomerTags.setBitmap(originalDataCustomerTags.getBitmap());
                    uniqueFieldList = getUniqueFieldList(originalDataCustomerTags.getBitmap());
                    logger.info("tagInfo uniqueFieldList size: " + (uniqueFieldList == null? 0:uniqueFieldList.size()));
                    if(CollectionUtils.isEmpty(uniqueFieldList)) break;
                    copyOriginUniqueValueToData(originalDataCustomerTags, dataCustomerTags, uniqueFieldList);
                    logger.info("tagInfo copy unique field end");
                    //3根据唯一标识选取相应的dataPopulation
                    List<DataCustomerTags> dataCustomerTagsList = dataCustomerTagsDao.selectList(dataCustomerTags);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap,dataCustomerTagsList)) break;
                    dataCustomerTags = dataCustomerTagsList.get(0);
                    logger.info("tagInfo dataId" + dataCustomerTags.getId());
                    if(dataCustomerTags.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;
                    keyId = dataCustomerTags.getKeyid();
                    logger.info("tagInfo keyId:" + keyId);
                    break;
                case TypeConstant.ARCH_POINT_TYPE:
                    //1.根据ID获取相应的Original表中对应的的数据
                    OriginalDataArchPoint originalDataArchPoint = new OriginalDataArchPoint();
                    originalDataArchPoint.setId(customTagOriginalDataMap.getOriginalDataId());
                    List<OriginalDataArchPoint> originalDataArchPointList = originalDataArchPointDao.selectList(originalDataArchPoint);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap, originalDataArchPointList)) break;
                    originalDataArchPoint = originalDataArchPointList.get(0);
                    logger.info("tagInfo: " + originalDataArchPoint.getBitmap());
                    if(originalDataArchPoint.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;

                    //2.获取该条数据的唯一标识，然后将相应的值赋予相应的data表中
                    DataArchPoint dataArchPoint = new DataArchPoint();
                    dataArchPoint.setBitmap(originalDataArchPoint.getBitmap());
                    uniqueFieldList = getUniqueFieldList(originalDataArchPoint.getBitmap());
                    logger.info("tagInfo uniqueFieldList size: " + (uniqueFieldList == null? 0:uniqueFieldList.size()));
                    if(CollectionUtils.isEmpty(uniqueFieldList)) break;
                    copyOriginUniqueValueToData(originalDataArchPoint, dataArchPoint, uniqueFieldList);
                    logger.info("tagInfo copy unique field end");
                    //3根据唯一标识选取相应的dataPopulation
                    List<DataArchPoint> dataArchPointList = dataArchPointDao.selectList(dataArchPoint);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap,dataArchPointList)) break;
                    dataArchPoint = dataArchPointList.get(0);
                    logger.info("tagInfo dataId" + dataArchPoint.getId());
                    if(dataArchPoint.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;
                    keyId = dataArchPoint.getKeyid();
                    logger.info("tagInfo keyId:" + keyId);
                    break;
                case TypeConstant.DATA_MEMBER_TYPE:
                    //1.根据ID获取相应的Original表中对应的的数据
                    OriginalDataMember originalDataMember = new OriginalDataMember();
                    originalDataMember.setId(customTagOriginalDataMap.getOriginalDataId());
                    List<OriginalDataMember> originalDataMemberList = originalDataMemberDao.selectList(originalDataMember);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap, originalDataMemberList)) break;
                    originalDataMember = originalDataMemberList.get(0);
                    logger.info("tagInfo: " + originalDataMember.getBitmap());
                    if(originalDataMember.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;

                    //2.获取该条数据的唯一标识，然后将相应的值赋予相应的data表中
                    DataMember dataMember = new DataMember();
                    dataMember.setBitmap(originalDataMember.getBitmap());
                    uniqueFieldList = getUniqueFieldList(originalDataMember.getBitmap());
                    logger.info("tagInfo uniqueFieldList size: " + (uniqueFieldList == null? 0:uniqueFieldList.size()));
                    if(CollectionUtils.isEmpty(uniqueFieldList)) break;
                    copyOriginUniqueValueToData(originalDataMember, dataMember, uniqueFieldList);
                    logger.info("tagInfo copy unique field end");
                    //3根据唯一标识选取相应的dataPopulation
                    List<DataMember> dataMemberList = dataMemberDao.selectList(dataMember);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap,dataMemberList)) break;
                    dataMember = dataMemberList.get(0);
                    logger.info("tagInfo dataId" + dataMember.getId());
                    if(dataMember.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;
                    keyId = dataMember.getKeyid();
                    logger.info("tagInfo keyId:" + keyId);
                    break;
                case TypeConstant.DATA_LOGIN_TYPE:
                    //1.根据ID获取相应的Original表中对应的的数据
                    OriginalDataLogin originalDataLogin = new OriginalDataLogin();
                    originalDataLogin.setId(customTagOriginalDataMap.getOriginalDataId());
                    List<OriginalDataLogin> originalDataLoginList = originalDataLoginDao.selectList(originalDataLogin);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap, originalDataLoginList)) break;
                    originalDataLogin = originalDataLoginList.get(0);
                    logger.info("tagInfo: " + originalDataLogin.getBitmap());
                    if(originalDataLogin.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;

                    //2.获取该条数据的唯一标识，然后将相应的值赋予相应的data表中
                    DataLogin dataLogin = new DataLogin();
                    dataLogin.setBitmap(originalDataLogin.getBitmap());
                    uniqueFieldList = getUniqueFieldList(originalDataLogin.getBitmap());
                    logger.info("tagInfo uniqueFieldList size: " + (uniqueFieldList == null? 0:uniqueFieldList.size()));
                    if(CollectionUtils.isEmpty(uniqueFieldList)) break;
                    copyOriginUniqueValueToData(originalDataLogin, dataLogin, uniqueFieldList);
                    logger.info("tagInfo copy unique field end");
                    //3根据唯一标识选取相应的dataPopulation
                    List<DataLogin> dataLoginList = dataLoginDao.selectList(dataLogin);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap,dataLoginList)) break;
                    dataLogin = dataLoginList.get(0);
                    logger.info("tagInfo dataId" + dataLogin.getId());
                    if(dataLogin.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;
                    keyId = dataLogin.getKeyid();
                    logger.info("tagInfo keyId:" + keyId);
                    break;
                case TypeConstant.DATA_PAYMENT_TYPE:
                    //1.根据ID获取相应的Original表中对应的的数据
                    OriginalDataPayment originalDataPayment = new OriginalDataPayment();
                    originalDataPayment.setId(customTagOriginalDataMap.getOriginalDataId());
                    List<OriginalDataPayment> originalDataPaymentList = originalDataPaymentDao.selectList(originalDataPayment);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap, originalDataPaymentList)) break;
                    originalDataPayment = originalDataPaymentList.get(0);
                    logger.info("tagInfo: " + originalDataPayment.getBitmap());
                    if(originalDataPayment.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;

                    //2.获取该条数据的唯一标识，然后将相应的值赋予相应的data表中
                    DataPayment dataPayment = new DataPayment();
                    dataPayment.setBitmap(originalDataPayment.getBitmap());
                    uniqueFieldList = getUniqueFieldList(originalDataPayment.getBitmap());
                    logger.info("tagInfo uniqueFieldList size: " + (uniqueFieldList == null? 0:uniqueFieldList.size()));
                    if(CollectionUtils.isEmpty(uniqueFieldList)) break;
                    copyOriginUniqueValueToData(originalDataPayment, dataPayment, uniqueFieldList);
                    logger.info("tagInfo copy unique field end");
                    //3根据唯一标识选取相应的dataPopulation
                    List<DataPayment> dataPaymentList = dataPaymentDao.selectList(dataPayment);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap,dataPaymentList)) break;
                    dataPayment = dataPaymentList.get(0);
                    logger.info("tagInfo dataId" + dataPayment.getId());
                    if(dataPayment.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;
                    keyId = dataPayment.getKeyid();
                    logger.info("tagInfo keyId:" + keyId);
                    break;

                case TypeConstant.DATA_SHOPPING_TYPE:
                    //1.根据ID获取相应的Original表中对应的的数据
                    OriginalDataShopping originalDataShopping = new OriginalDataShopping();
                    originalDataShopping.setId(customTagOriginalDataMap.getOriginalDataId());
                    List<OriginalDataShopping> originalDataShoppingList = originalDataShoppingDao.selectList(originalDataShopping);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap, originalDataShoppingList)) break;
                    originalDataShopping = originalDataShoppingList.get(0);
                    logger.info("tagInfo: " + originalDataShopping.getBitmap());
                    if(originalDataShopping.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;

                    //2.获取该条数据的唯一标识，然后将相应的值赋予相应的data表中
                    DataShopping dataShopping = new DataShopping();
                    dataShopping.setBitmap(originalDataShopping.getBitmap());
                    uniqueFieldList = getUniqueFieldList(originalDataShopping.getBitmap());
                    logger.info("tagInfo uniqueFieldList size: " + (uniqueFieldList == null? 0:uniqueFieldList.size()));
                    if(CollectionUtils.isEmpty(uniqueFieldList)) break;
                    copyOriginUniqueValueToData(originalDataShopping, dataShopping, uniqueFieldList);
                    logger.info("tagInfo copy unique field end");
                    //3根据唯一标识选取相应的dataPopulation
                    List<DataShopping> dataShoppingList = dataShoppingDao.selectList(dataShopping);
                    if (invalidErrorOriginalCusomerMapData(customTagOriginalDataMap,dataShoppingList)) break;
                    dataShopping = dataShoppingList.get(0);
                    logger.info("tagInfo dataId" + dataShopping.getId());
                    if(dataShopping.getStatus() != TypeConstant.DATA_SYNC_COMPLETE_MARK) break;
                    keyId = dataShopping.getKeyid();
                    logger.info("tagInfo keyId:" + keyId);
                    break;
                default:
                    break;
            }

            if(keyId != null){
                tagMainData(unhandledCustomTagIdList, customTagOriginalDataMap, keyId);
            }
        }
    }

    private <T,D>void copyOriginUniqueValueToData(T originalData, D data, List<KeyidMapBlock> uniqueFieldList) {
        for(KeyidMapBlock keyidMapBlock : uniqueFieldList){
            try {
                Field[] fields = originalData.getClass().getDeclaredFields();
                Field originalField = originalData.getClass().getDeclaredField(toCamelUpperCode(keyidMapBlock.getField()));
                originalField.setAccessible(true);
                Object obj = originalField.get(originalData);
                Field dataField = data.getClass().getDeclaredField(toCamelUpperCode(keyidMapBlock.getField()));
                dataField.setAccessible(true);
                dataField.set(data,obj);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private String toCamelUpperCode(String field) {
        if(!field.contains("_")) return field;
        String[] splitWord = field.split("_");
        StringBuffer resultBuffer = new StringBuffer();
        int index = 0;
        for(String word : splitWord){
            if(index == 0){
                resultBuffer.append(word);
                index++;
                continue;
            }
            word = word.replace(word.charAt(0), (char) (word.charAt(0) + ('A'-'a')));
            resultBuffer.append(word);
        }
        return resultBuffer.toString();
    }

    private <T> boolean invalidErrorOriginalCusomerMapData(CustomTagOriginalDataMap customTagOriginalDataMap, List<T> originalDataList) {
        if(CollectionUtils.isEmpty(originalDataList)){
            customTagOriginalDataMap.setStatus(ApiConstant.CUSTOM_TAG_ORIGINAL_DATA_MAP_INVALIDATE);
            customTagOriginalDataMapDao.updateById(customTagOriginalDataMap);
            return true;
        }
        return false;
    }

    private void tagMainData(List<Integer> unhandledCustomTagIdList, CustomTagOriginalDataMap customTagOriginalDataMap, Integer keyId) {
        for(Integer tagId : unhandledCustomTagIdList){
            CustomTagMap customTagMap = new CustomTagMap();
            customTagMap.setTagId(tagId);
            customTagMap.setMapId(keyId);
            Integer count = customTagMapDao.selectListCount(customTagMap);
            if(count > 0) continue;
            customTagMapDao.insert(customTagMap);
        }
        customTagOriginalDataMap.setStatus(ApiConstant.CUSTOM_TAG_ORIGINAL_DATA_MAP_INVALIDATE);
        customTagOriginalDataMapDao.updateById(customTagOriginalDataMap);
    }

    private List<KeyidMapBlock> getUniqueFieldList(String bitmap) {
        List<Integer> keyidListSequence = new LinkedList<Integer>();
        for(int index = 0; index < bitmap.length(); index++){
            if('1' == bitmap.charAt(index)){
                keyidListSequence.add(index+1);
            }
        }
        List<KeyidMapBlock> keyidMapBlockList = keyidMapBlockDao.selectListBySequenceList(keyidListSequence);
        return keyidMapBlockList;
    }

    interface TypeConstant{
       int POPULATION_TYPE = 1;
       int CUSTOMER_TAGS_TYPE = 2;
       int ARCH_POINT_TYPE = 3;
       int DATA_MEMBER_TYPE = 4;
       int DATA_LOGIN_TYPE = 5;
       int DATA_PAYMENT_TYPE = 6;
       int DATA_SHOPPING_TYPE = 7;

       int DATA_SYNC_COMPLETE_MARK = 2;
    }
}
