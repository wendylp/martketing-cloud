package cn.rongcapital.mkt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.ActionDao;
import cn.rongcapital.mkt.dao.CampaignDao;
import cn.rongcapital.mkt.dao.CampaignTemplateDao;
import cn.rongcapital.mkt.dao.UserDao;
import cn.rongcapital.mkt.po.Action;
import cn.rongcapital.mkt.po.Campaign;
import cn.rongcapital.mkt.service.DemoService;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class DemoServiceImpl implements DemoService{
//	@Resource
//	private DemoDao demoDao;
	@Resource
	private CampaignDao campaignDao;
	@Resource
	private CampaignTemplateDao campaignTemplateMapper;
	@Resource
	private UserDao userMapper;
	@Resource
	private ActionDao actionDao;
	
	public int getAll(){
 		return -1;
//		return demoDao.getAll();
	}
//	@ReadWrite(type=ReadWriteType.READ)
//	public String getAll2(){
//		return campaignDao.selectByPrimaryKey(1L).getName();
//	}
//	@ReadWrite(type=ReadWriteType.READ)
//	public String getAll3(){
//		return campaignTemplateMapper.selectByPrimaryKey(1L).getNode_table_name();
//	}
//	@ReadWrite(type=ReadWriteType.READ)
//	public String getAll4(){
//		return userMapper.selectByPrimaryKey(1L).getUser_name();
//	}
//	@ReadWrite(type=ReadWriteType.READ)
//	public int getAll5(){
//		return campaignDao.deleteByPrimaryKey(2l);
//	}
	@ReadWrite(type=ReadWriteType.WRITE)
	public int insert(){
		Campaign c = new Campaign();
		c.setName("insert");
		campaignDao.insert(c);
		return c.getId().intValue();
	}
	@ReadWrite(type=ReadWriteType.READ)
	public List<Action> getAll7(String value){
		Action t = new Action();
		t.setValue(value);
		t.setStartIndex(0);
		t.setPageSize(3);
		List<Action> l = actionDao.selectList(t);
		for(Action a:l){
			System.out.println(a.getDescription());
		}
		return l;
	}
}
