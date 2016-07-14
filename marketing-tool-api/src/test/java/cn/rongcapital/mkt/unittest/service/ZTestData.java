package cn.rongcapital.mkt.unittest.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.job.service.base.BasEventExportService;
import cn.rongcapital.mkt.service.ZTest;
import cn.rongcapital.mkt.unittest.AbstractUnitTest;

public class ZTestData extends AbstractUnitTest{

    @Autowired
    private ZTest zTest; 
    
    @Autowired
    private BasEventExportService basEventExportService;

//    @Test
//	public void testImportData() throws IOException{
//        zTest.importData();
//	}
    
    @Test
    public void testBasEventExport(){
        basEventExportService.exportData();
    }
}
