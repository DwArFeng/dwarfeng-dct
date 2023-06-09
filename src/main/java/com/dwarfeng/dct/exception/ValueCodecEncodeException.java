package com.dwarfeng.dct.exception;

/**
 * 值编解码器编码异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ValueCodecEncodeException extends ValueCodecException {

    private static final long serialVersionUID = 2561180941686356176L;

    protected final Object target;

    public ValueCodecEncodeException(Object target) {
        this.target = target;
    }

    public ValueCodecEncodeException(Throwable cause, Object target) {
        super(cause);
        this.target = target;
    }

    @Override
    public String getMessage() {
        return "值编解码器异常, 目标: " + target;
    }
}
