package com.dwarfeng.dct.exception;

import com.dwarfeng.dct.handler.FlatDataCodec;

/**
 * 扁平数据编解码器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FlatDataCodecDecodeException extends FlatDataCodecException {

    private static final long serialVersionUID = 6254643257634679128L;

    protected final String text;

    public FlatDataCodecDecodeException(FlatDataCodec codec, String text) {
        super(codec);
        this.text = text;
    }

    public FlatDataCodecDecodeException(Throwable cause, FlatDataCodec codec, String text) {
        super(cause, codec);
        this.text = text;
    }

    @Override
    public String getMessage() {
        return "扁平数据编解码器异常, 解码器: " + codec + ", 文本: " + text;
    }
}
