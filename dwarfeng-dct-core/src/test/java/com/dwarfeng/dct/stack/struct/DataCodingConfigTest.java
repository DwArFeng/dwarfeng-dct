package com.dwarfeng.dct.stack.struct;

import com.dwarfeng.dct.stack.bean.dto.FlatData;
import com.dwarfeng.dct.stack.handler.FlatDataCodec;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DataCodingConfigTest {

    @Test
    public void testPublicConstructorValid() {
        new DataCodingConfig(new StubFlatDataCodec(), new StubValueCodingHandler());
    }

    @Test(expected = NullPointerException.class)
    public void testPublicConstructorFlatDataCodecNull() {
        new DataCodingConfig(null, new StubValueCodingHandler());
    }

    @Test(expected = NullPointerException.class)
    public void testPublicConstructorValueCodingHandlerNull() {
        new DataCodingConfig(new StubFlatDataCodec(), null);
    }

    @Test
    public void testBuilderValid() {
        new DataCodingConfig.Builder()
                .setFlatDataCodec(new StubFlatDataCodec())
                .setValueCodingHandler(new StubValueCodingHandler())
                .build();
    }

    // 本测试方法用于测试 Builder 的 set 方法在传入 null 时不会立即抛出异常，因此对测试目标只写是合理的，因此抑制相关警告。
    @SuppressWarnings("WriteOnlyObject")
    @Test
    public void testBuilderSetNullDoesNotThrowImmediately() {
        new DataCodingConfig.Builder()
                .setFlatDataCodec(null)
                .setValueCodingHandler(null);
    }

    @Test(expected = NullPointerException.class)
    public void testBuilderBuildFlatDataCodecNull() {
        new DataCodingConfig.Builder()
                .setFlatDataCodec(null)
                .setValueCodingHandler(new StubValueCodingHandler())
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testBuilderBuildValueCodingHandlerNull() {
        new DataCodingConfig.Builder()
                .setFlatDataCodec(new StubFlatDataCodec())
                .setValueCodingHandler(null)
                .build();
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
        public String encode(@Nullable Object target) {
            return "";
        }

        @Nullable
        @Override
        public Object decode(@Nonnull String text) {
            return null;
        }
    }
}
