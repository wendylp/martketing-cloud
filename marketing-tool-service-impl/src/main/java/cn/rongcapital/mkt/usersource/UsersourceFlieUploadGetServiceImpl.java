package cn.rongcapital.mkt.usersource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.serialize.SerializeUtil;
import cn.rongcapital.mkt.common.uuid.UUIDGenerator;
import cn.rongcapital.mkt.usersource.po.Usersc;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.UploadFileOut;
@Service
public class UsersourceFlieUploadGetServiceImpl implements UsersourceFlieUploadGetService{

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
					lists.add(usersc);
					num++;
				}
			}
			//存入redis中
			String key = UUIDGenerator.getUUID();
			JedisClient.set(key.getBytes(), SerializeUtil.serialize(lists));
			out.setRecord_count(num);
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
		return null;
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
}
