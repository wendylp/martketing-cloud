<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://tempuri.org/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://tempuri.org/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://tempuri.org/">
      <s:element name="GetVipCard">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="condType" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="condValue" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="cardCodeToCheck" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="verifyCode" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetVipCardResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="GetVipCardResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="vipCard" type="tns:VipCard" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="VipCard">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="VipID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="CardCode" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="VipName" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="CardTypeId" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="CardTypeName" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="CanCent" type="s:boolean" />
          <s:element minOccurs="1" maxOccurs="1" name="CanOwnCoupon" type="s:boolean" />
          <s:element minOccurs="1" maxOccurs="1" name="CanDisc" type="s:boolean" />
          <s:element minOccurs="1" maxOccurs="1" name="ValidCent" type="s:double" />
          <s:element minOccurs="1" maxOccurs="1" name="YearCent" type="s:double" />
          <s:element minOccurs="1" maxOccurs="1" name="StageCent" type="s:double" />
          <s:element minOccurs="0" maxOccurs="1" name="Hello" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="IdCardCode" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="QYCode" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Birthday" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="Sex" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Phone" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Address" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="EMail" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="Cent_ToExpire" type="s:double" />
          <s:element minOccurs="0" maxOccurs="1" name="storeCode" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="BabyInfolist" type="tns:ArrayOfBabyInfo" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfBabyInfo">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="BabyInfo" nillable="true" type="tns:BabyInfo" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="BabyInfo">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="VipId" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="BBCount" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="Sex" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Birthday" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="DueDate" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="CrmSoapHeader" type="tns:CrmSoapHeader" />
      <s:complexType name="CrmSoapHeader">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="UserId" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
        </s:sequence>
        <s:anyAttribute />
      </s:complexType>
      <s:element name="RegisterInfoBind">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="vipid_in" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="Phone" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="RegisterInfoBindResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="RegisterInfoBindResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="vipId" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="VipRegisterInfo">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Phone" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="VipName" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="Sex" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="Birthday" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="QYCODE" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="storeCode" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="BKRDM" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="BabyInfolist" type="tns:ArrayOfBabyInfo" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="VipRegisterInfoResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="VipRegisterInfoResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="VipCode" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="VipId" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="UpdateMemberInfo">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="vipId" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="VipName" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="Birthday" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="Sex" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="Adrress" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="Email" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="storeCode" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="QYCODE" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="BabyInfolist" type="tns:ArrayOfBabyInfo" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="UpdateMemberInfoResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="UpdateMemberInfoResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="UpdateVipCent">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="vipId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="updateCent" type="s:double" />
            <s:element minOccurs="1" maxOccurs="1" name="updateType" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="storeCode" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="posId" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="billId" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="UpdateVipCentResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="UpdateVipCentResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetVipCoupon">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="condType" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="condValue" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="cardCodeToCheck" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="verifyCode" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="storeCode" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="requireValidDate" type="s:boolean" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetVipCouponResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="GetVipCouponResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="vipId" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="vipCode" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="coupons" type="tns:ArrayOfCoupon" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ArrayOfCoupon">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="Coupon" nillable="true" type="tns:Coupon" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="Coupon">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="CouponType" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="CouponTypeName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="BeginDate" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ValidDate" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="Balance" type="s:double" />
        </s:sequence>
      </s:complexType>
      <s:element name="UpdateVipCoupon">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="vipId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="couponId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="actionId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="couponMoney" type="s:double" />
            <s:element minOccurs="0" maxOccurs="1" name="CanUseBeginDate" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="CanUseEndDate" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="storeCode" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="UpdateVipCouponResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="UpdateVipCouponResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetVipScoreItem">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="vipid" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="time_Begin" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="time_End" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetVipScoreItemResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="GetVipScoreItemResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="VipScoreItems" type="tns:ArrayOfVipScoreItem" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ArrayOfVipScoreItem">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="VipScoreItem" nillable="true" type="tns:VipScoreItem" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="VipScoreItem">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Procdate" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Proctype" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="socre" type="s:double" />
        </s:sequence>
      </s:complexType>
      <s:element name="PrepareTransCouponPayment2">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="storeCode" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="posId" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="billId" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="cashier" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="accountDate" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="payments" type="tns:ArrayOfCouponPayment" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ArrayOfCouponPayment">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="CouponPayment" nillable="true" type="tns:CouponPayment" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="CouponPayment">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="VipId" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="CouponType" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="PayMoney" type="s:double" />
        </s:sequence>
      </s:complexType>
      <s:element name="PrepareTransCouponPayment2Response">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="PrepareTransCouponPayment2Result" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="transId" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ConfirmTransCouponPayment">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="transId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="serverBillId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="transMoney" type="s:double" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ConfirmTransCouponPaymentResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="ConfirmTransCouponPaymentResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="CancelTransCouponPayment">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="transId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="serverBillId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="transMoney" type="s:double" />
            <s:element minOccurs="1" maxOccurs="1" name="isweb" type="s:boolean" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="CancelTransCouponPaymentResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="CancelTransCouponPaymentResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="PrepareTransVipCoupon">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="vip_dest" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="phone" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="storeCode" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="posId" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="billId" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="payments" type="tns:ArrayOfCouponPayment" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="PrepareTransVipCouponResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="PrepareTransVipCouponResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="transId" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ConfirmTransVipCoupon">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="transId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="serverBillId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="transMoney" type="s:double" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ConfirmTransVipCouponResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="ConfirmTransVipCouponResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="CancelTransVipCoupon">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="transId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="serverBillId" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="transMoney" type="s:double" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="CancelTransVipCouponResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="CancelTransVipCouponResult" type="s:boolean" />
            <s:element minOccurs="0" maxOccurs="1" name="msg" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="Test">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="s" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TestResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="TestResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="GetVipCardSoapIn">
    <wsdl:part name="parameters" element="tns:GetVipCard" />
  </wsdl:message>
  <wsdl:message name="GetVipCardSoapOut">
    <wsdl:part name="parameters" element="tns:GetVipCardResponse" />
  </wsdl:message>
  <wsdl:message name="GetVipCardCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="RegisterInfoBindSoapIn">
    <wsdl:part name="parameters" element="tns:RegisterInfoBind" />
  </wsdl:message>
  <wsdl:message name="RegisterInfoBindSoapOut">
    <wsdl:part name="parameters" element="tns:RegisterInfoBindResponse" />
  </wsdl:message>
  <wsdl:message name="RegisterInfoBindCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="VipRegisterInfoSoapIn">
    <wsdl:part name="parameters" element="tns:VipRegisterInfo" />
  </wsdl:message>
  <wsdl:message name="VipRegisterInfoSoapOut">
    <wsdl:part name="parameters" element="tns:VipRegisterInfoResponse" />
  </wsdl:message>
  <wsdl:message name="VipRegisterInfoCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="UpdateMemberInfoSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateMemberInfo" />
  </wsdl:message>
  <wsdl:message name="UpdateMemberInfoSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateMemberInfoResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateMemberInfoCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="UpdateVipCentSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateVipCent" />
  </wsdl:message>
  <wsdl:message name="UpdateVipCentSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateVipCentResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateVipCentCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="GetVipCouponSoapIn">
    <wsdl:part name="parameters" element="tns:GetVipCoupon" />
  </wsdl:message>
  <wsdl:message name="GetVipCouponSoapOut">
    <wsdl:part name="parameters" element="tns:GetVipCouponResponse" />
  </wsdl:message>
  <wsdl:message name="GetVipCouponCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="UpdateVipCouponSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateVipCoupon" />
  </wsdl:message>
  <wsdl:message name="UpdateVipCouponSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateVipCouponResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateVipCouponCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="GetVipScoreItemSoapIn">
    <wsdl:part name="parameters" element="tns:GetVipScoreItem" />
  </wsdl:message>
  <wsdl:message name="GetVipScoreItemSoapOut">
    <wsdl:part name="parameters" element="tns:GetVipScoreItemResponse" />
  </wsdl:message>
  <wsdl:message name="GetVipScoreItemCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="PrepareTransCouponPayment2SoapIn">
    <wsdl:part name="parameters" element="tns:PrepareTransCouponPayment2" />
  </wsdl:message>
  <wsdl:message name="PrepareTransCouponPayment2SoapOut">
    <wsdl:part name="parameters" element="tns:PrepareTransCouponPayment2Response" />
  </wsdl:message>
  <wsdl:message name="PrepareTransCouponPayment2CrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="ConfirmTransCouponPaymentSoapIn">
    <wsdl:part name="parameters" element="tns:ConfirmTransCouponPayment" />
  </wsdl:message>
  <wsdl:message name="ConfirmTransCouponPaymentSoapOut">
    <wsdl:part name="parameters" element="tns:ConfirmTransCouponPaymentResponse" />
  </wsdl:message>
  <wsdl:message name="ConfirmTransCouponPaymentCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="CancelTransCouponPaymentSoapIn">
    <wsdl:part name="parameters" element="tns:CancelTransCouponPayment" />
  </wsdl:message>
  <wsdl:message name="CancelTransCouponPaymentSoapOut">
    <wsdl:part name="parameters" element="tns:CancelTransCouponPaymentResponse" />
  </wsdl:message>
  <wsdl:message name="CancelTransCouponPaymentCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="PrepareTransVipCouponSoapIn">
    <wsdl:part name="parameters" element="tns:PrepareTransVipCoupon" />
  </wsdl:message>
  <wsdl:message name="PrepareTransVipCouponSoapOut">
    <wsdl:part name="parameters" element="tns:PrepareTransVipCouponResponse" />
  </wsdl:message>
  <wsdl:message name="PrepareTransVipCouponCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="ConfirmTransVipCouponSoapIn">
    <wsdl:part name="parameters" element="tns:ConfirmTransVipCoupon" />
  </wsdl:message>
  <wsdl:message name="ConfirmTransVipCouponSoapOut">
    <wsdl:part name="parameters" element="tns:ConfirmTransVipCouponResponse" />
  </wsdl:message>
  <wsdl:message name="ConfirmTransVipCouponCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="CancelTransVipCouponSoapIn">
    <wsdl:part name="parameters" element="tns:CancelTransVipCoupon" />
  </wsdl:message>
  <wsdl:message name="CancelTransVipCouponSoapOut">
    <wsdl:part name="parameters" element="tns:CancelTransVipCouponResponse" />
  </wsdl:message>
  <wsdl:message name="CancelTransVipCouponCrmSoapHeader">
    <wsdl:part name="CrmSoapHeader" element="tns:CrmSoapHeader" />
  </wsdl:message>
  <wsdl:message name="TestSoapIn">
    <wsdl:part name="parameters" element="tns:Test" />
  </wsdl:message>
  <wsdl:message name="TestSoapOut">
    <wsdl:part name="parameters" element="tns:TestResponse" />
  </wsdl:message>
  <wsdl:portType name="CrmVipCardWebServiceSoap">
    <wsdl:operation name="GetVipCard">
      <wsdl:input message="tns:GetVipCardSoapIn" />
      <wsdl:output message="tns:GetVipCardSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="RegisterInfoBind">
      <wsdl:input message="tns:RegisterInfoBindSoapIn" />
      <wsdl:output message="tns:RegisterInfoBindSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="VipRegisterInfo">
      <wsdl:input message="tns:VipRegisterInfoSoapIn" />
      <wsdl:output message="tns:VipRegisterInfoSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateMemberInfo">
      <wsdl:input message="tns:UpdateMemberInfoSoapIn" />
      <wsdl:output message="tns:UpdateMemberInfoSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateVipCent">
      <wsdl:input message="tns:UpdateVipCentSoapIn" />
      <wsdl:output message="tns:UpdateVipCentSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetVipCoupon">
      <wsdl:input message="tns:GetVipCouponSoapIn" />
      <wsdl:output message="tns:GetVipCouponSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateVipCoupon">
      <wsdl:input message="tns:UpdateVipCouponSoapIn" />
      <wsdl:output message="tns:UpdateVipCouponSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetVipScoreItem">
      <wsdl:input message="tns:GetVipScoreItemSoapIn" />
      <wsdl:output message="tns:GetVipScoreItemSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="PrepareTransCouponPayment2">
      <wsdl:input message="tns:PrepareTransCouponPayment2SoapIn" />
      <wsdl:output message="tns:PrepareTransCouponPayment2SoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ConfirmTransCouponPayment">
      <wsdl:input message="tns:ConfirmTransCouponPaymentSoapIn" />
      <wsdl:output message="tns:ConfirmTransCouponPaymentSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CancelTransCouponPayment">
      <wsdl:input message="tns:CancelTransCouponPaymentSoapIn" />
      <wsdl:output message="tns:CancelTransCouponPaymentSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="PrepareTransVipCoupon">
      <wsdl:input message="tns:PrepareTransVipCouponSoapIn" />
      <wsdl:output message="tns:PrepareTransVipCouponSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ConfirmTransVipCoupon">
      <wsdl:input message="tns:ConfirmTransVipCouponSoapIn" />
      <wsdl:output message="tns:ConfirmTransVipCouponSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CancelTransVipCoupon">
      <wsdl:input message="tns:CancelTransVipCouponSoapIn" />
      <wsdl:output message="tns:CancelTransVipCouponSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="Test">
      <wsdl:input message="tns:TestSoapIn" />
      <wsdl:output message="tns:TestSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="CrmVipCardWebServiceSoap" type="tns:CrmVipCardWebServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="GetVipCard">
      <soap:operation soapAction="http://tempuri.org/GetVipCard" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:GetVipCardCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RegisterInfoBind">
      <soap:operation soapAction="http://tempuri.org/RegisterInfoBind" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:RegisterInfoBindCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="VipRegisterInfo">
      <soap:operation soapAction="http://tempuri.org/VipRegisterInfo" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:VipRegisterInfoCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateMemberInfo">
      <soap:operation soapAction="http://tempuri.org/UpdateMemberInfo" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateMemberInfoCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateVipCent">
      <soap:operation soapAction="http://tempuri.org/UpdateVipCent" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateVipCentCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetVipCoupon">
      <soap:operation soapAction="http://tempuri.org/GetVipCoupon" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:GetVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateVipCoupon">
      <soap:operation soapAction="http://tempuri.org/UpdateVipCoupon" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetVipScoreItem">
      <soap:operation soapAction="http://tempuri.org/GetVipScoreItem" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:GetVipScoreItemCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="PrepareTransCouponPayment2">
      <soap:operation soapAction="http://tempuri.org/PrepareTransCouponPayment2" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:PrepareTransCouponPayment2CrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ConfirmTransCouponPayment">
      <soap:operation soapAction="http://tempuri.org/ConfirmTransCouponPayment" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ConfirmTransCouponPaymentCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CancelTransCouponPayment">
      <soap:operation soapAction="http://tempuri.org/CancelTransCouponPayment" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CancelTransCouponPaymentCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="PrepareTransVipCoupon">
      <soap:operation soapAction="http://tempuri.org/PrepareTransVipCoupon" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:PrepareTransVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ConfirmTransVipCoupon">
      <soap:operation soapAction="http://tempuri.org/ConfirmTransVipCoupon" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ConfirmTransVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CancelTransVipCoupon">
      <soap:operation soapAction="http://tempuri.org/CancelTransVipCoupon" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CancelTransVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="Test">
      <soap:operation soapAction="http://tempuri.org/Test" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="CrmVipCardWebServiceSoap12" type="tns:CrmVipCardWebServiceSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="GetVipCard">
      <soap12:operation soapAction="http://tempuri.org/GetVipCard" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:GetVipCardCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RegisterInfoBind">
      <soap12:operation soapAction="http://tempuri.org/RegisterInfoBind" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:RegisterInfoBindCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="VipRegisterInfo">
      <soap12:operation soapAction="http://tempuri.org/VipRegisterInfo" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:VipRegisterInfoCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateMemberInfo">
      <soap12:operation soapAction="http://tempuri.org/UpdateMemberInfo" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateMemberInfoCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateVipCent">
      <soap12:operation soapAction="http://tempuri.org/UpdateVipCent" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateVipCentCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetVipCoupon">
      <soap12:operation soapAction="http://tempuri.org/GetVipCoupon" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:GetVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateVipCoupon">
      <soap12:operation soapAction="http://tempuri.org/UpdateVipCoupon" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetVipScoreItem">
      <soap12:operation soapAction="http://tempuri.org/GetVipScoreItem" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:GetVipScoreItemCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="PrepareTransCouponPayment2">
      <soap12:operation soapAction="http://tempuri.org/PrepareTransCouponPayment2" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:PrepareTransCouponPayment2CrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ConfirmTransCouponPayment">
      <soap12:operation soapAction="http://tempuri.org/ConfirmTransCouponPayment" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ConfirmTransCouponPaymentCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CancelTransCouponPayment">
      <soap12:operation soapAction="http://tempuri.org/CancelTransCouponPayment" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CancelTransCouponPaymentCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="PrepareTransVipCoupon">
      <soap12:operation soapAction="http://tempuri.org/PrepareTransVipCoupon" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:PrepareTransVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ConfirmTransVipCoupon">
      <soap12:operation soapAction="http://tempuri.org/ConfirmTransVipCoupon" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ConfirmTransVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CancelTransVipCoupon">
      <soap12:operation soapAction="http://tempuri.org/CancelTransVipCoupon" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CancelTransVipCouponCrmSoapHeader" part="CrmSoapHeader" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="Test">
      <soap12:operation soapAction="http://tempuri.org/Test" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="CrmVipCardWebService">
    <wsdl:port name="CrmVipCardWebServiceSoap" binding="tns:CrmVipCardWebServiceSoap">
      <soap:address location="http://113.247.253.35:366/CrmVipCardWebService.asmx" />
    </wsdl:port>
    <wsdl:port name="CrmVipCardWebServiceSoap12" binding="tns:CrmVipCardWebServiceSoap12">
      <soap12:address location="http://113.247.253.35:366/CrmVipCardWebService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>