package com.dwarfeng.dct.stack.util;

import com.dwarfeng.dct.stack.bean.dto.FlatData;
import com.dwarfeng.dct.stack.handler.FlatDataCodec;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Nonnull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DataCodingConfigUtilTest {

    @Test
    public void testCheckFlatDataCodecValid() {
        DataCodingConfigUtil.checkFlatDataCodec(new StubFlatDataCodec());
    }

    @Test(expected = NullPointerException.class)
    public void testCheckFlatDataCodecNull() {
        DataCodingConfigUtil.checkFlatDataCodec(null);
    }

    @Test
    public void testCheckValueCodingHandlerValid() {
        DataCodingConfigUtil.checkValueCodingHandler(new StubValueCodingHandler());
    }

    @Test(expected = NullPointerException.class)
    public void testCheckValueCodingHandlerNull() {
        DataCodingConfigUtil.checkValueCodingHandler(null);
    }

    private static final class StubFlatDataCodec implements FlatDataCodec {

        @Override
        public String encode(FlatData target) {
            return "";
        }

        @Override
        public FlatData decode(String text) {
            return null;
        }
    }

    private static final class StubValueCodingHandler implements ValueCodingHandler {

        @Nonnull
        @Override
        public String encode(Object target) {
            return "";
        }

        @Override
        public Object decode(@Nonnull String text) {
            return null;
        }
    }
}
