package com.dwarfeng.dct.util;

import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;

/**
 * {@link CompareUtil} 的单元测试。
 *
 * <p>
 * 测试方法顺序与 {@link CompareUtil} 中比较器声明顺序一致。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class CompareUtilTest {

    private static final LongIdKey KEY_1 = new LongIdKey(1L);
    private static final LongIdKey KEY_2 = new LongIdKey(2L);

    // region 主键比较

    @Test
    public void testLongIdKeyAsc() {
        Comparator<LongIdKey> c = CompareUtil.LONG_ID_KEY_ASC_COMPARATOR;
        Assert.assertTrue(c.compare(KEY_1, KEY_2) < 0);
        Assert.assertTrue(c.compare(KEY_2, KEY_1) > 0);
        Assert.assertEquals(0, c.compare(KEY_1, new LongIdKey(1L)));
    }

    @Test
    public void testLongIdKeyDesc() {
        Comparator<LongIdKey> c = CompareUtil.LONG_ID_KEY_DESC_COMPARATOR;
        Assert.assertTrue(c.compare(KEY_1, KEY_2) > 0);
        Assert.assertTrue(c.compare(KEY_2, KEY_1) < 0);
    }

    // endregion

    // region 日期比较

    @Test
    public void testInstantAscDesc() {
        Instant i1 = Instant.ofEpochMilli(0L);
        Instant i2 = Instant.ofEpochMilli(1L);
        Assert.assertTrue(CompareUtil.INSTANT_ASC_COMPARATOR.compare(i1, i2) < 0);
        Assert.assertTrue(CompareUtil.INSTANT_DESC_COMPARATOR.compare(i1, i2) > 0);
    }

    // endregion

    // region 数据比较

    @Test
    public void testDataDefaultComparatorPointKeyFirst() {
        Date d = new Date(1000L);
        Data a = new GeneralData(KEY_2, "x", d, 0);
        Data b = new GeneralData(KEY_1, "y", d, 0);
        Assert.assertTrue(CompareUtil.DATA_DEFAULT_COMPARATOR.compare(a, b) > 0);
    }

    @Test
    public void testDataDefaultComparatorThenInstant() {
        Date sameMs = new Date(724608000000L);
        Data earlier = new GeneralData(KEY_1, "a", sameMs, 100);
        Data later = new GeneralData(KEY_1, "b", sameMs, 200_000);
        Assert.assertTrue(CompareUtil.DATA_DEFAULT_COMPARATOR.compare(earlier, later) < 0);
    }

    @Test
    public void testDataHappenedInstantAscIgnoresPointKey() {
        Date sameMs = new Date(724608000000L);
        Data earlier = new GeneralData(KEY_2, "a", sameMs, 100);
        Data later = new GeneralData(KEY_1, "b", sameMs, 200_000);
        Assert.assertTrue(CompareUtil.DATA_HAPPENED_INSTANT_ASC_COMPARATOR.compare(earlier, later) < 0);
        Assert.assertTrue(CompareUtil.DATA_HAPPENED_INSTANT_DESC_COMPARATOR.compare(earlier, later) > 0);
    }

    @Test
    public void testDataHappenedInstantAscSameInstantEquals() {
        Date sameMs = new Date(724608000000L);
        Data a = new GeneralData(KEY_1, "a", sameMs, 100);
        Data b = new GeneralData(KEY_2, "b", sameMs, 100);
        Assert.assertEquals(0, CompareUtil.DATA_HAPPENED_INSTANT_ASC_COMPARATOR.compare(a, b));
    }

    // endregion

    // region 通用数据比较

    @Test
    public void testGeneralDataDefaultComparator() {
        Date sameMs = new Date(724608000000L);
        GeneralData a = new GeneralData(KEY_1, "a", sameMs, 0);
        GeneralData b = new GeneralData(KEY_1, "b", sameMs, TimeUtil.MAX_NANO_OFFSET);
        Assert.assertTrue(CompareUtil.GENERAL_DATA_DEFAULT_COMPARATOR.compare(a, b) < 0);
    }

    @Test
    public void testGeneralDataHappenedInstantAscDesc() {
        Date sameMs = new Date(724608000000L);
        GeneralData a = new GeneralData(KEY_1, "a", sameMs, 0);
        GeneralData b = new GeneralData(KEY_1, "b", sameMs, TimeUtil.MAX_NANO_OFFSET);
        Assert.assertTrue(CompareUtil.GENERAL_DATA_HAPPENED_INSTANT_ASC_COMPARATOR.compare(a, b) < 0);
        Assert.assertTrue(CompareUtil.GENERAL_DATA_HAPPENED_INSTANT_DESC_COMPARATOR.compare(a, b) > 0);
    }

    // endregion

    // region 扁平数据比较

    @Test
    public void testFlatDataDefaultComparator() {
        Date sameMs = new Date(724608000000L);
        FlatData a = new FlatData(KEY_1, "string:x", sameMs, 0);
        FlatData b = new FlatData(KEY_1, "string:y", sameMs, 500_000);
        Assert.assertTrue(CompareUtil.FLAT_DATA_DEFAULT_COMPARATOR.compare(a, b) < 0);
    }

    @Test
    public void testFlatDataHappenedInstantAscDesc() {
        Date sameMs = new Date(724608000000L);
        FlatData a = new FlatData(KEY_1, "string:x", sameMs, 0);
        FlatData b = new FlatData(KEY_1, "string:y", sameMs, 400_000);
        Assert.assertTrue(CompareUtil.FLAT_DATA_HAPPENED_INSTANT_ASC_COMPARATOR.compare(a, b) < 0);
        Assert.assertTrue(CompareUtil.FLAT_DATA_HAPPENED_INSTANT_DESC_COMPARATOR.compare(a, b) > 0);
    }

    // endregion
}
