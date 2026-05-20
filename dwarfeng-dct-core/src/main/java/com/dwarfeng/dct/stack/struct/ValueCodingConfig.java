package com.dwarfeng.dct.stack.struct;

import com.dwarfeng.dct.stack.handler.ValueCodec;
import com.dwarfeng.dct.stack.util.ValueCodingConfigUtil;
import com.dwarfeng.dutil.basic.prog.Buildable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 值编解码器配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ValueCodingConfig {

    private final List<ValueCodec> codecs;
    private final List<Class<?>> preCacheClasses;
    private final List<String> preCachePrefixes;

    public ValueCodingConfig(
            List<ValueCodec> codecs, List<Class<?>> preCacheClasses, List<String> preCachePrefixes
    ) {
        this(codecs, preCacheClasses, preCachePrefixes, false);
    }

    private ValueCodingConfig(
            List<ValueCodec> codecs, List<Class<?>> preCacheClasses, List<String> preCachePrefixes,
            boolean paramReliable
    ) {
        // 如果参数不可靠，则检查参数。
        if (!paramReliable) {
            ValueCodingConfigUtil.checkCodecs(codecs);
            ValueCodingConfigUtil.checkTargetClasses(preCacheClasses);
            ValueCodingConfigUtil.checkValuePrefixes(preCachePrefixes);
        }
        // 设置值。
        this.codecs = Collections.unmodifiableList(new ArrayList<>(codecs));
        this.preCacheClasses = Collections.unmodifiableList(new ArrayList<>(preCacheClasses));
        this.preCachePrefixes = Collections.unmodifiableList(new ArrayList<>(preCachePrefixes));
    }

    public List<ValueCodec> getCodecs() {
        return codecs;
    }

    public List<Class<?>> getPreCacheClasses() {
        return preCacheClasses;
    }

    public List<String> getPreCachePrefixes() {
        return preCachePrefixes;
    }

    @Override
    public String toString() {
        return "ValueCodingConfig{" +
                "codecs=" + codecs +
                ", preCacheClasses=" + preCacheClasses +
                ", preCachePrefixes=" + preCachePrefixes +
                '}';
    }

    public static final class Builder implements Buildable<ValueCodingConfig> {

        private List<ValueCodec> codecs = new ArrayList<>();
        private List<Class<?>> preCacheClasses = new ArrayList<>();
        private List<String> preCachePrefixes = new ArrayList<>();

        public Builder() {
        }

        public Builder addCodec(ValueCodec codec) {
            codecs.add(codec);
            return this;
        }

        public Builder addCodecs(Collection<ValueCodec> codecs) {
            this.codecs.addAll(codecs);
            return this;
        }

        public Builder addPreCacheClass(Class<?> preCacheClass) {
            preCacheClasses.add(preCacheClass);
            return this;
        }

        public Builder addPreCacheClasses(Collection<Class<?>> preCacheClasses) {
            this.preCacheClasses.addAll(preCacheClasses);
            return this;
        }

        public Builder addPreCachePrefix(String preCachePrefix) {
            preCachePrefixes.add(preCachePrefix);
            return this;
        }

        public Builder addPreCachePrefixes(Collection<String> preCachePrefixes) {
            this.preCachePrefixes.addAll(preCachePrefixes);
            return this;
        }

        public Builder setCodecs(List<ValueCodec> codecs) {
            this.codecs = codecs;
            return this;
        }

        public Builder setPreCacheClasses(List<Class<?>> preCacheClasses) {
            this.preCacheClasses = preCacheClasses;
            return this;
        }

        public Builder setPreCachePrefixes(List<String> preCachePrefixes) {
            this.preCachePrefixes = preCachePrefixes;
            return this;
        }

        @Override
        public ValueCodingConfig build() {
            // 检查参数。
            ValueCodingConfigUtil.checkCodecs(codecs);
            ValueCodingConfigUtil.checkTargetClasses(preCacheClasses);
            ValueCodingConfigUtil.checkValuePrefixes(preCachePrefixes);

            // 构造并返回配置。
            return new ValueCodingConfig(codecs, preCacheClasses, preCachePrefixes, true);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "codecs=" + codecs +
                    ", preCacheClasses=" + preCacheClasses +
                    ", preCachePrefixes=" + preCachePrefixes +
                    '}';
        }
    }
}
