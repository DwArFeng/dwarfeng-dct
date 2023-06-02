package com.dwarfeng.dct.exception;

import com.dwarfeng.dct.handler.ValueCodec;

/**
 * 值编解码器解码异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ValueCodecDecodeException extends ValueCodecException {

    private static final long serialVersionUID = -2528205304630553634L;

    protected final String text;

    public ValueCodecDecodeException(ValueCodec codec, String text) {
        super(codec);
        this.text = text;
    }

    public ValueCodecDecodeException(Throwable cause, ValueCodec codec, String text) {
        super(cause, codec);
        this.text = text;
    }

    @Override
    public String getMessage() {
        return "值编解码器异常, 解码器: " + codec + ", 文本: " + text;
    }
}
