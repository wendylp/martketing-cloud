package cn.rongcapital.mkt.mongodb;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.rongcapital.mkt.po.mongodb.TagRecommend;

public interface TagRecommendRepository extends MongoRepository<TagRecommend, Long> {

	/**
	 * 方法以findBy开头，后面跟TagTree中的属性名(首字母大写)
	 * 
	 * @param mid
	 * @return
	 */
	public List<TagRecommend> findByTagId(int tagId);

	Page<TagRecommend> findAll(Pageable pageable);

}