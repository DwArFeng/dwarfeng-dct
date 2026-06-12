package com.dwarfeng.dct.impl.handler;

import com.dwarfeng.dct.stack.bean.dto.GeneralData;
import com.dwarfeng.dct.stack.exception.AmbiguousDataCodingHandlerException;
import com.dwarfeng.dct.stack.exception.DataCodingHandlerNotFoundException;
import com.dwarfeng.dct.stack.exception.NoDataCodingHandlerPresentException;
import com.dwarfeng.dct.stack.handler.DataCodingHandler;
import com.dwarfeng.dct.stack.handler.DataCodingQosHandler;
import com.dwarfeng.dct.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据编码 QoS 处理器实现测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DataCodingQosHandlerImplTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataCodingQosHandler dataCodingQosHandler;

    @Test
    public void testEmptyMapThrowsNoHandlerPresent() throws HandlerException {
        DataCodingQosHandler qosHandler = new DataCodingQosHandlerImpl(Collections.emptyMap());
        try {
            qosHandler.encode(null, new GeneralData(new LongIdKey(1L), "v", new java.util.Date(0L), 0));
            Assert.fail();
        } catch (NoDataCodingHandlerPresentException ignored) {
        }
    }

    @Test
    public void testNullHandlerNameWithSingleHandler() throws HandlerException {
        String encoded = dataCodingQosHandler.encode(
                null, new GeneralData(new LongIdKey(1L), "v", new java.util.Date(0L), 0)
        );
        Data decoded = dataCodingQosHandler.decode(null, encoded);

        Assert.assertEquals("v", decoded.getValue());
    }

    @Test(expected = AmbiguousDataCodingHandlerException.class)
    public void testNullHandlerNameWithMultipleHandlers() throws HandlerException {
        Map<String, DataCodingHandler> map = new HashMap<>();
        map.put("a", new StubDataCodingHandler());
        map.put("b", new StubDataCodingHandler());
        DataCodingQosHandler qosHandler = new DataCodingQosHandlerImpl(map);
        qosHandler.encode(null, new GeneralData(new LongIdKey(1L), "v", new java.util.Date(0L), 0));
    }

    @Test(expected = DataCodingHandlerNotFoundException.class)
    public void testHandlerNotFound() throws HandlerException {
        Map<String, DataCodingHandler> map = new HashMap<>();
        map.put("h1", new StubDataCodingHandler());
        DataCodingQosHandler qosHandler = new DataCodingQosHandlerImpl(map);
        qosHandler.encode("missing", new GeneralData(new LongIdKey(1L), "v", new java.util.Date(0L), 0));
    }

    @Test
    public void testListHandlerNamesSortedAndUnmodifiable() throws HandlerException {
        Map<String, DataCodingHandler> map = new HashMap<>();
        map.put("z", new StubDataCodingHandler());
        map.put("a", new StubDataCodingHandler());
        DataCodingQosHandler qosHandler = new DataCodingQosHandlerImpl(map);

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

    private static class StubDataCodingHandler implements DataCodingHandler {

        @Nonnull
        @Override
        public String encode(@Nonnull Data data) {
            return "encoded:" + data.getValue();
        }

        @Nonnull
        @Override
        public Data decode(@Nonnull String string) {
            return new GeneralData(new LongIdKey(1L), string.substring("encoded:".length()), new java.util.Date(0L), 0);
        }
    }
}
