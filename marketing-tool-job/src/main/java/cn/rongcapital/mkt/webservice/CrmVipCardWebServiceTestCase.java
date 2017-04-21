/**
 * CrmVipCardWebServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.rongcapital.mkt.webservice;

public class CrmVipCardWebServiceTestCase extends junit.framework.TestCase {
    public CrmVipCardWebServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void test22CrmVipCardWebServiceSoap12UpdateVipCoupon() throws Exception {
        cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap12Stub binding;
        try {
            binding = (cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap12Stub)
                          new cn.rongcapital.mkt.webservice.CrmVipCardWebServiceLocator().getCrmVipCardWebServiceSoap12();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        binding.updateVipCoupon(10000, 1, 0, 2, new java.lang.String(), new java.lang.String(), new java.lang.String(), new javax.xml.rpc.holders.BooleanHolder(), new javax.xml.rpc.holders.StringHolder());
        // TBD - validate results
    }
}
