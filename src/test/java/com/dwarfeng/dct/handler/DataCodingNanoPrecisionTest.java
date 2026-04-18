package com.dwarfeng.dct.handler;

import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.dct.util.FlatDataUtil;
import com.dwarfeng.dct.util.GeneralDataUtil;
import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.Instant;
import java.util.Date;

/**
 * 数据编码纳秒精度测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DataCodingNanoPrecisionTest {

    @Test
    public void testDataCodingNanoOffsetRoundTrip() throws Exception {
        try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-context*.xml"
        )) {
            DataCodingHandler dataCodingHandler = ctx.getBean(DataCodingHandler.class);
            GeneralData generalData = new GeneralData(new LongIdKey(12450L), "foobar", new Date(724608000000L));
            GeneralDataUtil.setHappenedInstant(generalData, Instant.ofEpochMilli(724608000000L).plusNanos(123456L));

            String encoded = dataCodingHandler.encode(generalData);
            Data decoded = dataCodingHandler.decode(encoded);

            Assert.assertTrue(encoded.contains("\"happened_date_nano_offset\":123456"));
            Assert.assertEquals(generalData.getHappenedDate(), decoded.getHappenedDate());
            Assert.assertEquals(generalData.getHappenedDateNanoOffset(), decoded.getHappenedDateNanoOffset());
        }
    }

    @Test
    public void testOldJsonCompatibility() throws Exception {
        try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/application-context*.xml"
        )) {
            DataCodingHandler dataCodingHandler = ctx.getBean(DataCodingHandler.class);
            String oldJson =
                    "{\"point_key\":{\"long_id\":12450},\"value\":\"string:foobar\",\"happened_date\":724608000000}";

            Data decoded = dataCodingHandler.decode(oldJson);

            Assert.assertEquals(0, decoded.getHappenedDateNanoOffset());
        }
    }

    @Test
    public void testGeneralDataTimeUtilInstantRoundTrip() {
        GeneralData generalData = new GeneralData(new LongIdKey(12450L), "foobar", new Date(724608000000L));
        Instant expected = Instant.ofEpochMilli(724608000000L).plusNanos(TimeUtil.MAX_NANO_OFFSET);

        GeneralDataUtil.setHappenedInstant(generalData, expected);
        Instant actual = GeneralDataUtil.getHappenedInstant(generalData);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testFlatDataTimeUtilNullCheck() {
        FlatDataUtil.setHappenedInstant(null, Instant.now());
    }

    @Test
    public void testFlatDataTimeUtilInstantRoundTrip() {
        FlatData flatData = new FlatData(new LongIdKey(12450L), "string:foobar", new Date(724608000000L));
        Instant expected = Instant.ofEpochMilli(724608000000L).plusNanos(654321L);

        FlatDataUtil.setHappenedInstant(flatData, expected);
        Instant actual = FlatDataUtil.getHappenedInstant(flatData);

        Assert.assertEquals(expected, actual);
    }
}
