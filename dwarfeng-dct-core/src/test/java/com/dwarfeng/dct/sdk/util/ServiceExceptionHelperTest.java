package com.dwarfeng.dct.sdk.util;

import com.dwarfeng.dct.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link ServiceExceptionHelper} 测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ServiceExceptionHelperTest {

    @Test
    public void testPutDefaultDestination() {
        Map<Class<? extends Exception>, ServiceException.Code> map = new HashMap<>();
        ServiceExceptionHelper.putDefaultDestination(map);

        Assert.assertEquals(ServiceExceptionCodes.DATA_CODING_FAILED, map.get(DataCodingException.class));
        Assert.assertEquals(ServiceExceptionCodes.FLAT_DATA_CODEC_FAILED, map.get(FlatDataCodecException.class));
        Assert.assertEquals(ServiceExceptionCodes.FLAT_DATA_CODEC_ENCODE_FAILED, map.get(FlatDataCodecEncodeException.class));
        Assert.assertEquals(ServiceExceptionCodes.FLAT_DATA_CODEC_DECODE_FAILED, map.get(FlatDataCodecDecodeException.class));
        Assert.assertEquals(ServiceExceptionCodes.VALUE_CODING_FAILED, map.get(ValueCodingException.class));
        Assert.assertEquals(ServiceExceptionCodes.VALUE_CODEC_FAILED, map.get(ValueCodecException.class));
        Assert.assertEquals(ServiceExceptionCodes.VALUE_CODEC_ENCODE_FAILED, map.get(ValueCodecEncodeException.class));
        Assert.assertEquals(ServiceExceptionCodes.VALUE_CODEC_DECODE_FAILED, map.get(ValueCodecDecodeException.class));
        Assert.assertEquals(ServiceExceptionCodes.DATA_CODING_QOS_FAILED, map.get(DataCodingQosException.class));
        Assert.assertEquals(ServiceExceptionCodes.AMBIGUOUS_DATA_CODING_HANDLER, map.get(AmbiguousDataCodingHandlerException.class));
        Assert.assertEquals(ServiceExceptionCodes.NO_DATA_CODING_HANDLER_PRESENT, map.get(NoDataCodingHandlerPresentException.class));
        Assert.assertEquals(ServiceExceptionCodes.DATA_CODING_QOS_HANDLER_NOT_FOUND, map.get(DataCodingHandlerNotFoundException.class));
        Assert.assertEquals(ServiceExceptionCodes.VALUE_CODING_QOS_FAILED, map.get(ValueCodingQosException.class));
        Assert.assertEquals(ServiceExceptionCodes.AMBIGUOUS_VALUE_CODING_HANDLER, map.get(AmbiguousValueCodingHandlerException.class));
        Assert.assertEquals(ServiceExceptionCodes.NO_VALUE_CODING_HANDLER_PRESENT, map.get(NoValueCodingHandlerPresentException.class));
        Assert.assertEquals(ServiceExceptionCodes.VALUE_CODING_QOS_HANDLER_NOT_FOUND, map.get(ValueCodingHandlerNotFoundException.class));
    }

    @Test
    public void testPutDefaultDestinationWithNullMap() {
        Map<Class<? extends Exception>, ServiceException.Code> map = ServiceExceptionHelper.putDefaultDestination(null);
        Assert.assertNotNull(map);
        Assert.assertEquals(16, map.size());
    }
}
