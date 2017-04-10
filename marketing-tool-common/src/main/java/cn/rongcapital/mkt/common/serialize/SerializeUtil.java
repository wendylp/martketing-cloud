package cn.rongcapital.mkt.common.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SerializeUtil {

	 private final static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);
	//序列化
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			logger.error("序列化异常", e);
		}finally{
			try {
				baos.close();
				oos.close();
			} catch (IOException e) {
				logger.error("IO Close Exception", e.getMessage(), e);
			}
		}
		return null;
	}
	
	//反序列化
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			logger.error("返序列化异常", e);
		}finally{
			try {
				bais.close();
				ois.close();
			} catch (IOException e) {
				logger.error("IO Close Exception", e.getMessage(), e);
			}
		}
		return null;
	}
}
