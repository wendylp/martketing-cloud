/**
 * CouponPayment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.rongcapital.mkt.webservice;

public class CouponPayment  implements java.io.Serializable {
    private int vipId;

    private int couponType;

    private double payMoney;

    public CouponPayment() {
    }

    public CouponPayment(
           int vipId,
           int couponType,
           double payMoney) {
           this.vipId = vipId;
           this.couponType = couponType;
           this.payMoney = payMoney;
    }


    /**
     * Gets the vipId value for this CouponPayment.
     * 
     * @return vipId
     */
    public int getVipId() {
        return vipId;
    }


    /**
     * Sets the vipId value for this CouponPayment.
     * 
     * @param vipId
     */
    public void setVipId(int vipId) {
        this.vipId = vipId;
    }


    /**
     * Gets the couponType value for this CouponPayment.
     * 
     * @return couponType
     */
    public int getCouponType() {
        return couponType;
    }


    /**
     * Sets the couponType value for this CouponPayment.
     * 
     * @param couponType
     */
    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }


    /**
     * Gets the payMoney value for this CouponPayment.
     * 
     * @return payMoney
     */
    public double getPayMoney() {
        return payMoney;
    }


    /**
     * Sets the payMoney value for this CouponPayment.
     * 
     * @param payMoney
     */
    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CouponPayment)) return false;
        CouponPayment other = (CouponPayment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.vipId == other.getVipId() &&
            this.couponType == other.getCouponType() &&
            this.payMoney == other.getPayMoney();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getVipId();
        _hashCode += getCouponType();
        _hashCode += new Double(getPayMoney()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CouponPayment.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CouponPayment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vipId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "VipId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CouponType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payMoney");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PayMoney"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
