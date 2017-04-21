/**
 * Coupon.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.rongcapital.mkt.webservice;

public class Coupon  implements java.io.Serializable {
    private int couponType;

    private java.lang.String couponTypeName;

    private java.lang.String beginDate;

    private java.lang.String validDate;

    private double balance;

    public Coupon() {
    }

    public Coupon(
           int couponType,
           java.lang.String couponTypeName,
           java.lang.String beginDate,
           java.lang.String validDate,
           double balance) {
           this.couponType = couponType;
           this.couponTypeName = couponTypeName;
           this.beginDate = beginDate;
           this.validDate = validDate;
           this.balance = balance;
    }


    /**
     * Gets the couponType value for this Coupon.
     * 
     * @return couponType
     */
    public int getCouponType() {
        return couponType;
    }


    /**
     * Sets the couponType value for this Coupon.
     * 
     * @param couponType
     */
    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }


    /**
     * Gets the couponTypeName value for this Coupon.
     * 
     * @return couponTypeName
     */
    public java.lang.String getCouponTypeName() {
        return couponTypeName;
    }


    /**
     * Sets the couponTypeName value for this Coupon.
     * 
     * @param couponTypeName
     */
    public void setCouponTypeName(java.lang.String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }


    /**
     * Gets the beginDate value for this Coupon.
     * 
     * @return beginDate
     */
    public java.lang.String getBeginDate() {
        return beginDate;
    }


    /**
     * Sets the beginDate value for this Coupon.
     * 
     * @param beginDate
     */
    public void setBeginDate(java.lang.String beginDate) {
        this.beginDate = beginDate;
    }


    /**
     * Gets the validDate value for this Coupon.
     * 
     * @return validDate
     */
    public java.lang.String getValidDate() {
        return validDate;
    }


    /**
     * Sets the validDate value for this Coupon.
     * 
     * @param validDate
     */
    public void setValidDate(java.lang.String validDate) {
        this.validDate = validDate;
    }


    /**
     * Gets the balance value for this Coupon.
     * 
     * @return balance
     */
    public double getBalance() {
        return balance;
    }


    /**
     * Sets the balance value for this Coupon.
     * 
     * @param balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Coupon)) return false;
        Coupon other = (Coupon) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.couponType == other.getCouponType() &&
            ((this.couponTypeName==null && other.getCouponTypeName()==null) || 
             (this.couponTypeName!=null &&
              this.couponTypeName.equals(other.getCouponTypeName()))) &&
            ((this.beginDate==null && other.getBeginDate()==null) || 
             (this.beginDate!=null &&
              this.beginDate.equals(other.getBeginDate()))) &&
            ((this.validDate==null && other.getValidDate()==null) || 
             (this.validDate!=null &&
              this.validDate.equals(other.getValidDate()))) &&
            this.balance == other.getBalance();
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
        _hashCode += getCouponType();
        if (getCouponTypeName() != null) {
            _hashCode += getCouponTypeName().hashCode();
        }
        if (getBeginDate() != null) {
            _hashCode += getBeginDate().hashCode();
        }
        if (getValidDate() != null) {
            _hashCode += getValidDate().hashCode();
        }
        _hashCode += new Double(getBalance()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Coupon.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "Coupon"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CouponType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("couponTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CouponTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "BeginDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ValidDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Balance"));
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
