package com.dwarfeng.dct.exception;

/**
 * 扁平数据编解码器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FlatDataCodecDecodeException extends FlatDataCodecException {

    private static final long serialVersionUID = 3815842684073299976L;

    protected final String text;

    public FlatDataCodecDecodeException(String text) {
        this.text = text;
    }

    public FlatDataCodecDecodeException(Throwable cause, String text) {
        super(cause);
        this.text = text;
    }

    @Override
    public String getMessage() {
        return "扁平数据编解码器异常, 文本: " + text;
    }
}
