package cn.rongcapital.mkt.unittest.service;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.unittest.AbstractUnitTest;

public class TagRelatedTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // @Autowired
    // private TagRelatedImporter tagRelatedImporter;

    // @Autowired
    // private BasEventExportService basEventExportService;

    @Test
    private void testLogger() {
        logger.info("logger works");
    }

    // @Test
    // public void testImportData() throws IOException{
    // tagRelatedImporter.importData();
    // }

    // @Test
    // public void testBasEventExport(){
    // basEventExportService.exportData();
    // }
}
