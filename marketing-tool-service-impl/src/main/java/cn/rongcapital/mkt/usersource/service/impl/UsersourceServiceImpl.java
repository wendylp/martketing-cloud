/*************************************************
 * @功能简述: UsersourceService 实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:	2017.03.01
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.usersource.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.regex.RegularValidation;
import cn.rongcapital.mkt.common.uuid.UUIDGenerator;
import cn.rongcapital.mkt.dao.usersource.UsersourceDao;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;
import cn.rongcapital.mkt.material.coupon.service.impl.CouponSaveServiceImpl;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.usersource.po.Usersource;
import cn.rongcapital.mkt.usersource.service.UsersourceService;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceIn;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceListIn;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class UsersourceServiceImpl implements UsersourceService{

	private static final Logger logger = LoggerFactory.getLogger(CouponSaveServiceImpl.class);

	@Autowired
	private UsersourceDao usersourceDao;

	
	@Override
	public BaseOutput saveUsersource(UsersourceIn in) {
		EventBehaviorOut baseOutput = new EventBehaviorOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
		
		if (!RegularValidation.nameValidation(in.getName())){
			logger.error("name validation failed, name: {}", in.getName());
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR_NAME.getCode());
            baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR_NAME.getMsg());
            return baseOutput;
		}
		
		Usersource parm = new Usersource();
		parm.setName(in.getName());
		parm.setStatus((byte)0);
		List<Usersource> list = usersourceDao.selectList(parm);
		if(CollectionUtils.isEmpty(list)){
			parm.setClassificationId(in.getId());
			parm.setAvailable(false); //通过页面新建的来源默认未启用
			parm.setIdentityId(UUIDGenerator.getUUID()); //识别标识符全局唯一，打标签用。
			parm.setDescription(in.getDescription()!=null ? in.getDescription() : null);
			usersourceDao.insert(parm);
		}else{
			logger.error("name already exists, name: {}", in.getName());
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR_NAME_EXISTS.getCode());
            baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR_NAME_EXISTS.getMsg());
            return baseOutput;
		}
		
		return baseOutput;
	}

	@Override
	public BaseOutput getUsersourceList(Long id, Integer index, Integer size) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);
        
        Usersource temp = new Usersource(index,size);
        temp.setClassificationId(id);
        temp.setOrderField("create_time");
        temp.setOrderFieldType("desc");
        
        int totalCnt = usersourceDao.selectListCount(temp);
        result.setTotalCount(totalCnt);
        if(totalCnt >0){
        	List<Usersource> list = usersourceDao.selectList(temp);
            if (!CollectionUtils.isEmpty(list)) {
            	for (Usersource e : list) {
    				Map<String, Object> map = new HashMap<String, Object>();
    				map.put("id", e.getId());
    				map.put("name", e.getName());
    				map.put("identity_id", e.getIdentityId());
    				map.put("available", e.getAvailable() ? 1L : 0L);
    				map.put("description", e.getDescription()!=null ? e.getDescription(): "");
    				result.getData().add(map);
    			}
                result.setTotal(list.size());
            }
        }
		
		return result;
	}

}
