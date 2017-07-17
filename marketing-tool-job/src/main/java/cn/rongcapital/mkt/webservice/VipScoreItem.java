/**
 * VipScoreItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.rongcapital.mkt.webservice;

public class VipScoreItem  implements java.io.Serializable {
    private java.lang.String procdate;

    private java.lang.String proctype;

    private double socre;

    public VipScoreItem() {
    }

    public VipScoreItem(
           java.lang.String procdate,
           java.lang.String proctype,
           double socre) {
           this.procdate = procdate;
           this.proctype = proctype;
           this.socre = socre;
    }


    /**
     * Gets the procdate value for this VipScoreItem.
     * 
     * @return procdate
     */
    public java.lang.String getProcdate() {
        return procdate;
    }


    /**
     * Sets the procdate value for this VipScoreItem.
     * 
     * @param procdate
     */
    public void setProcdate(java.lang.String procdate) {
        this.procdate = procdate;
    }


    /**
     * Gets the proctype value for this VipScoreItem.
     * 
     * @return proctype
     */
    public java.lang.String getProctype() {
        return proctype;
    }


    /**
     * Sets the proctype value for this VipScoreItem.
     * 
     * @param proctype
     */
    public void setProctype(java.lang.String proctype) {
        this.proctype = proctype;
    }


    /**
     * Gets the socre value for this VipScoreItem.
     * 
     * @return socre
     */
    public double getSocre() {
        return socre;
    }


    /**
     * Sets the socre value for this VipScoreItem.
     * 
     * @param socre
     */
    public void setSocre(double socre) {
        this.socre = socre;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VipScoreItem)) return false;
        VipScoreItem other = (VipScoreItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.procdate==null && other.getProcdate()==null) || 
             (this.procdate!=null &&
              this.procdate.equals(other.getProcdate()))) &&
            ((this.proctype==null && other.getProctype()==null) || 
             (this.proctype!=null &&
              this.proctype.equals(other.getProctype()))) &&
            this.socre == other.getSocre();
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
        if (getProcdate() != null) {
            _hashCode += getProcdate().hashCode();
        }
        if (getProctype() != null) {
            _hashCode += getProctype().hashCode();
        }
        _hashCode += new Double(getSocre()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VipScoreItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "VipScoreItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procdate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Procdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proctype");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Proctype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("socre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "socre"));
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
