package com.dwarfeng.dct.sdk.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * {@link ServiceExceptionCodes} 测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ServiceExceptionCodesTest {

    @Test
    public void testDefaultOffset() {
        Assert.assertEquals(30000, ServiceExceptionCodes.getExceptionCodeOffset());
        Assert.assertEquals(30000, ServiceExceptionCodes.DATA_CODING_FAILED.getCode());
        Assert.assertEquals(30001, ServiceExceptionCodes.FLAT_DATA_CODEC_FAILED.getCode());
        Assert.assertEquals(30002, ServiceExceptionCodes.FLAT_DATA_CODEC_ENCODE_FAILED.getCode());
        Assert.assertEquals(30003, ServiceExceptionCodes.FLAT_DATA_CODEC_DECODE_FAILED.getCode());
        Assert.assertEquals(30010, ServiceExceptionCodes.VALUE_CODING_FAILED.getCode());
        Assert.assertEquals(30011, ServiceExceptionCodes.VALUE_CODEC_FAILED.getCode());
        Assert.assertEquals(30012, ServiceExceptionCodes.VALUE_CODEC_ENCODE_FAILED.getCode());
        Assert.assertEquals(30013, ServiceExceptionCodes.VALUE_CODEC_DECODE_FAILED.getCode());
        Assert.assertEquals(30020, ServiceExceptionCodes.DATA_CODING_QOS_FAILED.getCode());
        Assert.assertEquals(30021, ServiceExceptionCodes.AMBIGUOUS_DATA_CODING_HANDLER.getCode());
        Assert.assertEquals(30022, ServiceExceptionCodes.NO_DATA_CODING_HANDLER_PRESENT.getCode());
        Assert.assertEquals(30023, ServiceExceptionCodes.DATA_CODING_QOS_HANDLER_NOT_FOUND.getCode());
        Assert.assertEquals(30030, ServiceExceptionCodes.VALUE_CODING_QOS_FAILED.getCode());
        Assert.assertEquals(30031, ServiceExceptionCodes.AMBIGUOUS_VALUE_CODING_HANDLER.getCode());
        Assert.assertEquals(30032, ServiceExceptionCodes.NO_VALUE_CODING_HANDLER_PRESENT.getCode());
        Assert.assertEquals(30033, ServiceExceptionCodes.VALUE_CODING_QOS_HANDLER_NOT_FOUND.getCode());
    }

    @Test
    public void testSetExceptionCodeOffset() {
        int originalOffset = ServiceExceptionCodes.getExceptionCodeOffset();
        try {
            ServiceExceptionCodes.setExceptionCodeOffset(40000);
            Assert.assertEquals(40000, ServiceExceptionCodes.getExceptionCodeOffset());
            Assert.assertEquals(40000, ServiceExceptionCodes.DATA_CODING_FAILED.getCode());
            Assert.assertEquals(40001, ServiceExceptionCodes.FLAT_DATA_CODEC_FAILED.getCode());
            Assert.assertEquals(40002, ServiceExceptionCodes.FLAT_DATA_CODEC_ENCODE_FAILED.getCode());
            Assert.assertEquals(40003, ServiceExceptionCodes.FLAT_DATA_CODEC_DECODE_FAILED.getCode());
            Assert.assertEquals(40010, ServiceExceptionCodes.VALUE_CODING_FAILED.getCode());
            Assert.assertEquals(40011, ServiceExceptionCodes.VALUE_CODEC_FAILED.getCode());
            Assert.assertEquals(40012, ServiceExceptionCodes.VALUE_CODEC_ENCODE_FAILED.getCode());
            Assert.assertEquals(40013, ServiceExceptionCodes.VALUE_CODEC_DECODE_FAILED.getCode());
            Assert.assertEquals(40020, ServiceExceptionCodes.DATA_CODING_QOS_FAILED.getCode());
            Assert.assertEquals(40021, ServiceExceptionCodes.AMBIGUOUS_DATA_CODING_HANDLER.getCode());
            Assert.assertEquals(40022, ServiceExceptionCodes.NO_DATA_CODING_HANDLER_PRESENT.getCode());
            Assert.assertEquals(40023, ServiceExceptionCodes.DATA_CODING_QOS_HANDLER_NOT_FOUND.getCode());
            Assert.assertEquals(40030, ServiceExceptionCodes.VALUE_CODING_QOS_FAILED.getCode());
            Assert.assertEquals(40031, ServiceExceptionCodes.AMBIGUOUS_VALUE_CODING_HANDLER.getCode());
            Assert.assertEquals(40032, ServiceExceptionCodes.NO_VALUE_CODING_HANDLER_PRESENT.getCode());
            Assert.assertEquals(40033, ServiceExceptionCodes.VALUE_CODING_QOS_HANDLER_NOT_FOUND.getCode());
        } finally {
            ServiceExceptionCodes.setExceptionCodeOffset(originalOffset);
        }
    }
}
