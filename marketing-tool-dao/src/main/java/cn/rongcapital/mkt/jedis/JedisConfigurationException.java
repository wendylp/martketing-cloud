package cn.rongcapital.mkt.jedis;

public class JedisConfigurationException extends Exception {
	
	private static final long serialVersionUID = -3145511931705048960L;

	public JedisConfigurationException() {
	}

	public JedisConfigurationException(String msg) {
		super(msg);
	}
}
