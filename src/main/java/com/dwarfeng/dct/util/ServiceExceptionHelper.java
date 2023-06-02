package com.dwarfeng.dct.util;

import com.dwarfeng.dct.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 dwarfeng-dct 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为null。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(DctException.class, ServiceExceptionCodes.DCT_FAILED);
        map.put(ValueCodecException.class, ServiceExceptionCodes.VALUE_CODEC_FAILED);
        map.put(ValueCodecEncodeException.class, ServiceExceptionCodes.VALUE_CODEC_ENCODE_FAILED);
        map.put(ValueCodecDecodeException.class, ServiceExceptionCodes.VALUE_CODEC_DECODE_FAILED);
        map.put(FlatDataCodecException.class, ServiceExceptionCodes.FLAT_DATA_CODEC_FAILED);
        map.put(FlatDataCodecEncodeException.class, ServiceExceptionCodes.FLAT_DATA_CODEC_ENCODE_FAILED);
        map.put(FlatDataCodecDecodeException.class, ServiceExceptionCodes.FLAT_DATA_CODEC_DECODE_FAILED);

        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
