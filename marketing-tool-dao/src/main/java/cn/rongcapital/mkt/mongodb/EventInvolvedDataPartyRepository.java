package cn.rongcapital.mkt.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.rongcapital.mkt.po.mongodb.EventInvolvedDataParty;

public interface  EventInvolvedDataPartyRepository extends MongoRepository<EventInvolvedDataParty,Long> {


}
