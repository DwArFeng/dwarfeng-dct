package com.dwarfeng.dct.sdk.util;

import com.dwarfeng.dct.stack.exception.ValueCodecEncodeException;
import com.dwarfeng.dct.stack.exception.ValueCodingException;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * {@link ValueCodingExceptionHelper} 测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ValueCodingExceptionHelperTest {

    @Test
    public void testParseHandlerException() {
        ValueCodecEncodeException exception = new ValueCodecEncodeException(null);
        HandlerException parsed = ValueCodingExceptionHelper.parse(exception);
        Assert.assertSame(exception, parsed);
    }

    @Test
    public void testParsePlainException() {
        RuntimeException cause = new RuntimeException("test");
        HandlerException parsed = ValueCodingExceptionHelper.parse(cause);
        Assert.assertTrue(parsed instanceof ValueCodingException);
        Assert.assertSame(cause, parsed.getCause());
    }
}
