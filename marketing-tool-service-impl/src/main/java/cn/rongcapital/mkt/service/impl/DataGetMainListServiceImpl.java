package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.dao.DataAppDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPosDao;
import cn.rongcapital.mkt.po.DataApp;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPos;
import cn.rongcapital.mkt.service.DataGetMainListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataGetMainListServiceImpl implements DataGetMainListService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private DataAppDao dataAppDao;

    @Autowired
    private DataPosDao dataPosDao;

    @Override
    public Object getMainList(String method, String userToken, Integer dataType, Integer index,
                    Integer size, String ver) {

        BaseOutput rseult = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        if (dataType == DataTypeEnum.PARTY.getCode()) {
            assignPartyData(rseult, index, size);
        } // 获取App数据
        else if (dataType == DataTypeEnum.APP.getCode()) {
            assignAppData(rseult, index, size);
        } else if (dataType == DataTypeEnum.POS.getCode()) {
            assignAppData(rseult, index, size);
        }


        rseult.setTotal(rseult.getData().size());

        return Response.ok().entity(rseult).build();
    }

    // 这代码写的太2了
    private void assignPartyData(BaseOutput rseult, int index, int size) {

        DataParty paramParty = new DataParty();
        paramParty.setStartIndex(index);
        paramParty.setPageSize(size);
        paramParty.setDeleted(Boolean.FALSE);

        List<DataParty> dataPartyList = dataPartyDao.selectList(paramParty);
        if (dataPartyList != null && !dataPartyList.isEmpty()) {
            for (DataParty dataParty : dataPartyList) {
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
    }

    private void assignAppData(BaseOutput rseult, int index, int size) {
        DataApp paramDataApp = new DataApp();
        paramDataApp.setStartIndex(index);
        paramDataApp.setPageSize(size);
        paramDataApp.setDeleted(Boolean.FALSE);

        List<DataApp> dataAppList = dataAppDao.selectList(paramDataApp);
        if (dataAppList != null && !dataAppList.isEmpty()) {
            for (DataApp dataApp : dataAppList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataApp.getId());
                map.put("account_name", dataApp.getAccountName());
                map.put("name", dataApp.getName());
                map.put("delivery_address", dataApp.getDeliveryAddress());
                map.put("mobile", dataApp.getMobile());
                map.put("email", dataApp.getEmail());
                map.put("order_count", dataApp.getOrderCount());
                map.put("order_amount", dataApp.getOrderAmount());
                map.put("cart_item_count", dataApp.getCartItemCount());
                map.put("favorite_item_count", dataApp.getFavoriteItemCount());

                rseult.getData().add(map);
            }
        }
    }

    private void assignPosData(BaseOutput rseult, int index, int size) {
        DataPos paramDataPos = new DataPos();
        paramDataPos.setStartIndex(index);
        paramDataPos.setPageSize(size);
        paramDataPos.setDeleted(Boolean.FALSE);

        List<DataPos> dataPosList = dataPosDao.selectList(paramDataPos);

        if (dataPosList != null && !dataPosList.isEmpty()) {
            for (DataPos dataPos : dataPosList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataPos.getId());
                map.put("account_name", dataPos.getAccountName());
                map.put("time", dataPos.getTime());
                map.put("store", dataPos.getStore());
                map.put("monetory", dataPos.getMonetary());
                map.put("sku_list", dataPos.getSkuList());
            }
        }
    }

}
