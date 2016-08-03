package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.mongodb.DataPartyRepository;
import cn.rongcapital.mkt.po.mongodb.DataParty;

@Service
public class TagStatTaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPartyRepository dataPartyRepository;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void task(Integer taskId) {

        // 1.从mysql tag表中读取所有标签名称
        // Tag tag=new Tag();
        // tag.setStatus(0);
        // List<String> tags=tagDao.selectList(tag);


        // 2.在mongo中逐个统计标签所包含人数

        logger.info(taskId.toString());
        List<DataParty> dpList = null;
        dpList = dataPartyRepository.findAll();
        for (DataParty dp : dpList) {
            System.out.println(JSON.toJSONString(dp));
        }
        dpList = dataPartyRepository.findByMid("101");
        for (DataParty dp : dpList) {
            System.out.println(JSON.toJSONString(dp));
        }
        dpList = mongoTemplate.find(new Query(Criteria.where("mid").is("101")), DataParty.class);
        for (DataParty dp : dpList) {
            System.out.println(JSON.toJSONString(dp));
        }
    }
}
