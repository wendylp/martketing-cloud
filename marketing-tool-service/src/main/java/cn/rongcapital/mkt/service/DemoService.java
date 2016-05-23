package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.po.Action;

public interface DemoService {

	public int getAll();
//	public String getAll2();
//	public String getAll3();
//	public String getAll4();
//	public int getAll5();
	public int insert();
	public List<Action> getAll7(String value);
}
