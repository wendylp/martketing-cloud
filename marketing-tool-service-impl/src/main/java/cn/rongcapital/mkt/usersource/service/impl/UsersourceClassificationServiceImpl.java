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
import cn.rongcapital.mkt.dao.usersource.UsersourceDao;
import cn.rongcapital.mkt.material.coupon.service.impl.CouponSaveServiceImpl;
import cn.rongcapital.mkt.usersource.po.Usersource;
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
	
	@Autowired
	private UsersourceDao usersourceDao;
	
	private static final Long ROOT_NODE_ID = -1L;// 父节点id(根节点默认值为-1)

	
	@Override
	public BaseOutput saveUsersourceClassification(UsersourceClassificationIn in) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		if (!RegularValidation.nameValidation(in.getName())){
			logger.error("name validation failed, name: {}", in.getName());
			result.setCode(ApiErrorCode.VALIDATE_ERROR_NAME.getCode());
			result.setMsg(ApiErrorCode.VALIDATE_ERROR_NAME.getMsg());
            return result;
		}
		
		UsersourceClassification parm = new UsersourceClassification();
		parm.setName(in.getName());
		parm.setStatus((byte)0);
		List<UsersourceClassification> nameList = classificationDao.selectList(parm);
		if(CollectionUtils.isEmpty(nameList)){
			parm.setParentId( in.getId()!=null ? in.getId() : ROOT_NODE_ID);
			classificationDao.insert(parm);
		}else{
			logger.error("name already exists, name: {}", in.getName());
			result.setCode(ApiErrorCode.VALIDATE_ERROR_NAME_EXISTS.getCode());
			result.setMsg(ApiErrorCode.VALIDATE_ERROR_NAME_EXISTS.getMsg());
            return result;
		}
		
		return result;
	}

	@Override
	public BaseOutput classificationList() {
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		UsersourceClassificationOut parm = new UsersourceClassificationOut();
		parm.setParentId(ROOT_NODE_ID);
		parm.setStatus((byte)0);
		List<UsersourceClassificationOut>  rootList= classificationDao.selectClassificationList(parm);
		List<UsersourceClassificationOut> resultList= new ArrayList<UsersourceClassificationOut>();
		if(!CollectionUtils.isEmpty(rootList)){
			for (UsersourceClassificationOut node : rootList) {
				generateClassificationTree(node);
				resultList.add(node);
			}
		}
		
		result.setTotalCount(resultList.size());
		result.setTotal(resultList.size());
		result.getData().addAll(resultList);
		
		return result;
	}

	private void generateClassificationTree(UsersourceClassificationOut node) {
		UsersourceClassificationOut temp = new UsersourceClassificationOut();
		temp.setParentId(node.getId());
		temp.setStatus((byte)0);
		List<UsersourceClassificationOut> list = classificationDao.selectClassificationList(temp);
		if(CollectionUtils.isEmpty(list)){
			Usersource parm = new Usersource();
			parm.setClassificationId(node.getId());
			parm.setStatus((byte)0);
			node.setCount(Long.valueOf(usersourceDao.selectListCount(parm)));
		}else{
			node.setList(list);
			for (UsersourceClassificationOut id : list) {
				generateClassificationTree(id);
			}
		}
	}
	
}
