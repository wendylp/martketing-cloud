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

@RunWith(SpringJUnit4ClassRunner.class)
public class Xv010 extends AbstractUnitTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void test010() {
		Criteria criteria = Criteria.where("mid").is(34307);
		Query query = new Query(criteria);
		cn.rongcapital.mkt.po.mongodb.DataParty dp2 = mongoTemplate.findOne(query, cn.rongcapital.mkt.po.mongodb.DataParty.class,
				"data_party");
		System.out.println(dp2);
	}

	@Test
	public void test() {
		Criteria criteria = new Criteria();
		Query query = new Query(criteria);
		List<Org> orgs = mongoTemplate.find(query, Org.class, "TBBXOrg");
		for (Org cur : orgs) {
			System.out.println("org:" + cur.getORGNAME());
			Criteria criteria2 = Criteria.where("saleorg").is(cur.ORGCODE);
			Query query2 = new Query(criteria2);
			List<Store> stores = mongoTemplate.find(query2, Store.class, "TBBXStore");
			StringBuilder builder = new StringBuilder();
			for (Store cur2 : stores) {
				builder.append(cur2.storename + "/");
			}
			String str = builder.toString();
			if (builder.length() > 0) {
				str = builder.substring(0, builder.length() - 1);
			}
			System.out.println("store：" + str);
		}
	}

	public class Org {
		private String ORGCODE;
		private String ORGNAME;

		public String getORGCODE() {
			return ORGCODE;
		}

		public void setORGCODE(String oRGCODE) {
			ORGCODE = oRGCODE;
		}

		public String getORGNAME() {
			return ORGNAME;
		}

		public void setORGNAME(String oRGNAME) {
			ORGNAME = oRGNAME;
		}

		@Override
		public String toString() {
			return "Org [ORGCODE=" + ORGCODE + ", ORGNAME=" + ORGNAME + "]";
		}
	}

	public class Store {
		private String saleorg;
		private String storename;

		public String getSaleorg() {
			return saleorg;
		}

		public void setSaleorg(String saleorg) {
			this.saleorg = saleorg;
		}

		public String getStorename() {
			return storename;
		}

		public void setStorename(String storename) {
			this.storename = storename;
		}

		@Override
		public String toString() {
			return "Store [saleorg=" + saleorg + ", storename=" + storename + "]";
		}
	}

}
