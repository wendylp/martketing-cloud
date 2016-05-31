package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.PartyDao;
import cn.rongcapital.mkt.po.Party;
import cn.rongcapital.mkt.service.DataGetMainListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataGetMainListServiceImpl implements DataGetMainListService {

    @Autowired
    private PartyDao partyDao;

    @Override
    public Object getMainList(String method, String userToken, Integer index, Integer size,
                    String ver) {
        Party paramParty = new Party();
        paramParty.setStartIndex(index);
        paramParty.setPageSize(size);

        List<Party> partyList = partyDao.selectList(paramParty);

        BaseOutput rseult = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        if (partyList != null && partyList.isEmpty()) {
            for (Party party : partyList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", party.getId());
                map.put("account_name", party.getAccountName());
                map.put("name", party.getName());
                map.put("gender", party.getGender());
                map.put("age", party.getAge());
                map.put("home_address", party.getHomeAddress());
                map.put("work_address", party.getWorkAddress());
                map.put("home_status", party.getHomeStatus());
                map.put("work_status", party.getWorkStatus());
                map.put("member_level", party.getMemberLevel());
                map.put("mobile", party.getMobile());
                map.put("email", party.getEmail());
                map.put("wechat", party.getWechat());
                map.put("qq", party.getQq());
                map.put("weibo", party.getWeibo());
                map.put("order_count", party.getOrderCount());
                map.put("order_amount", party.getOrderAmount());
                map.put("cart_item_count", party.getCartItemCount());
                map.put("favorite_item_count", party.getFavoriteItemCount());
                map.put("salary", party.getSalary());
                map.put("offline_activity_attendence", party.getOfflineActivityAttendence());
                rseult.getData().add(map);
            }
        }

        rseult.setTotal(rseult.getData().size());

        return Response.ok().entity(rseult).build();
    }

}
