package com.dwarfeng.dct.exception;

/**
 * 值编解码器解码异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ValueCodecDecodeException extends ValueCodecException {

    private static final long serialVersionUID = 1899007943556452397L;

    protected final String text;

    public ValueCodecDecodeException(String text) {
        this.text = text;
    }

    public ValueCodecDecodeException(Throwable cause, String text) {
        super(cause);
        this.text = text;
    }

    @Override
    public String getMessage() {
        return "值编解码器异常, 文本: " + text;
    }
}
