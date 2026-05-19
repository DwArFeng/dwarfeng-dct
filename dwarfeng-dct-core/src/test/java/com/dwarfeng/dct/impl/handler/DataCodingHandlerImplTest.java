package com.dwarfeng.dct.impl.handler;

import com.dwarfeng.dct.sdk.util.GeneralDataUtil;
import com.dwarfeng.dct.stack.bean.dto.GeneralData;
import com.dwarfeng.dct.stack.handler.DataCodingHandler;
import com.dwarfeng.dct.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DataCodingHandlerImplTest {

    @Autowired
    private DataCodingHandler dataCodingHandler;

    @Test
    public void testDataCodingNanoOffsetRoundTrip() throws Exception {
        GeneralData generalData = GeneralDataUtil.newInstance(
                new LongIdKey(12450L), "foobar", Instant.ofEpochMilli(724608000000L).plusNanos(123456L)
        );

        String encoded = dataCodingHandler.encode(generalData);
        Data decoded = dataCodingHandler.decode(encoded);

        Assert.assertTrue(encoded.contains("\"happened_date_nano_offset\":123456"));
        Assert.assertEquals(generalData.getHappenedDate(), decoded.getHappenedDate());
        Assert.assertEquals(generalData.getHappenedDateNanoOffset(), decoded.getHappenedDateNanoOffset());
    }

    @Test
    public void testOldJsonCompatibility() throws Exception {
        String oldJson =
                "{\"point_key\":{\"long_id\":12450},\"value\":\"string:foobar\",\"happened_date\":724608000000}";

        Data decoded = dataCodingHandler.decode(oldJson);

        Assert.assertEquals(0, decoded.getHappenedDateNanoOffset());
    }
}
