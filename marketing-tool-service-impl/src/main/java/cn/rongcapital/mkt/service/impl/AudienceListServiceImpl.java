/*************************************************
 * @功能简述: AudienceListService实现类
 * @see: MkyApi
 * @author: 杨玉麟
 * @version: 1.0
 * @date: 2016/6/6 
*************************************************/


package cn.rongcapital.mkt.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceColumnsDao;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.po.AudienceColumns;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.service.AudienceListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ColumnsOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceListServiceImpl implements AudienceListService {
	@Autowired
	AudienceListDao audienceListDao;
	
	@Autowired
	AudienceColumnsDao audienceColumnsDao;
	
	private static final String ORDER_BY_FIELD_NAME = "field_order";//排序的字段名
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput audienceList(String userToken,Integer size,Integer index) {
		
		AudienceList param = new AudienceList();
		param.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int totalCount = audienceListDao.selectListCount(param);
		param.setPageSize(size);
		param.setStartIndex((index-1)*size);
		param.setOrderField("create_time");
		param.setOrderFieldType("desc");
		
		List<AudienceList> reList = audienceListDao.selectList(param);
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if (CollectionUtils.isNotEmpty(reList)) {
			result.setTotal(reList.size());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (AudienceList s : reList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("audience_list_id", s.getId());
				map.put("audience_list_name", s.getAudienceName());
				map.put("audience_count", s.getAudienceRows());
				map.put("source_name", s.getSource());
				map.put("create_time", df.format(s.getCreateTime()));
				result.getData().add(map);
			}
		}
		
		//查询页面表格的列名
		AudienceColumns audienceColumns = new AudienceColumns();
		audienceColumns.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		audienceColumns.setOrderField(ORDER_BY_FIELD_NAME);
		List<AudienceColumns> audienceColumnsList = audienceColumnsDao.selectList(audienceColumns);
		List<Object> columnsOutList = new ArrayList<Object>(); 
		if(CollectionUtils.isNotEmpty(audienceColumnsList)) {
			for(AudienceColumns ac:audienceColumnsList) {
				ColumnsOut columnsOut = new ColumnsOut();
				columnsOut.setColCode(ac.getFieldCode());
				columnsOut.setColName(ac.getFieldName());
				columnsOutList.add(columnsOut);
			}
		}
		result.setColNames(columnsOutList);
		result.setTotal(result.getData().size());
		result.setTotalCount(totalCount);
		return result;
	}

    @Override
    public BaseOutput getAudienceByListId(String userToken, Integer audienceId, Integer index, Integer size) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        AudienceList audienceListParam = new AudienceList();
        audienceListParam.setId(audienceId);
        List<AudienceList> audienceLists = audienceListDao.selectList(audienceListParam);
        if (CollectionUtils.isEmpty(audienceLists)) {
            return result;
        }

        audienceListParam = audienceLists.get(0);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("audience_list_id", audienceListParam.getId());
        resultMap.put("audience_list_name", audienceListParam.getAudienceName());
        resultMap.put("audience_count", audienceListParam.getAudienceRows());
        resultMap.put("source_name", audienceListParam.getSource());
        resultMap.put("create_time", df.format(audienceListParam.getCreateTime()));

        result.getData().add(resultMap);
        return result;
    }

}
