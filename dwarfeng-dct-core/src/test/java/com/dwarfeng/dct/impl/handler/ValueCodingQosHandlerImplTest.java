package com.dwarfeng.dct.impl.handler;

import com.dwarfeng.dct.stack.exception.AmbiguousValueCodingHandlerException;
import com.dwarfeng.dct.stack.exception.NoValueCodingHandlerPresentException;
import com.dwarfeng.dct.stack.exception.ValueCodingHandlerNotFoundException;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.dct.stack.handler.ValueCodingQosHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 值编码 QoS 处理器实现测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ValueCodingQosHandlerImplTest {

    @Test
    public void testEmptyMapThrowsNoHandlerPresent() throws HandlerException {
        ValueCodingQosHandler qosHandler = new ValueCodingQosHandlerImpl(Collections.emptyMap());
        try {
            qosHandler.encode(null, "v");
            Assert.fail();
        } catch (NoValueCodingHandlerPresentException ignored) {
        }
    }

    @Test
    public void testNullHandlerNameWithSingleHandler() throws HandlerException {
        StubValueCodingHandler handler = new StubValueCodingHandler();
        Map<String, ValueCodingHandler> map = new HashMap<>();
        map.put("h1", handler);
        ValueCodingQosHandler qosHandler = new ValueCodingQosHandlerImpl(map);

        String encoded = qosHandler.encode(null, "v");
        Object decoded = qosHandler.decode(null, encoded);

        Assert.assertEquals("v", decoded);
    }

    @Test(expected = AmbiguousValueCodingHandlerException.class)
    public void testNullHandlerNameWithMultipleHandlers() throws HandlerException {
        Map<String, ValueCodingHandler> map = new HashMap<>();
        map.put("a", new StubValueCodingHandler());
        map.put("b", new StubValueCodingHandler());
        ValueCodingQosHandler qosHandler = new ValueCodingQosHandlerImpl(map);
        qosHandler.encode(null, "v");
    }

    @Test(expected = ValueCodingHandlerNotFoundException.class)
    public void testHandlerNotFound() throws HandlerException {
        Map<String, ValueCodingHandler> map = new HashMap<>();
        map.put("h1", new StubValueCodingHandler());
        ValueCodingQosHandler qosHandler = new ValueCodingQosHandlerImpl(map);
        qosHandler.encode("missing", "v");
    }

    @Test
    public void testListHandlerNamesSortedAndUnmodifiable() throws HandlerException {
        Map<String, ValueCodingHandler> map = new HashMap<>();
        map.put("z", new StubValueCodingHandler());
        map.put("a", new StubValueCodingHandler());
        ValueCodingQosHandler qosHandler = new ValueCodingQosHandlerImpl(map);

        List<String> names = qosHandler.listHandlerNames();
        Assert.assertEquals(2, names.size());
        Assert.assertEquals("a", names.get(0));
        Assert.assertEquals("z", names.get(1));
        try {
            names.add("x");
            Assert.fail();
        } catch (UnsupportedOperationException ignored) {
        }
    }

    private static class StubValueCodingHandler implements ValueCodingHandler {

        @Nonnull
        @Override
        public String encode(Object target) {
            return "encoded:" + target;
        }

        @Override
        public Object decode(@Nonnull String text) {
            return text.substring("encoded:".length());
        }
    }
}
