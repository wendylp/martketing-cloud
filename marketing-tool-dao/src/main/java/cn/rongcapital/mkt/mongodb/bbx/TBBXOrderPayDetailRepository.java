/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-18
 * @date(最后修改日期)：2017-4-18
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.mongodb.bbx;

import cn.rongcapital.mkt.bbx.po.TBBXOrderPayDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TBBXOrderPayDetailRepository extends MongoRepository<TBBXOrderPayDetail,Long> {
    @Override
    public Page<TBBXOrderPayDetail> findAll(Pageable pageable);
}
