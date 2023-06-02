package com.dwarfeng.dct.util;

import com.dwarfeng.dct.handler.FlatDataCodec;
import com.dwarfeng.dct.handler.ValueCodingHandler;

import java.util.Objects;

/**
 * 扁平数据编解码器工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FlatDataCodecUtil {

    /**
     * 检查指定的扁平数据编解码器是否合法。
     *
     * @param flatDataCodec 指定的扁平数据编解码器。
     */
    public static void checkCodec(FlatDataCodec flatDataCodec) {
        if (Objects.isNull(flatDataCodec)) {
            throw new IllegalArgumentException("FlatDataCodec 不能为空");
        }
    }

    /**
     * 检查指定的值编码处理器是否合法。
     *
     * @param valueCodingHandler 指定的值编码处理器。
     */
    public static void checkValueCodingHandler(ValueCodingHandler valueCodingHandler) {
        if (Objects.isNull(valueCodingHandler)) {
            throw new IllegalArgumentException("ValueCodingHandler 不能为空");
        }
    }

    private FlatDataCodecUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
