package com.dwarfeng.dct.util;

import com.dwarfeng.dct.bean.dto.FlatData;
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
public class FlatDataUtilTest {

    private static final LongIdKey KEY = new LongIdKey(12450L);

    @Test
    public void testNewInstanceAndGetHappenedInstantRoundTrip() {
        Instant expected = Instant.ofEpochMilli(724608000000L).plusNanos(654321L);
        FlatData flatData = FlatDataUtil.newInstance(KEY, "string:foobar", expected);

        Assert.assertEquals(expected, FlatDataUtil.getHappenedInstant(flatData));
    }

    @Test
    public void testNewInstanceNanoOffsetBoundaryMax() {
        Instant expected = Instant.ofEpochMilli(724608000000L).plusNanos(TimeUtil.MAX_NANO_OFFSET);
        FlatData flatData = FlatDataUtil.newInstance(KEY, "string:foobar", expected);

        Assert.assertEquals(expected, FlatDataUtil.getHappenedInstant(flatData));
    }

    @Test
    public void testSetHappenedInstantRoundTrip() {
        FlatData flatData = new FlatData(KEY, "string:foobar", null, 0);
        Instant expected = Instant.ofEpochMilli(1L);
        FlatDataUtil.setHappenedInstant(flatData, expected);

        Assert.assertEquals(expected, FlatDataUtil.getHappenedInstant(flatData));
    }

    @Test(expected = NullPointerException.class)
    public void testGetHappenedInstantNullFlatData() {
        FlatDataUtil.getHappenedInstant(null);
    }

    @Test(expected = NullPointerException.class)
    public void testSetHappenedInstantNullFlatData() {
        FlatDataUtil.setHappenedInstant(null, Instant.now());
    }

    @Test(expected = NullPointerException.class)
    public void testSetHappenedInstantNullInstant() {
        FlatDataUtil.setHappenedInstant(new FlatData(), null);
    }

    @Test(expected = NullPointerException.class)
    public void testNewInstanceNullPointKey() {
        FlatDataUtil.newInstance(null, "string:foobar", Instant.now());
    }

    @Test(expected = NullPointerException.class)
    public void testNewInstanceNullInstant() {
        FlatDataUtil.newInstance(KEY, "string:foobar", null);
    }
}
