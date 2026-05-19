package com.dwarfeng.dct.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 30000;

    // 数据编码异常。
    public static final ServiceException.Code DATA_CODING_FAILED =
            new ServiceException.Code(offset(0), "data coding failed");
    // 扁平数据编解码器异常。
    public static final ServiceException.Code FLAT_DATA_CODEC_FAILED =
            new ServiceException.Code(offset(1), "flat data codec failed");
    public static final ServiceException.Code FLAT_DATA_CODEC_ENCODE_FAILED =
            new ServiceException.Code(offset(2), "flat data codec encode failed");
    public static final ServiceException.Code FLAT_DATA_CODEC_DECODE_FAILED =
            new ServiceException.Code(offset(3), "flat data codec decode failed");
    // 值编码异常。
    public static final ServiceException.Code VALUE_CODING_FAILED =
            new ServiceException.Code(offset(10), "value coding failed");
    // 值编解码器异常。
    public static final ServiceException.Code VALUE_CODEC_FAILED =
            new ServiceException.Code(offset(11), "value codec failed");
    public static final ServiceException.Code VALUE_CODEC_ENCODE_FAILED =
            new ServiceException.Code(offset(12), "value codec encode failed");
    public static final ServiceException.Code VALUE_CODEC_DECODE_FAILED =
            new ServiceException.Code(offset(13), "value codec decode failed");

    private static int offset(int i) {
        return EXCEPTION_CODE_OFFSET + i;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        DATA_CODING_FAILED.setCode(offset(0));
        FLAT_DATA_CODEC_FAILED.setCode(offset(1));
        FLAT_DATA_CODEC_ENCODE_FAILED.setCode(offset(2));
        FLAT_DATA_CODEC_DECODE_FAILED.setCode(offset(3));
        VALUE_CODING_FAILED.setCode(offset(10));
        VALUE_CODEC_FAILED.setCode(offset(11));
        VALUE_CODEC_ENCODE_FAILED.setCode(offset(12));
        VALUE_CODEC_DECODE_FAILED.setCode(offset(13));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
