package cn.rongcapital.mkt.job.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.job.service.DataPartySyncService;
import cn.rongcapital.mkt.job.test.GlobalConfiguration;
import cn.rongcapital.mkt.service.OriginalDataArchPointScheduleService;
import cn.rongcapital.mkt.service.OriginalDataPopulationService;
import cn.rongcapital.mkt.service.OriginalDataShoppingScheduleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GlobalConfiguration.class)
public class OriginalSyncServiceTest {

    @Autowired
    private OriginalDataArchPointScheduleService originalDataArchPointScheduleService;

    @Autowired
    private OriginalDataPopulationService originalDataPopulationService;

    @Autowired
    private OriginalDataShoppingScheduleService originalDataShoppingScheduleService;

    @Autowired
    @Qualifier("dataPopulationToDataParty")
    private DataPartySyncService<Integer> dataPopulationToDataParty;

    @Autowired
    @Qualifier("dataShoppingToDataParty")
    private DataPartySyncService<Integer> dataShoppingToDataParty;

    @Test
    public void sync() {
        //originalDataPopulationService.cleanData();
        //originalDataShoppingScheduleService.cleanData();

        dataPopulationToDataParty.doSync();
        dataShoppingToDataParty.doSync();
    }
}