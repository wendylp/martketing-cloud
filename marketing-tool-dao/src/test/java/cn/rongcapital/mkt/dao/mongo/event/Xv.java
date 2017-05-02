/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年4月11日 
 * @date(最后修改日期)：2017年4月11日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.dao.mongo.event;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;

import com.mongodb.BasicDBList;

@RunWith(SpringJUnit4ClassRunner.class)
public class Xv extends AbstractUnitTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void test() {
		// 大类
		BasicDBList max = new BasicDBList();
		Criteria criteria = Criteria.where("PCCODEP").is(null);
		Query query = new Query(criteria);
		List<Bbx> bbxs = mongoTemplate.find(query, Bbx.class, "TBBXProductType");
		StringBuilder builder = new StringBuilder();
		for (Bbx cur : bbxs) {
			max.add(cur.getPCCODE());
			builder.append(cur.getPCNAME() + "/");
		}
		String str = builder.substring(0, builder.length() - 1);
		System.out.println("大类：" + str);

		// 中类
		BasicDBList mid = new BasicDBList();
		criteria = Criteria.where("PCCODEP").in(max);
		query = new Query(criteria);
		List<Bbx> mids = mongoTemplate.find(query, Bbx.class, "TBBXProductType");
		builder = new StringBuilder();
		for (Bbx cur : mids) {
			mid.add(cur.getPCCODE());
			builder.append(cur.getPCNAME() + "/");
		}
		str = builder.substring(0, builder.length() - 1);
		System.out.println("中类：" + str);

		// 小类
		criteria = Criteria.where("PCCODEP").in(mid);
		query = new Query(criteria);
		List<Bbx> mins = mongoTemplate.find(query, Bbx.class, "TBBXProductType");
		builder = new StringBuilder();
		for (Bbx cur : mins) {
			builder.append(cur.getPCNAME() + "/");
		}
		str = builder.substring(0, builder.length() - 1);
		System.out.println("小类：" + str);
	}

	public class Bbx {
		private String PCCODE;
		private String PCNAME;
		private String PCCODEP;

		public String getPCCODE() {
			return PCCODE;
		}

		public void setPCCODE(String pCCODE) {
			PCCODE = pCCODE;
		}

		public String getPCNAME() {
			return PCNAME;
		}

		public void setPCNAME(String pCNAME) {
			PCNAME = pCNAME;
		}

		public String getPCCODEP() {
			return PCCODEP;
		}

		public void setPCCODEP(String pCCODEP) {
			PCCODEP = pCCODEP;
		}

		@Override
		public String toString() {
			return "Bbx [PCCODE=" + PCCODE + ", PCNAME=" + PCNAME + ", PCCODEP=" + PCCODEP + "]";
		}

	}

}
