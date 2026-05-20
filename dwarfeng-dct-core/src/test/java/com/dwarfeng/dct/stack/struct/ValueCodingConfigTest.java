package com.dwarfeng.dct.stack.struct;

import com.dwarfeng.dct.sdk.util.Constants;
import com.dwarfeng.dct.stack.handler.ValueCodec;
import org.junit.Assert;
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
public class ValueCodingConfigTest {

    @Test
    public void testPublicConstructorEmptyLists() {
        new ValueCodingConfig(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
        );
    }

    @Test(expected = NullPointerException.class)
    public void testPublicConstructorCodecsNull() {
        new ValueCodingConfig(null, Collections.emptyList(), Collections.emptyList());
    }

    @Test(expected = NullPointerException.class)
    public void testPublicConstructorPreCacheClassesNull() {
        new ValueCodingConfig(Collections.emptyList(), null, Collections.emptyList());
    }

    @Test(expected = NullPointerException.class)
    public void testPublicConstructorPreCachePrefixesNull() {
        new ValueCodingConfig(Collections.emptyList(), Collections.emptyList(), null);
    }

    @Test(expected = NullPointerException.class)
    public void testPublicConstructorIllegalCodec() {
        List<ValueCodec> codecs = new ArrayList<>();
        codecs.add(null);
        new ValueCodingConfig(codecs, Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void testImmutableCollections() {
        List<ValueCodec> codecs = new ArrayList<>();
        codecs.add(new ValidStubCodec("p"));
        List<Class<?>> classes = new ArrayList<>();
        classes.add(String.class);
        List<String> prefixes = new ArrayList<>();
        prefixes.add("p");

        ValueCodingConfig config = new ValueCodingConfig(codecs, classes, prefixes);

        codecs.add(new ValidStubCodec("x"));
        classes.add(Integer.class);
        prefixes.add("x");

        Assert.assertEquals(1, config.getCodecs().size());
        Assert.assertEquals(1, config.getPreCacheClasses().size());
        Assert.assertEquals(1, config.getPreCachePrefixes().size());
    }

    // 本测试方法用于测试异常是否正确抛出，因此抑制相关警告。
    @SuppressWarnings("DataFlowIssue")
    @Test(expected = UnsupportedOperationException.class)
    public void testGetterReturnsUnmodifiableCodecs() {
        ValueCodingConfig config = new ValueCodingConfig(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList()
        );
        config.getCodecs().add(new ValidStubCodec("x"));
    }

    @Test
    public void testBuilderValid() {
        new ValueCodingConfig.Builder()
                .addCodec(new ValidStubCodec("p"))
                .addPreCacheClass(String.class)
                .addPreCachePrefix("p")
                .build();
    }

    @Test
    public void testBuilderSetNullDoesNotThrowImmediately() {
        new ValueCodingConfig.Builder()
                .setCodecs(null)
                .setPreCacheClasses(null)
                .setPreCachePrefixes(null);
    }

    @Test(expected = NullPointerException.class)
    public void testBuilderBuildCodecsNull() {
        new ValueCodingConfig.Builder()
                .setCodecs(null)
                .build();
    }

    @Test
    public void testBuilderSetCodecsReplacesContent() {
        List<ValueCodec> codecs = Collections.singletonList(new ValidStubCodec("p"));
        ValueCodingConfig config = new ValueCodingConfig.Builder()
                .addCodec(new ValidStubCodec("ignored"))
                .setCodecs(codecs)
                .setPreCacheClasses(Collections.singletonList(String.class))
                .setPreCachePrefixes(Collections.singletonList("p"))
                .build();
        Assert.assertEquals(1, config.getCodecs().size());
        Assert.assertEquals("p", config.getCodecs().get(0).getValuePrefix());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuilderBuildIllegalPrefixDeferred() {
        new ValueCodingConfig.Builder()
                .addPreCachePrefix("a" + Constants.FLAT_DATA_VALUE_PREFIX_DELIMITER + "b")
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testBuilderBuildIllegalCodecDeferred() {
        new ValueCodingConfig.Builder()
                .addCodec(null)
                .build();
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
