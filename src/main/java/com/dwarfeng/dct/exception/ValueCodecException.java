package com.dwarfeng.dct.exception;

import com.dwarfeng.dct.handler.ValueCodec;

/**
 * 值编码解码器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ValueCodecException extends DctException {

    private static final long serialVersionUID = 4032012735560259873L;

    protected final ValueCodec codec;

    public ValueCodecException(ValueCodec codec) {
        this.codec = codec;
    }

    public ValueCodecException(Throwable cause, ValueCodec codec) {
        super(cause);
        this.codec = codec;
    }

    @Override
    public String getMessage() {
        return "值编解码器异常: " + codec;
    }
}
