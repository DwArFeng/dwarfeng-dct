package com.dwarfeng.dct.stack.util;

import com.dwarfeng.dct.sdk.util.Constants;
import com.dwarfeng.dct.stack.handler.ValueCodec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ValueCodingConfigUtilTest {

    @Test
    public void testCheckCodecValid() {
        ValueCodingConfigUtil.checkCodec(new ValidStubCodec("pre"));
    }

    @Test(expected = NullPointerException.class)
    public void testCheckCodecNull() {
        ValueCodingConfigUtil.checkCodec(null);
    }

    @Test(expected = NullPointerException.class)
    public void testCheckCodecsNullList() {
        ValueCodingConfigUtil.checkCodecs(null);
    }

    @Test
    public void testCheckCodecsEmptyList() {
        ValueCodingConfigUtil.checkCodecs(Collections.emptyList());
    }

    @Test
    public void testCheckTargetClassValid() {
        ValueCodingConfigUtil.checkTargetClass(String.class);
    }

    @Test(expected = NullPointerException.class)
    public void testCheckTargetClassNull() {
        ValueCodingConfigUtil.checkTargetClass(null);
    }

    @Test(expected = NullPointerException.class)
    public void testCheckTargetClassesNullList() {
        ValueCodingConfigUtil.checkTargetClasses(null);
    }

    @Test
    public void testCheckTargetClassesEmptyList() {
        ValueCodingConfigUtil.checkTargetClasses(Collections.emptyList());
    }

    @Test
    public void testCheckValuePrefixValid() {
        ValueCodingConfigUtil.checkValuePrefix("abc");
    }

    @Test(expected = NullPointerException.class)
    public void testCheckValuePrefixNull() {
        ValueCodingConfigUtil.checkValuePrefix(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckValuePrefixEmpty() {
        ValueCodingConfigUtil.checkValuePrefix("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckValuePrefixWhitespace() {
        ValueCodingConfigUtil.checkValuePrefix("a b");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckValuePrefixContainsDelimiter() {
        ValueCodingConfigUtil.checkValuePrefix("a" + Constants.FLAT_DATA_VALUE_PREFIX_DELIMITER + "b");
    }

    @Test(expected = NullPointerException.class)
    public void testCheckValuePrefixesNullList() {
        ValueCodingConfigUtil.checkValuePrefixes(null);
    }

    @Test
    public void testCheckValuePrefixesEmptyList() {
        ValueCodingConfigUtil.checkValuePrefixes(Collections.emptyList());
    }

    @Test
    public void testCheckValuePrefixes() {
        ValueCodingConfigUtil.checkValuePrefixes(Collections.singletonList("p1"));
    }

    @Test
    public void testCheckTargetClasses() {
        ValueCodingConfigUtil.checkTargetClasses(Collections.singletonList(String.class));
    }

    @Test
    public void testCheckCodecs() {
        ValueCodingConfigUtil.checkCodecs(Collections.singletonList(new ValidStubCodec("x")));
    }

    @Test(expected = NullPointerException.class)
    public void testCheckCodecsWithNullElement() {
        List<ValueCodec> codecs = new ArrayList<>();
        codecs.add(null);
        ValueCodingConfigUtil.checkCodecs(codecs);
    }

    private static final class ValidStubCodec implements ValueCodec {

        private final String prefix;

        private ValidStubCodec(String prefix) {
            this.prefix = prefix;
        }

        @Override
        @Nonnull
        public Class<?> getTargetClass() {
            return String.class;
        }

        @Override
        @Nonnull
        public String getValuePrefix() {
            return prefix;
        }

        @Override
        public int getPriority() {
            return 0;
        }

        @Override
        @Nonnull
        public String encode(@Nonnull Object target) {
            return prefix + ":v";
        }

        @Override
        @Nonnull
        public Object decode(@Nonnull String text) {
            return "v";
        }
    }
}
