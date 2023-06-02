package com.dwarfeng.dct.exception;

import com.dwarfeng.dct.handler.FlatDataCodec;

/**
 * 扁平数据编解码器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FlatDataCodecException extends DctException {

    private static final long serialVersionUID = -4737979205008787683L;

    protected final FlatDataCodec codec;

    public FlatDataCodecException(FlatDataCodec codec) {
        this.codec = codec;
    }

    public FlatDataCodecException(Throwable cause, FlatDataCodec codec) {
        super(cause);
        this.codec = codec;
    }

    @Override
    public String getMessage() {
        return "扁平数据编解码器异常: " + codec;
    }
}
