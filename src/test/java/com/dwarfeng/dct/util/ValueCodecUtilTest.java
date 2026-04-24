package com.dwarfeng.dct.util;

import com.dwarfeng.dct.handler.ValueCodec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Nonnull;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ValueCodecUtilTest {

    @Test
    public void testCheckCodecValid() {
        ValueCodecUtil.checkCodec(new ValidStubCodec("pre"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckCodecNull() {
        ValueCodecUtil.checkCodec(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckCodecsNullList() {
        ValueCodecUtil.checkCodecs(null);
    }

    @Test
    public void testCheckTargetClassValid() {
        ValueCodecUtil.checkTargetClass(String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckTargetClassNull() {
        ValueCodecUtil.checkTargetClass(null);
    }

    @Test
    public void testCheckValuePrefixValid() {
        ValueCodecUtil.checkValuePrefix("abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckValuePrefixEmpty() {
        ValueCodecUtil.checkValuePrefix("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckValuePrefixWhitespace() {
        ValueCodecUtil.checkValuePrefix("a b");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckValuePrefixContainsDelimiter() {
        ValueCodecUtil.checkValuePrefix("a" + Constants.FLAT_DATA_VALUE_PREFIX_DELIMITER + "b");
    }

    @Test
    public void testCheckValuePrefixes() {
        ValueCodecUtil.checkValuePrefixes(Collections.singletonList("p1"));
    }

    @Test
    public void testCheckTargetClasses() {
        ValueCodecUtil.checkTargetClasses(Collections.singletonList(String.class));
    }

    @Test
    public void testCheckCodecs() {
        ValueCodecUtil.checkCodecs(Collections.singletonList(new ValidStubCodec("x")));
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
