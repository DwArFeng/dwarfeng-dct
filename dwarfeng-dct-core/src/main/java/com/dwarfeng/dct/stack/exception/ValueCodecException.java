package com.dwarfeng.dct.stack.exception;

/**
 * 值编码解码器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ValueCodecException extends DctException {

    private static final long serialVersionUID = 1448125069073327982L;

    public ValueCodecException() {
    }

    public ValueCodecException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "值编解码器异常";
    }
}
