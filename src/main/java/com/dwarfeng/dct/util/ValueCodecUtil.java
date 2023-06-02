package com.dwarfeng.dct.util;

import com.dwarfeng.dct.handler.ValueCodec;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 值编解码器工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ValueCodecUtil {

    /**
     * 检查指定的值编解码器是否合法。
     *
     * @param valueCodec 指定的值编解码器。
     */
    public static void checkCodec(ValueCodec valueCodec) {
        if (Objects.isNull(valueCodec)) {
            throw new IllegalArgumentException("ValueCodec 不能为空");
        }
        checkTargetClass(valueCodec.getTargetClass());
        checkValuePrefix(valueCodec.getValuePrefix());
    }

    /**
     * 检查指定的值编解码器集合是否合法。
     *
     * @param valueCodecs 指定的值编解码器集合。
     */
    public static void checkCodecs(Collection<ValueCodec> valueCodecs) {
        if (Objects.isNull(valueCodecs)) {
            throw new IllegalArgumentException("ValueCodec 列表不能为空");
        }
        for (ValueCodec valueCodec : valueCodecs) {
            checkCodec(valueCodec);
        }
    }

    /**
     * 检查指定的目标类是否合法。
     *
     * @param targetClass 指定的目标类。
     */
    public static void checkTargetClass(Class<?> targetClass) {
        if (Objects.isNull(targetClass)) {
            throw new IllegalArgumentException("目标类不能为空");
        }
    }

    /**
     * 检查指定的目标类集合是否合法。
     *
     * @param targetClasses 指定的目标类集合。
     */
    public static void checkTargetClasses(Collection<Class<?>> targetClasses) {
        if (Objects.isNull(targetClasses)) {
            throw new IllegalArgumentException("目标类列表不能为空");
        }
        for (Class<?> targetClass : targetClasses) {
            checkTargetClass(targetClass);
        }
    }

    /**
     * 检查指定的值前缀是否合法。
     *
     * @param valuePrefix 指定的值前缀。
     */
    public static void checkValuePrefix(String valuePrefix) {
        if (Objects.isNull(valuePrefix)) {
            throw new IllegalArgumentException("值前缀不能为空");
        }
        if (StringUtils.isEmpty(valuePrefix)) {
            throw new IllegalArgumentException("值前缀不能为空");
        }
        if (StringUtils.containsWhitespace(valuePrefix)) {
            throw new IllegalArgumentException("值前缀不能包含空白字符");
        }
        if (StringUtils.contains(valuePrefix, Constants.FLAT_DATA_VALUE_PREFIX_DELIMITER)) {
            throw new IllegalArgumentException("值前缀不能包含 " + Constants.FLAT_DATA_VALUE_PREFIX_DELIMITER);
        }
    }

    /**
     * 检查指定的值前缀集合是否合法。
     *
     * @param valuePrefixes 指定的值前缀集合。
     */
    public static void checkValuePrefixes(Collection<String> valuePrefixes) {
        if (Objects.isNull(valuePrefixes)) {
            throw new IllegalArgumentException("值前缀列表不能为空");
        }
        for (String valuePrefix : valuePrefixes) {
            checkValuePrefix(valuePrefix);
        }
    }

    private ValueCodecUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
