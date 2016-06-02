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
import cn.rongcapital.mkt.dao.DataCrmDao;
import cn.rongcapital.mkt.dao.DataEshopDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPersonalDao;
import cn.rongcapital.mkt.dao.DataPosDao;
import cn.rongcapital.mkt.dao.DataPublicDao;
import cn.rongcapital.mkt.dao.DataTmallDao;
import cn.rongcapital.mkt.po.DataApp;
import cn.rongcapital.mkt.po.DataCrm;
import cn.rongcapital.mkt.po.DataEshop;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPersonal;
import cn.rongcapital.mkt.po.DataPos;
import cn.rongcapital.mkt.po.DataPublic;
import cn.rongcapital.mkt.po.DataTmall;
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

    @Autowired
    private DataPublicDao dataPublicDao;

    @Autowired
    private DataPersonalDao dataPersonalDao;

    @Autowired
    private DataEshopDao dataEshopDao;

    @Autowired
    private DataCrmDao dataCrmDao;

    @Autowired
    private DataTmallDao dataTmallDao;

    @Override
    public Object getMainList(String method, String userToken, Integer dataType, Integer index,
                    Integer size, String ver) {

        BaseOutput rseult = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        // 这代码写的太2了
        if (dataType == DataTypeEnum.PARTY.getCode()) {
            assignPartyData(rseult, index, size);
        } else if (dataType == DataTypeEnum.APP.getCode()) {
            assignAppData(rseult, index, size);
        } else if (dataType == DataTypeEnum.POS.getCode()) {
            assignPosData(rseult, index, size);
        } else if (dataType == DataTypeEnum.PUBLIC.getCode()) {
            assignPublicData(rseult, index, size);
        } else if (dataType == DataTypeEnum.PERSONAL.getCode()) {
            assignPersonalData(rseult, index, size);
        } else if (dataType == DataTypeEnum.ESHOP.getCode()) {
            assignEshopData(rseult, index, size);
        } else if (dataType == DataTypeEnum.CRM.getCode()) {
            assignCrmData(rseult, index, size);
        } else if (dataType == DataTypeEnum.TMALL.getCode()) {
            assignTmallData(rseult, index, size);
        }

        rseult.setTotal(rseult.getData().size());

        return Response.ok().entity(rseult).build();
    }

    private void assignPartyData(BaseOutput result, int index, int size) {

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
                map.put("child_amount", dataParty.getChildAmount());
                map.put("child_annual_budget", dataParty.getChildAnnualBudget());

                result.getData().add(map);
            }
        }
    }

    private void assignAppData(BaseOutput result, int index, int size) {
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

                result.getData().add(map);
            }
        }
    }

    private void assignPosData(BaseOutput result, int index, int size) {
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

                result.getData().add(map);
            }
        }
    }

    private void assignPublicData(BaseOutput result, int index, int size) {
        DataPublic paramDataPublic = new DataPublic();
        paramDataPublic.setStartIndex(index);
        paramDataPublic.setPageSize(size);
        paramDataPublic.setDeleted(Boolean.FALSE);

        List<DataPublic> dataPublicList = dataPublicDao.selectList(paramDataPublic);

        if (dataPublicList != null && !dataPublicList.isEmpty()) {
            for (DataPublic dataPublic : dataPublicList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataPublic.getId());
                map.put("open_id", dataPublic.getOpenId());
                map.put("public_name", dataPublic.getPublicName());
                map.put("nick_name", dataPublic.getNickName());
                map.put("icon_url", dataPublic.getIconUrl());
                map.put("gender", dataPublic.getGender());
                map.put("area", dataPublic.getArea());

                result.getData().add(map);
            }
        }
    }

    private void assignPersonalData(BaseOutput result, int index, int size) {
        DataPersonal paramDataPersonal = new DataPersonal();
        paramDataPersonal.setStartIndex(index);
        paramDataPersonal.setPageSize(size);
        paramDataPersonal.setDeleted(Boolean.FALSE);

        List<DataPersonal> dataPersonalList = dataPersonalDao.selectList(paramDataPersonal);

        if (dataPersonalList != null && !dataPersonalList.isEmpty()) {
            for (DataPersonal dataPersonal : dataPersonalList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataPersonal.getId());
                map.put("open_id", dataPersonal.getOpenId());
                map.put("personal_name", dataPersonal.getPersonalName());
                map.put("nick_name", dataPersonal.getNickName());

                result.getData().add(map);
            }
        }
    }

    private void assignEshopData(BaseOutput result, int index, int size) {
        DataEshop paramDataEshop = new DataEshop();
        paramDataEshop.setStartIndex(index);
        paramDataEshop.setPageSize(size);
        paramDataEshop.setDeleted(Boolean.FALSE);

        List<DataEshop> dataEshopList = dataEshopDao.selectList(paramDataEshop);

        if (dataEshopList != null && !dataEshopList.isEmpty()) {
            for (DataEshop dataEshop : dataEshopList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataEshop.getId());
                map.put("account_name", dataEshop.getAccountName());
                map.put("name", dataEshop.getName());
                map.put("delivery_address", dataEshop.getDeliveryAddress());
                map.put("mobile", dataEshop.getMobile());
                map.put("email", dataEshop.getEmail());
                map.put("order_count", dataEshop.getOrderCount());
                map.put("order_amount", dataEshop.getOrderAmount());
                map.put("cart_iterm_count", dataEshop.getCartItemCount());
                map.put("favorite_item_count", dataEshop.getFavoriteItemCount());

                result.getData().add(map);
            }
        }
    }

    private void assignCrmData(BaseOutput result, int index, int size) {
        DataCrm paramDataCrm = new DataCrm();
        paramDataCrm.setStartIndex(index);
        paramDataCrm.setPageSize(size);
        paramDataCrm.setDeleted(Boolean.FALSE);

        List<DataCrm> dataCrmList = dataCrmDao.selectList(paramDataCrm);

        if (dataCrmList != null && !dataCrmList.isEmpty()) {
            for (DataCrm dataCrm : dataCrmList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataCrm.getId());
                map.put("account_name", dataCrm.getAccountName());
                map.put("name", dataCrm.getName());
                map.put("gender", dataCrm.getGender());
                map.put("age", dataCrm.getAge());
                map.put("home_address", dataCrm.getHomeAddress());
                map.put("work_address", dataCrm.getWorkAddress());
                map.put("home_status", dataCrm.getHomeStatus());
                map.put("work_status", dataCrm.getWorkStatus());
                map.put("member_level", dataCrm.getMemberLevel());
                map.put("mobile", dataCrm.getMobile());
                map.put("email", dataCrm.getEmail());
                map.put("wechat", dataCrm.getWechat());
                map.put("qq", dataCrm.getQq());
                map.put("weibo", dataCrm.getWeibo());

                result.getData().add(map);
            }
        }
    }

    private void assignTmallData(BaseOutput result, int index, int size) {
        DataTmall paramDataTmall = new DataTmall();
        paramDataTmall.setStartIndex(index);
        paramDataTmall.setPageSize(size);
        paramDataTmall.setDeleted(Boolean.FALSE);

        List<DataTmall> dataTmallList = dataTmallDao.selectList(paramDataTmall);

        if (dataTmallList != null && !dataTmallList.isEmpty()) {
            for (DataTmall dataTmall : dataTmallList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataTmall.getId());
                map.put("account_name", dataTmall.getAccountName());
                map.put("name", dataTmall.getName());
                map.put("delivery_address", dataTmall.getDeliveryAddress());
                map.put("mobile", dataTmall.getMobile());
                map.put("email", dataTmall.getEmail());
                map.put("order_count", dataTmall.getOrderCount());
                map.put("order_amount", dataTmall.getOrderAmount());
                map.put("cart_item_count", dataTmall.getCartItemCount());
                map.put("favorite_item_count", dataTmall.getFavoriteItemCount());

                result.getData().add(map);
            }
        }
    }

}
