package com.dwarfeng.dct.stack.util;

import com.dwarfeng.dct.stack.handler.FlatDataCodec;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;

import java.util.Objects;

/**
 * 数据编解码配置工具类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class DataCodingConfigUtil {

    /**
     * 检查指定的扁平数据编解码器是否合法。
     *
     * @param flatDataCodec 指定的扁平数据编解码器。
     */
    public static void checkFlatDataCodec(FlatDataCodec flatDataCodec) {
        if (Objects.isNull(flatDataCodec)) {
            throw new NullPointerException("扁平数据编解码器不能为 null");
        }
    }

    /**
     * 检查指定的值编码处理器是否合法。
     *
     * @param valueCodingHandler 指定的值编码处理器。
     */
    public static void checkValueCodingHandler(ValueCodingHandler valueCodingHandler) {
        if (Objects.isNull(valueCodingHandler)) {
            throw new NullPointerException("值编码处理器不能为 null");
        }
    }

    private DataCodingConfigUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
