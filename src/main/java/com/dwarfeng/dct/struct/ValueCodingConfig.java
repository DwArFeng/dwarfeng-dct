package com.dwarfeng.dct.struct;

import com.dwarfeng.dct.handler.ValueCodec;
import com.dwarfeng.dct.util.ValueCodecUtil;
import com.dwarfeng.dutil.basic.prog.Buildable;

import java.util.ArrayList;
import java.util.Collection;
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
        ValueCodecUtil.checkCodecs(codecs);
        ValueCodecUtil.checkTargetClasses(preCacheClasses);
        ValueCodecUtil.checkValuePrefixes(preCachePrefixes);
        this.codecs = codecs;
        this.preCacheClasses = preCacheClasses;
        this.preCachePrefixes = preCachePrefixes;
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

        private final List<ValueCodec> codecs = new ArrayList<>();
        private final List<Class<?>> preCacheClasses = new ArrayList<>();
        private final List<String> preCachePrefixes = new ArrayList<>();

        public Builder() {
        }

        public Builder addCodec(ValueCodec codec) {
            ValueCodecUtil.checkCodec(codec);
            codecs.add(codec);
            return this;
        }

        public Builder addCodecs(Collection<ValueCodec> codecs) {
            ValueCodecUtil.checkCodecs(codecs);
            this.codecs.addAll(codecs);
            return this;
        }

        public Builder addPreCacheClass(Class<?> preCacheClass) {
            ValueCodecUtil.checkTargetClass(preCacheClass);
            preCacheClasses.add(preCacheClass);
            return this;
        }

        public Builder addPreCacheClasses(Collection<Class<?>> preCacheClasses) {
            ValueCodecUtil.checkTargetClasses(preCacheClasses);
            this.preCacheClasses.addAll(preCacheClasses);
            return this;
        }

        public Builder addPreCachePrefix(String preCachePrefix) {
            ValueCodecUtil.checkValuePrefix(preCachePrefix);
            preCachePrefixes.add(preCachePrefix);
            return this;
        }

        public Builder addPreCachePrefixes(Collection<String> preCachePrefixes) {
            for (String preCachePrefix : preCachePrefixes) {
                ValueCodecUtil.checkValuePrefix(preCachePrefix);
            }
            this.preCachePrefixes.addAll(preCachePrefixes);
            return this;
        }

        @Override
        public ValueCodingConfig build() {
            return new ValueCodingConfig(codecs, preCacheClasses, preCachePrefixes);
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
