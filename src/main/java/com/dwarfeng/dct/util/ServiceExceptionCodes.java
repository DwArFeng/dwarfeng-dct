package com.dwarfeng.dct.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 30000;

    // 根异常。
    public static final ServiceException.Code DCT_FAILED =
            new ServiceException.Code(offset(0), "dct failed");
    // 值编解码器异常。
    public static final ServiceException.Code VALUE_CODEC_FAILED =
            new ServiceException.Code(offset(1), "value codec failed");
    public static final ServiceException.Code VALUE_CODEC_ENCODE_FAILED =
            new ServiceException.Code(offset(2), "value codec encode failed");
    public static final ServiceException.Code VALUE_CODEC_DECODE_FAILED =
            new ServiceException.Code(offset(3), "value codec decode failed");
    // 扁平数据编解码器异常。
    public static final ServiceException.Code FLAT_DATA_CODEC_FAILED =
            new ServiceException.Code(offset(4), "flat data codec failed");
    public static final ServiceException.Code FLAT_DATA_CODEC_ENCODE_FAILED =
            new ServiceException.Code(offset(5), "flat data codec encode failed");
    public static final ServiceException.Code FLAT_DATA_CODEC_DECODE_FAILED =
            new ServiceException.Code(offset(6), "flat data codec decode failed");

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
        DCT_FAILED.setCode(offset(0));
        VALUE_CODEC_FAILED.setCode(offset(1));
        VALUE_CODEC_ENCODE_FAILED.setCode(offset(2));
        VALUE_CODEC_DECODE_FAILED.setCode(offset(3));
        FLAT_DATA_CODEC_FAILED.setCode(offset(4));
        FLAT_DATA_CODEC_ENCODE_FAILED.setCode(offset(5));
        FLAT_DATA_CODEC_DECODE_FAILED.setCode(offset(6));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
