package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactListDao;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.service.ImportContactsDataToMDataService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ImportContactsDataIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yunfeng on 2016-8-22.
 */
@Service
public class ImportContactsDataToMDataServiceImpl implements ImportContactsDataToMDataService{

    @Autowired
    private ContactTemplateDao contactTemplateDao;

    @Autowired
    private ContactListDao contactListDao;

    @Override
    public BaseOutput importContactsDataToMData(ImportContactsDataIn importContactsDataIn) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        Long contactId = importContactsDataIn.getContactId();
        if(contactId == null){
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
            baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR.getMsg());
            return baseOutput;
        }

        //Todo:1先从Template里选出keyList
        //Todo:2然后从keyblock表中构造出bitmap
        //Todo:3将bitmap等数据导入主数据
        //Todo:4从主数据算出id写到ContactList里面。


        return baseOutput;
    }

}
