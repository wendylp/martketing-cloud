package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.Party;
import cn.rongcapital.mkt.service.DataGetMainListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataGetMainListServiceImpl implements DataGetMainListService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Override
    public Object getMainList(String method, String userToken, Integer index, Integer size,
                    String ver) {
        DataParty paramParty = new DataParty();
        paramParty.setStartIndex(index);
        paramParty.setPageSize(size);

        List<DataParty> partyList = dataPartyDao.selectList(paramParty);

        BaseOutput rseult = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        if (partyList != null && !partyList.isEmpty()) {
            for (DataParty dataParty : partyList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataParty.getId());
                map.put("account_name", dataParty.getAccountName());
                map.put("name", dataParty.getName());
                map.put("gender", dataParty.getGender());
                map.put("age", dataParty.getAge());
                map.put("home_address", dataParty.getHomeAddress());
                map.put("work_address", dataParty.getWorkAddress());
                map.put("home_status", dataParty.getHomeStatus());
                map.put("work_status", dataParty.getWorkStatus());
                map.put("member_level", dataParty.getMemberLevel());
                map.put("mobile", dataParty.getMobile());
                map.put("email", dataParty.getEmail());
                map.put("wechat", dataParty.getWechat());
                map.put("qq", dataParty.getQq());
                map.put("weibo", dataParty.getWeibo());
                map.put("order_count", dataParty.getOrderCount());
                map.put("order_amount", dataParty.getOrderAmount());
                map.put("cart_item_count", dataParty.getCartItemCount());
                map.put("favorite_item_count", dataParty.getFavoriteItemCount());
                map.put("salary", dataParty.getSalary());
                map.put("offline_activity_attendence", dataParty.getOfflineActivityAttendence());
                rseult.getData().add(map);
            }
        }

        rseult.setTotal(rseult.getData().size());

        return Response.ok().entity(rseult).build();
    }
}
