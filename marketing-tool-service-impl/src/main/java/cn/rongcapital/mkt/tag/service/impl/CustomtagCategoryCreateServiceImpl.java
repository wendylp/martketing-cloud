package cn.rongcapital.mkt.tag.service.impl;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.mongodao.MongoCustomTagCategoryDao;
import cn.rongcapital.mkt.po.mongodb.CustomTagCategory;
import cn.rongcapital.mkt.tag.service.CustomtagCategoryCreateService;
import cn.rongcapital.mkt.tag.vo.in.CustomTagCategoryIn;
import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 创建自定义标签分类
 *
 * @see （与该类关联的类): CustomtagCategoryCreateService
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.7 @date(创建、开发日期): 2017-1-20 最后修改日期: 2017-1-20
 * @复审人: 丛树林
 *************************************************/
@Service
public class CustomtagCategoryCreateServiceImpl implements CustomtagCategoryCreateService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MongoCustomTagCategoryDao mongoCustomTagCategoryDao;

	/**
	 * 功能描述：创建、编辑自定义标签的分类
	 * 
	 * 接口：mkt.customtag.category.create
	 * 
	 * @param body
	 *            json对象
	 * @param securityContext
	 *            用户登录信息
	 * 
	 * @return BaseOutput
	 */
	@Override
	public BaseOutput updateCustomtagCategory(CustomTagCategoryIn body, SecurityContext securityContext) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		CustomTagCategory toBean = new CustomTagCategory();

		org.springframework.beans.BeanUtils.copyProperties(body, toBean);

		long count = mongoCustomTagCategoryDao.countByCustomTagCategoryName(toBean);
		if (count > 0) {
			result.setCode(ApiErrorCode.VALIDATE_ERROR_CATEGORY_EXISTS.getCode());
			result.setMsg(ApiErrorCode.VALIDATE_ERROR_CATEGORY_EXISTS.getMsg());

			return result;
		}

		if (toBean.getCustomTagCategoryId() != null) {
			mongoCustomTagCategoryDao.updateCategoryNameById(toBean);
		} else {
			toBean.setCustomTagCategoryId(RandomStringUtils.random(10,true,true) + System.currentTimeMillis());
			toBean.setCustomTagCategoryType(1);
			toBean.setLevel(0);
			toBean.setIsDeleted(0);
			toBean.setCreateTime(new Date());
			toBean.setUpdateTime(new Date());
			toBean.setChildrenCustomTagCategoryList(new ArrayList<String>());
			toBean.setChildrenCustomTagList(new ArrayList<String>());
			mongoCustomTagCategoryDao.insertMongoCustomTagCategory(toBean);

		}

		return result;
	}

}
