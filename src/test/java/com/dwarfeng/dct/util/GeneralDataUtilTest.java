package com.dwarfeng.dct.util;

import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class GeneralDataUtilTest {

    private static final LongIdKey KEY = new LongIdKey(12450L);

    @Test
    public void testNewInstanceAndGetHappenedInstantRoundTrip() {
        Instant expected = Instant.ofEpochMilli(724608000000L).plusNanos(123456L);
        GeneralData generalData = GeneralDataUtil.newInstance(KEY, "foobar", expected);

        Assert.assertEquals(expected, GeneralDataUtil.getHappenedInstant(generalData));
    }

    @Test
    public void testNewInstanceNanoOffsetBoundaryMax() {
        Instant expected = Instant.ofEpochMilli(724608000000L).plusNanos(TimeUtil.MAX_NANO_OFFSET);
        GeneralData generalData = GeneralDataUtil.newInstance(KEY, "foobar", expected);

        Assert.assertEquals(expected, GeneralDataUtil.getHappenedInstant(generalData));
    }

    @Test
    public void testSetHappenedInstantRoundTrip() {
        GeneralData generalData = new GeneralData(KEY, "x", null, 0);
        Instant expected = Instant.ofEpochMilli(1L);
        GeneralDataUtil.setHappenedInstant(generalData, expected);

        Assert.assertEquals(expected, GeneralDataUtil.getHappenedInstant(generalData));
    }

    @Test(expected = NullPointerException.class)
    public void testGetHappenedInstantNullGeneralData() {
        GeneralDataUtil.getHappenedInstant(null);
    }

    @Test(expected = NullPointerException.class)
    public void testSetHappenedInstantNullGeneralData() {
        GeneralDataUtil.setHappenedInstant(null, Instant.now());
    }

    @Test(expected = NullPointerException.class)
    public void testSetHappenedInstantNullInstant() {
        GeneralDataUtil.setHappenedInstant(new GeneralData(), null);
    }

    @Test(expected = NullPointerException.class)
    public void testNewInstanceNullPointKey() {
        GeneralDataUtil.newInstance(null, "foobar", Instant.now());
    }

    @Test(expected = NullPointerException.class)
    public void testNewInstanceNullInstant() {
        GeneralDataUtil.newInstance(KEY, "foobar", null);
    }
}
