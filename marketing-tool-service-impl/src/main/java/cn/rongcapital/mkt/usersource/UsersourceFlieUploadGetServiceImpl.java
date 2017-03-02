package cn.rongcapital.mkt.usersource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.common.regex.RegularValidation;
import cn.rongcapital.mkt.common.serialize.SerializeUtil;
import cn.rongcapital.mkt.common.uuid.UUIDGenerator;
import cn.rongcapital.mkt.dao.usersource.UsersourceClassificationDao;
import cn.rongcapital.mkt.dao.usersource.UsersourceDao;
import cn.rongcapital.mkt.usersource.po.Usersc;
import cn.rongcapital.mkt.usersource.po.Usersource;
import cn.rongcapital.mkt.usersource.po.UsersourceClassification;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.UploadFileOut;

import com.alibaba.druid.util.StringUtils;
@Service
public class UsersourceFlieUploadGetServiceImpl implements UsersourceFlieUploadGetService{

	@Autowired
	private UsersourceClassificationDao usersourceClassificationDao;
	
	@Autowired
	private UsersourceDao usersourceDao;
	
	
	@Override
	public BaseOutput getUsersourceUploadUrlGet() {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), 1, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("file_url", ApiConstant.USERSOURCE_FILE_UPLOAD);
		baseOutput.getData().add(map);
		baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
		baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		return baseOutput;
	}

	@Override
	public BaseOutput uploadFile(MultipartFormDataInput input) {
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file_input");
		BaseOutput baseOutput = new BaseOutput();
		baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
		baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		if (inputParts == null) {
			baseOutput.setCode(ApiErrorCode.FILE_ERROR.getCode());
			baseOutput.setMsg(ApiErrorCode.FILE_ERROR.getMsg());
			return baseOutput;
		}
		UploadFileOut out = new UploadFileOut();
		InputPart inputPart = inputParts.get(0);
		String fileName = getFileName(inputPart);
		out.setFile_name(fileName);
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
			baseOutput.setCode(ApiErrorCode.VALIDATE_ERROR.getCode());
			baseOutput.setMsg("上传的文件不是预定格式");
			return baseOutput;
		}
		InputStream is = null;
		try {
			is = inputPart.getBody(InputStream.class, null);
			byte[] bytes = IOUtils.toByteArray(is);
			is = new ByteArrayInputStream(bytes);
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.rowIterator();
			Long num = 0L;
			List<Usersc> lists = new ArrayList<Usersc>();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Integer rowIndex = row.getRowNum();
				if (rowIndex == 0) {
					continue;
				}
				Usersc usersc = getUsersc(row);
				if(usersc != null){
					BaseOutput outPut = checkUsersc(lists, usersc, baseOutput);
					if(outPut.getCode() != 0){
						return outPut;
					}
					lists.add(usersc);
					num++;
				}
			}
			//存入redis中
			String key = UUIDGenerator.getUUID();
			JedisClient.set(key.getBytes(), SerializeUtil.serialize(lists));
			out.setFile_id(key);
			out.setRecord_count(num);
			List<Object> objList = new ArrayList<Object>();
		    objList.add(out);
			baseOutput.setData(objList);
		} catch (Exception e) {
			baseOutput.setCode(ApiErrorCode.SYSTEM_ERROR.getCode());
			baseOutput.setMsg("系统异常");
			return baseOutput;
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				baseOutput.setCode(ApiErrorCode.SYSTEM_ERROR.getCode());
				baseOutput.setMsg("系统异常");
				return baseOutput;
			}
		}
		return baseOutput;
	}
	
	private String getFileName(InputPart inputPart) {

		String finalFileName = null;
		try {
			Field field = inputPart.getClass().getDeclaredField("bodyPart");
			field.setAccessible(true);
			Object bodyPart = field.get(inputPart);
			Method methodBodyPart = bodyPart.getClass().getMethod("getHeader", new Class[] {});
			Iterable iterable = (Iterable) methodBodyPart.invoke(bodyPart, new Class[] {});
			Object[] content = IteratorUtils.toArray(iterable.iterator());
			Method methodContent = content[0].getClass().getMethod("getRaw",
					new Class[] {});
			String[] contentDisposition = methodContent.invoke(content[0], new Class[] {}).toString().split(";");
			for (String filename : contentDisposition) {
				if (filename.trim().startsWith("filename")) {
					String[] name = filename.split("=");
					finalFileName = name[1].trim().replaceAll("\"", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			finalFileName = "temp";

		}
		return finalFileName;
	}
	
	private BaseOutput checkUsersc(List<Usersc> lists, Usersc usersc, BaseOutput baseOutput){
		
		if(usersc.isError()){
			baseOutput.setCode(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getCode());
			baseOutput.setMsg(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getMsg());
			return baseOutput;
		}
		//用户来源不存在
		if(StringUtils.isEmpty(usersc.getName())){
			baseOutput.setCode(ApiErrorCode.USERSOURCE_NOT_FOUND.getCode());
			baseOutput.setMsg(ApiErrorCode.USERSOURCE_NOT_FOUND.getMsg());
			return baseOutput;
		}
		//来源一级分类不能为空
		if(StringUtils.isEmpty(usersc.getPrimaryClassification())){
			baseOutput.setCode(ApiErrorCode.PRIMARYCLASSIFICATION_NOT_FOUND.getCode());
			baseOutput.setMsg(ApiErrorCode.PRIMARYCLASSIFICATION_NOT_FOUND.getMsg());
			return baseOutput;
		}
		//分类或来源存在非法字符
		if(!RegularValidation.nameValidation(usersc.getName())){
			baseOutput.setCode(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getCode());
			baseOutput.setMsg(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getMsg());
			return baseOutput;
		}
		//分类或来源存在非法字符
		if(!RegularValidation.nameValidation(usersc.getPrimaryClassification())){
			baseOutput.setCode(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getCode());
			baseOutput.setMsg(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getMsg());
			return baseOutput;
		}
		//分类或来源存在非法字符
		if(!StringUtils.isEmpty(usersc.getTwoLevelClassification())&&!RegularValidation.nameValidation(usersc.getTwoLevelClassification())){
			baseOutput.setCode(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getCode());
			baseOutput.setMsg(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getMsg());
			return baseOutput;
		}
		//分类或来源存在非法字符
		if(!StringUtils.isEmpty(usersc.getThreeLevelClassification())&&!RegularValidation.nameValidation(usersc.getThreeLevelClassification())){
			baseOutput.setCode(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getCode());
			baseOutput.setMsg(ApiErrorCode.USERSOURCE_FORMAT_ERROR.getMsg());
			return baseOutput;
		}
		boolean flag = false;
		for(Usersc sc : lists){
			if(usersc.getName().equals(sc.getName())){
				flag = true;
			}
		}
		if(flag){
			baseOutput.setCode(ApiErrorCode.SOURCECLASS_FOUND.getCode());
			baseOutput.setMsg(ApiErrorCode.SOURCECLASS_FOUND.getMsg());
			return baseOutput;
		}
		return baseOutput;
	}
	
	
	private Usersc getUsersc(Row row) throws Exception{
		Class rowClass = (Class) row.getClass(); 
		Field[] rowfs = rowClass.getDeclaredFields(); 
		Field rowf = rowfs[1];
		rowf.setAccessible(true);
		Object val = rowf.get(row);
		String valString = val.toString();
		Usersc usersc = null;
		if(valString.length() > 5){
			String trValue = valString.replace(" ", "");
			String valNew = trValue.substring(1, trValue.length()-1);
			String[] vals = valNew.split(",");
			usersc = new Usersc();
			//防止模板出现多余的逗号
			if(vals.length > 6){
				usersc.setError(true);
				return usersc;
			}
			for(String value : vals){
				String[] cell = value.split("=");
				String valu = null;
				String key = cell[0];
				if(cell.length == 2){
					 valu = cell[1];
				}
				switch (key) {
				case "0":
					usersc.setPrimaryClassification(valu);
					break;
				case "1":
					usersc.setTwoLevelClassification(valu);
					break;
				case "2":
					usersc.setThreeLevelClassification(valu);
					break;
				case "3":
					usersc.setName(valu);
					break;
				case "4":
					usersc.setDescription(valu);
					break;
				case "5":
					usersc.setRemarks(valu);
					break;
				default:
					break;
				}
			}
		}
		return usersc;
	}

	@Override
	public BaseOutput importUsersourceDate(String fileId) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), 1, null);
		//判断一次todo
		
		Date now = new Date();
		try {
			byte[] val = JedisClient.get(fileId.getBytes());
			List<Usersc> list = (List<Usersc>) SerializeUtil.unserialize(val);
			for(Usersc usersc : list){
				//一级分类
				UsersourceClassification uc = new UsersourceClassification();
				uc.setName(usersc.getPrimaryClassification());
				List<UsersourceClassification> listus = usersourceClassificationDao.selectList(uc);
				int size = listus.size();
				Long priId = null;
				if(size == 0){
					uc.setParentId(-1L);
					uc.setInitialData(true);
					uc.setStatus((byte) 0);
					uc.setCreateTime(now);
					uc.setUpdateTime(now);
					usersourceClassificationDao.insert(uc);
					priId = uc.getId();
				}else{
					priId =  listus.get(0).getId();
				}
				//二级分类
				String twoName = usersc.getTwoLevelClassification();
				Long twoId = null;
				if(!StringUtils.isEmpty(twoName)){
					UsersourceClassification ucTwo = new UsersourceClassification();
					ucTwo.setName(twoName);
					List<UsersourceClassification> listusTwo = usersourceClassificationDao.selectList(ucTwo);
					int sizeTwo = listusTwo.size();
					if(sizeTwo == 0){
						ucTwo.setParentId(priId);
						ucTwo.setInitialData(true);
						ucTwo.setStatus((byte) 0);
						ucTwo.setCreateTime(now);
						ucTwo.setUpdateTime(now);
						usersourceClassificationDao.insert(ucTwo);
						twoId = ucTwo.getId();
					}else{
						twoId = listusTwo.get(0).getId();
					}
				}
				//三级分类
				String threeName = usersc.getThreeLevelClassification();
				Long threeId = null;
				if(!StringUtils.isEmpty(threeName)){
					UsersourceClassification ucThree = new UsersourceClassification();
					ucThree.setName(threeName);
					List<UsersourceClassification> listusThree = usersourceClassificationDao.selectList(ucThree);
					int sizeThree = listusThree.size();
					if(sizeThree == 0){
						ucThree.setParentId(twoId);
						ucThree.setInitialData(true);
						ucThree.setStatus((byte) 0);
						ucThree.setCreateTime(now);
						ucThree.setUpdateTime(now);
						usersourceClassificationDao.insert(ucThree);
						threeId = ucThree.getId();
					}
				}
				//来源
				String name = usersc.getName();
				if(StringUtils.isEmpty(name)){
					baseOutput.setCode(ApiErrorCode.USERSOURCE_NOT_FOUND.getCode());
					baseOutput.setMsg(ApiErrorCode.USERSOURCE_NOT_FOUND.getMsg());
					return baseOutput;
				}
				Usersource usersource = new Usersource();
				usersource.setName(name);
				List<Usersource> usersourcList = usersourceDao.selectList(usersource);
				int sourceSize = usersourcList.size();
				if(sourceSize > 0){
					baseOutput.setCode(ApiErrorCode.SOURCE_FOUND.getCode());
					baseOutput.setMsg(ApiErrorCode.SOURCE_FOUND.getMsg());
					return baseOutput;
				}
				usersource.setIdentityId(UUIDGenerator.getUUID());
				usersource.setAvailable(true);
				usersource.setDescription(usersc.getDescription());
				if(threeId != null){
//					usersource.setClassificationId(threeId);
				}
			}
		} catch (JedisException e) {
			baseOutput.setCode(ApiErrorCode.REDIS_GET_DATA_ERROR.getCode());
			baseOutput.setMsg(ApiErrorCode.REDIS_GET_DATA_ERROR.getMsg());
			return baseOutput;
		}
		return null;
	}
}
