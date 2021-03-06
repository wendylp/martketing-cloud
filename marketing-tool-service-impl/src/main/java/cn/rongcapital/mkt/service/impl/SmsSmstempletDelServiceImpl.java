package cn.rongcapital.mkt.service.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.common.exception.NoWriteablePermissionException;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dao.SmsTempletMaterialMapDao;
import cn.rongcapital.mkt.dataauth.interceptor.DataAuthEvict;
import cn.rongcapital.mkt.dataauth.interceptor.DataAuthWriteable;
import cn.rongcapital.mkt.dataauth.interceptor.ParamType;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsSmstempletDelService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsSmstempletDelIn;
@Service
public class SmsSmstempletDelServiceImpl implements SmsSmstempletDelService{
    
    @Autowired
    private SmsMaterialDao smsMaterialDao;
    
    
    @Autowired
    private SmsTempletDao smsTempletDao;

    @Autowired
    private SmsTempletMaterialMapDao smsTempletMaterialMapDao;
    
    @Autowired
    private DataAuthService dataAuthService;
    
    private static final String TABLE_NAME ="sms_templet";// 资源对应表名
    
    
    /**
     * 短信模板删除
     * 
     * 接口：mkt.sms.smstemplet.del
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @throws NoWriteablePermissionException 
     * @Date 2016-11-14
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false ,rollbackFor= NoWriteablePermissionException.class)
    @DataAuthWriteable(resourceType=TABLE_NAME,resourceId="#body.id",orgId="#body.orgId",type = ParamType.SpEl)
    @DataAuthEvict(resourceType = TABLE_NAME,resourceId = "#body.id",type = ParamType.SpEl)
    public BaseOutput delSmsTemple(SmsSmstempletDelIn body, SecurityContext securityContext) throws  NoWriteablePermissionException {
       
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        
        // 查询素材中是否用到
        SmsMaterial smsMaterialSerach = new SmsMaterial();
        smsMaterialSerach.setSmsTempletId(body.getId());
        smsMaterialSerach.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsMaterial> smsMaterialLists = smsMaterialDao.selectList(smsMaterialSerach);
        if(smsMaterialLists != null && smsMaterialLists.size() > 0) {
            result.setCode(ReturnCode.DATA_IS_USE.getCode());
            result.setMsg(ReturnCode.DATA_IS_USE.getMsg());
            return result;
        }
        SmsTemplet smsTempletDel = new SmsTemplet();
        smsTempletDel.setId(body.getId());
        smsTempletDel.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        smsTempletDel.setOrgId(Integer.parseInt(String.valueOf(body.getOrgId())));
        // 判断数据是否存在
        List<SmsTemplet> smsTempletLists = smsTempletDao.selectList(smsTempletDel);
        if(smsTempletLists ==null || smsTempletLists.size() <= 0) {
            result.setCode(ReturnCode.DATA_NOT_EXIST.getCode());
            result.setMsg(ReturnCode.DATA_NOT_EXIST.getMsg());
            return result;
        }
        
        // 删除数据
        smsTempletDel.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
        int delCount = this.smsTempletDao.updateById(smsTempletDel);

//        // 判断用户是否具有删除短信模板对应权限记录的权限
//		if(dataAuthService.validateWriteable(TABLE_NAME, smsTempletDel.getId(), body.getOrgId())){
//		    // 删除短信模板对应权限记录
//		    dataAuthService.evict(TABLE_NAME, smsTempletDel.getId());
//		}
		
        
		//变量模板时
		if(SmsTempletTypeEnum.VARIABLE.getStatusCode() == smsTempletLists.get(0).getType().intValue()){

			//删除物料关联
			smsTempletMaterialMapDao.deleteByTempletId(smsTempletDel.getId());
		}
        
        // 设置删除个数
        result.setTotal(delCount);
        
        return result;
    }

    // 自定义返回状态
    private enum ReturnCode {
        SUCCESS(0, "SUCCESS"), 
        DATA_IS_USE(1, "当前内容被使用中 不能删除请稍后再试"), 
        DATA_NOT_EXIST(2, "数据不存在");

        private int code;

        private String msg;

        private ReturnCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }
    }
    
}
