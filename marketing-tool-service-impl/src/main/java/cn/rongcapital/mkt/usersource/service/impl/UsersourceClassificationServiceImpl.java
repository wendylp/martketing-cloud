/*************************************************
 * @功能简述: UsersourceClassificationService 实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date:	2017.03.01
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.usersource.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.regex.RegularValidation;
import cn.rongcapital.mkt.dao.usersource.UsersourceClassificationDao;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;
import cn.rongcapital.mkt.material.coupon.service.impl.CouponSaveServiceImpl;
import cn.rongcapital.mkt.usersource.po.UsersourceClassification;
import cn.rongcapital.mkt.usersource.service.UsersourceClassificationService;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceClassificationIn;
import cn.rongcapital.mkt.usersource.vo.out.UsersourceClassificationOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class UsersourceClassificationServiceImpl implements UsersourceClassificationService{

	private static final Logger logger = LoggerFactory.getLogger(CouponSaveServiceImpl.class);

	@Autowired
	private UsersourceClassificationDao classificationDao;
	
	@Override
	public BaseOutput saveUsersourceClassification(UsersourceClassificationIn in) {
		EventBehaviorOut baseOutput = new EventBehaviorOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
		
		if (!RegularValidation.nameValidation(in.getName())){
			logger.error("name validation failed, name: {}", in.getName());
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR_NAME.getCode());
            baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR_NAME.getMsg());
            return baseOutput;
		}
		
		UsersourceClassification parm = new UsersourceClassification();
		parm.setName(in.getName());
		parm.setStatus((byte)0);
		List<UsersourceClassification> nameList = classificationDao.selectList(parm);
		if(CollectionUtils.isEmpty(nameList)){
			parm.setParentId( in.getId()!=null ? in.getId() : -1L);
			classificationDao.insert(parm);
		}else{
			logger.error("name already exists, name: {}", in.getName());
            baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR_NAME_EXISTS.getCode());
            baseOutput.setMsg(ApiErrorCode.VALIDATE_ERROR_NAME_EXISTS.getMsg());
            return baseOutput;
		}
		
		return baseOutput;
	}

	@Override
	public BaseOutput classificationList() {
		EventBehaviorOut baseOutput = new EventBehaviorOut(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
		
		UsersourceClassification parm = new UsersourceClassification();
		parm.setParentId(-1L);
		parm.setStatus((byte)0);
		List<UsersourceClassification> rootList = classificationDao.selectList(parm);
		List<UsersourceClassification> resultList = new ArrayList<UsersourceClassification>();

		for (UsersourceClassification root : rootList) {
			generateClassificationList(resultList, root);
		}
		baseOutput.getData().addAll(resultList);
		
		return baseOutput;
	}

	private void generateClassificationList(List<UsersourceClassification> resultList, UsersourceClassification root) {
		UsersourceClassification temp;
		temp = new UsersourceClassification();
		temp.setParentId(root.getId());
		List<UsersourceClassification> list = classificationDao.selectList(temp);
		if(CollectionUtils.isEmpty(list)){
			resultList.add(root);
		}else{
			for(UsersourceClassification classification : list){
				generateClassificationList(resultList,classification);
			}
		}
	}
	
	private UsersourceClassificationOut convert(UsersourceClassification mc){
		
		UsersourceClassificationOut out = new UsersourceClassificationOut();
		return null;
	}

}
