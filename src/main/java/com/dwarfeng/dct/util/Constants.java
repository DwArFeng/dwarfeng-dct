package com.dwarfeng.dct.util;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class Constants {

    /**
     * 扁平数据值的前缀分隔符。
     */
    public static final String FLAT_DATA_VALUE_PREFIX_DELIMITER = ":";

    /**
     * 值编解码器的优先级：最高。
     *
     * <p>
     * 默认实现中没有使用该优先级，用户可以使用该优先级以覆盖默认实现。
     */
    public static final int VALUE_CODEC_PRIORITY_MAX = Integer.MIN_VALUE;

    /**
     * 值编解码器的优先级：非常高。
     *
     * <p>
     * 默认实现中没有使用该优先级，用户可以使用该优先级以覆盖默认实现。
     */
    public static final int VALUE_CODEC_PRIORITY_VERY_HIGH = Integer.MIN_VALUE + 1000;

    /**
     * 值编解码器的优先级：高。
     *
     * <p>
     * 默认实现中没有使用该优先级，用户可以使用该优先级以覆盖默认实现。
     */
    public static final int VALUE_CODEC_PRIORITY_HIGH = Integer.MIN_VALUE + 2000;

    /**
     * 值编解码器的优先级：正常。
     *
     * <p>
     * 默认实现中大部分使用该优先级。
     */
    public static final int VALUE_CODEC_PRIORITY_NORMAL = 0;

    /**
     * 值编解码器的优先级：低。
     *
     * <p>
     * 该优先级为项目的预留优先级，以后可能会使用。
     */
    public static final int VALUE_CODEC_PRIORITY_LOW = Integer.MAX_VALUE - 2000;

    /**
     * 值编解码器的优先级：非常低。
     *
     * <p>
     * 该优先级为项目的预留优先级，以后可能会使用。
     */
    public static final int VALUE_CODEC_PRIORITY_VERY_LOW = Integer.MAX_VALUE - 1000;

    /**
     * 值编解码器的优先级：最低。
     *
     * <p>
     * 该优先级的编解码器将会被最后使用，通常是保底的编解码器。<br>
     * 该项目中少部分编解码器使用了该优先级。
     */
    public static final int VALUE_CODEC_PRIORITY_MIN = Integer.MAX_VALUE;

    private Constants() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
