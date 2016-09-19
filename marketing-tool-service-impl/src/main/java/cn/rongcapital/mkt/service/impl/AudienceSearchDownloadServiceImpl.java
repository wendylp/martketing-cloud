/*************************************************
 * @功能简述:
 * @see: MkyApi
 * @author: chengjincheng
 * @version: 1.0
 * @date: 2016/9/13
 *************************************************/

package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.service.AudienceSearchDownloadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceSearchDownloadServiceImpl implements AudienceSearchDownloadService {

	@Autowired
	DataPopulationDao dataPopulationDao;

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput searchData(Integer audience_id) {

		List<DataPopulation> dataList = dataPopulationDao.searchDataByAudienceId(audience_id);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		String[][] columnNameList = { { "name", "姓名" }, { "mobile", "手机号" }, { "gender", "性别" },
				{ "birthdayExport", "出身年月日" }, { "provice", "省" }, { "city", "市" }, { "email", "邮箱" },
				{ "identifyNo", "身份证号" }, { "drivingLicense", "驾驶证号" } };

		List<Map<String, String>> columnsMapList = new ArrayList<Map<String, String>>();

		for (String[] column : columnNameList) {
			Map<String, String> columnsNameMap = new HashMap<String, String>();
			columnsNameMap.put(column[0], column[1]);
			columnsMapList.add(columnsNameMap);
		}

		File file = FileUtil.generateFileforDownload(columnsMapList, dataList, FileNameEnum.POPULATION.getDetailName());

		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("download_file_name", file.getName());
		result.getData().add(resultMap);

		return result;
	}
}