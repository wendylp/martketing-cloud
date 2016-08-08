package cn.rongcapital.mkt.unittest.service;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.service.TagRelatedImporter;
import cn.rongcapital.mkt.unittest.AbstractUnitTest;

public class TagRelatedTest extends AbstractUnitTest{

    @Autowired
    private TagRelatedImporter tagRelatedImporter; 
    
//    @Autowired
//    private BasEventExportService basEventExportService;

    @Test
	public void testImportData() throws IOException{
        tagRelatedImporter.importData();
	}
    
//    @Test
//    public void testBasEventExport(){
//        basEventExportService.exportData();
//    }
}
