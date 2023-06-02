package com.dwarfeng.dct.exception;

import com.dwarfeng.dct.handler.ValueCodec;

/**
 * 值编解码器编码异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ValueCodecEncodeException extends ValueCodecException {

    private static final long serialVersionUID = 4785781134162740987L;

    private final Object target;

    public ValueCodecEncodeException(ValueCodec codec, Object target) {
        super(codec);
        this.target = target;
    }

    public ValueCodecEncodeException(Throwable cause, ValueCodec codec, Object target) {
        super(cause, codec);
        this.target = target;
    }

    @Override
    public String getMessage() {
        return "值编解码器异常, 编解码器: " + codec + ", 目标: " + target;
    }
}
