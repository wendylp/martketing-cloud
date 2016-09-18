package cn.rongcapital.mkt.jedis;

import org.apache.log4j.Logger;


public class JedisProperties {

	private static final Logger log = Logger.getLogger(JedisProperties.class);

	public static String JEDIS_CONFIG = "/cloud_dev/jedisConfig.properties";
	private static JedisProperties properties = null;
	private JedisConfiguration config = null;
	private String classesPath = null;

	public static JedisProperties getInstance() {
		if (properties == null) {
			properties = new JedisProperties();
			try {
				properties.config = new JedisConfiguration(JEDIS_CONFIG);
			} catch (JedisConfigurationException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return properties;
	}

	public static JedisProperties getInstance(String filepath) {
		if (properties == null) {
			properties = new JedisProperties();
			try {
				properties.config = new JedisConfiguration(filepath);
			} catch (JedisConfigurationException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		return properties;
	}

	public String getValue(String key, String defalutValue) {
		return this.config.getValue(key, defalutValue);
	}

	public String getValue(String key) {
		return this.config.getValue(key);
	}

	public int getIntValue(String key, int defaultValue) {
		int value = 0;
		value = Integer.parseInt(getValue(key, defaultValue + ""));
		return value;
	}

	public int getIntValue(String key) {
		int value = 0;
		value = Integer.parseInt(getValue(key));
		return value;
	}

	public void setValue(String key, String value) throws JedisConfigurationException {
		this.config.setValue(key, value);
	}

	public String getClassesPath() {
		return this.classesPath;
	}

	public void setClassesPath(String classesPath) {
		this.classesPath = classesPath;
	}

	public static void main(String[] args) throws JedisConfigurationException {
		String ips = getInstance().getValue("dns.list");
		System.out.println(ips);
	}
}
