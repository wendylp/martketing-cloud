/**
 * CrmVipCardWebServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.rongcapital.mkt.webservice;

public interface CrmVipCardWebServiceSoap_PortType extends java.rmi.Remote {
    public void updateVipCoupon(int vipId, int couponId, int actionId, double couponMoney, java.lang.String canUseBeginDate, java.lang.String canUseEndDate, java.lang.String storeCode, javax.xml.rpc.holders.BooleanHolder updateVipCouponResult, javax.xml.rpc.holders.StringHolder msg) throws java.rmi.RemoteException;

}
