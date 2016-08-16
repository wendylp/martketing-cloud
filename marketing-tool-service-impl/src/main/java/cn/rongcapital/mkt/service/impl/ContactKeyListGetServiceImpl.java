package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ContactTemplateDao;
import cn.rongcapital.mkt.dao.DefaultContactTemplateDao;
import cn.rongcapital.mkt.po.ContactTemplate;
import cn.rongcapital.mkt.po.DefaultContactTemplate;
import cn.rongcapital.mkt.service.ContactKeyListGetService;
import cn.rongcapital.mkt.vo.out.ContactKeyOutput;
import cn.rongcapital.mkt.vo.out.GetContactKeyListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Yunfeng on 2016-8-15.
 */
@Service
public class ContactKeyListGetServiceImpl implements ContactKeyListGetService{

    //Todo: 1.这块需要分两种情况,contactId为null则代表为创建接口
    //Todo: 2.contactId不为null则代表编辑接口。

    @Autowired
    private DefaultContactTemplateDao defaultContactTemplateDao;

    @Autowired
    private ContactTemplateDao contactTemplateDao;

    @Override
    public GetContactKeyListOutput getContactKeyList(Integer contactId) {

        GetContactKeyListOutput getContactKeyListOutput = new GetContactKeyListOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO);
        DefaultContactTemplate defaultContactTemplate = new DefaultContactTemplate();
        defaultContactTemplate.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<DefaultContactTemplate> defaultContactTemplateList = defaultContactTemplateDao.selectList(defaultContactTemplate);

        if(contactId != null){
            //将默认模板和现在数据库中已有的模板做对比返回给前端
            ContactTemplate contactTemplate = new ContactTemplate();
            contactTemplate.setContactId(contactId);
            List<ContactTemplate> contactTemplateList = contactTemplateDao.selectList(contactTemplate);

            //获取默认模板
            if(!CollectionUtils.isEmpty(contactTemplateList)){
                getContactKeyListOutput.setKeyList(contactTemplateList.get(0).getKeyList());
                for(DefaultContactTemplate defaultContactTemplate1 : defaultContactTemplateList){
                    for(ContactTemplate contactTemplate1 : contactTemplateList){
                        if(defaultContactTemplate1.getFieldCode().equals(contactTemplate1.getFieldCode())){
                            defaultContactTemplate1.setIsSelected(contactTemplate1.getSelected());
                            defaultContactTemplate1.setIsChecked(contactTemplate1.getIschecked().byteValue());
                            defaultContactTemplate1.setIsRequired(contactTemplate1.getRequired().byteValue());
                            defaultContactTemplate1.setStartIndex(contactTemplate1.getIndex());
                            break;
                        }
                    }
                }
            }
        }

        //将默认模板返回给前端
        for (DefaultContactTemplate defaultTemplate : defaultContactTemplateList){
            ContactKeyOutput contactKeyOutput = new ContactKeyOutput();
            contactKeyOutput.setFieldName(defaultTemplate.getFieldName());
            contactKeyOutput.setFieldCode(defaultTemplate.getFieldCode());
            contactKeyOutput.setIsChecked(Integer.valueOf(defaultTemplate.getIsChecked()));
            contactKeyOutput.setSelected(Integer.valueOf(defaultTemplate.getIsSelected()));
            contactKeyOutput.setRequired(Integer.valueOf(defaultTemplate.getIsRequired()));
            contactKeyOutput.setIndex(defaultTemplate.getDefaultShownSeq());
            getContactKeyListOutput.getDataKeyList().add(contactKeyOutput);
        }
        return getContactKeyListOutput;
    }

}