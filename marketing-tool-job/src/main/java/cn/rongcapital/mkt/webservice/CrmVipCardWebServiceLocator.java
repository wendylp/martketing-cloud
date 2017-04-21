/**
 * CrmVipCardWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.rongcapital.mkt.webservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class CrmVipCardWebServiceLocator extends org.apache.axis.client.Service implements cn.rongcapital.mkt.webservice.CrmVipCardWebService {

    
    
    public CrmVipCardWebServiceLocator() {
    }
    public CrmVipCardWebServiceLocator(String crmVipCardWebServiceSoap_address) {
        this.CrmVipCardWebServiceSoap_address = crmVipCardWebServiceSoap_address;
    }



    public CrmVipCardWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CrmVipCardWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CrmVipCardWebServiceSoap
    private java.lang.String CrmVipCardWebServiceSoap_address = "http://113.247.253.35:366/CrmVipCardWebService.asmx";

    public java.lang.String getCrmVipCardWebServiceSoapAddress() {
        return CrmVipCardWebServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CrmVipCardWebServiceSoapWSDDServiceName = "CrmVipCardWebServiceSoap";

    public java.lang.String getCrmVipCardWebServiceSoapWSDDServiceName() {
        return CrmVipCardWebServiceSoapWSDDServiceName;
    }

    public void setCrmVipCardWebServiceSoapWSDDServiceName(java.lang.String name) {
        CrmVipCardWebServiceSoapWSDDServiceName = name;
    }

    public cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_PortType getCrmVipCardWebServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CrmVipCardWebServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCrmVipCardWebServiceSoap(endpoint);
    }

    public cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_PortType getCrmVipCardWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_BindingStub _stub = new cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_BindingStub(portAddress, this);
            _stub.setPortName(getCrmVipCardWebServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCrmVipCardWebServiceSoapEndpointAddress(java.lang.String address) {
        CrmVipCardWebServiceSoap_address = address;
    }


    // Use to get a proxy class for CrmVipCardWebServiceSoap12
    private java.lang.String CrmVipCardWebServiceSoap12_address = "http://113.247.253.35:366/CrmVipCardWebService.asmx";

    public java.lang.String getCrmVipCardWebServiceSoap12Address() {
        return CrmVipCardWebServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CrmVipCardWebServiceSoap12WSDDServiceName = "CrmVipCardWebServiceSoap12";

    public java.lang.String getCrmVipCardWebServiceSoap12WSDDServiceName() {
        return CrmVipCardWebServiceSoap12WSDDServiceName;
    }

    public void setCrmVipCardWebServiceSoap12WSDDServiceName(java.lang.String name) {
        CrmVipCardWebServiceSoap12WSDDServiceName = name;
    }

    public cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_PortType getCrmVipCardWebServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CrmVipCardWebServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCrmVipCardWebServiceSoap12(endpoint);
    }

    public cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_PortType getCrmVipCardWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap12Stub _stub = new cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getCrmVipCardWebServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCrmVipCardWebServiceSoap12EndpointAddress(java.lang.String address) {
        CrmVipCardWebServiceSoap12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_BindingStub _stub = new cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_BindingStub(new java.net.URL(CrmVipCardWebServiceSoap_address), this);
                _stub.setPortName(getCrmVipCardWebServiceSoapWSDDServiceName());
                return _stub;
            }
            if (cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap12Stub _stub = new cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap12Stub(new java.net.URL(CrmVipCardWebServiceSoap12_address), this);
                _stub.setPortName(getCrmVipCardWebServiceSoap12WSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CrmVipCardWebServiceSoap".equals(inputPortName)) {
            return getCrmVipCardWebServiceSoap();
        }
        else if ("CrmVipCardWebServiceSoap12".equals(inputPortName)) {
            return getCrmVipCardWebServiceSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "CrmVipCardWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "CrmVipCardWebServiceSoap"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "CrmVipCardWebServiceSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CrmVipCardWebServiceSoap".equals(portName)) {
            setCrmVipCardWebServiceSoapEndpointAddress(address);
        }
        else 
if ("CrmVipCardWebServiceSoap12".equals(portName)) {
            setCrmVipCardWebServiceSoap12EndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
