package com.dwarfeng.dct.sdk.util;

import com.dwarfeng.dct.stack.exception.DataCodingException;
import com.dwarfeng.dct.stack.exception.FlatDataCodecEncodeException;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * {@link DataCodingExceptionHelper} 测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DataCodingExceptionHelperTest {

    @Test
    public void testParseHandlerException() {
        FlatDataCodecEncodeException exception = new FlatDataCodecEncodeException(null);
        HandlerException parsed = DataCodingExceptionHelper.parse(exception);
        Assert.assertSame(exception, parsed);
    }

    @Test
    public void testParsePlainException() {
        RuntimeException cause = new RuntimeException("test");
        HandlerException parsed = DataCodingExceptionHelper.parse(cause);
        Assert.assertTrue(parsed instanceof DataCodingException);
        Assert.assertSame(cause, parsed.getCause());
    }
}
