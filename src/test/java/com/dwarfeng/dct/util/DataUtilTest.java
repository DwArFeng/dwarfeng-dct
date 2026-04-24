package com.dwarfeng.dct.util;

import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DataUtilTest {

    private static final LongIdKey KEY = new LongIdKey(1L);

    @Test
    public void testGetHappenedInstant() {
        Date happened = new Date(724608000000L);
        int offset = 123456;
        Data data = new GeneralData(KEY, "v", happened, offset);
        Assert.assertEquals(
                TimeUtil.toInstant(happened, offset),
                DataUtil.getHappenedInstant(data)
        );
    }

    @Test(expected = NullPointerException.class)
    public void testGetHappenedInstantNullData() {
        DataUtil.getHappenedInstant(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHappenedInstantIllegalNanoOffset() {
        Data data = new GeneralData(KEY, "v", new Date(0L), TimeUtil.MAX_NANO_OFFSET + 1);
        DataUtil.getHappenedInstant(data);
    }
}
