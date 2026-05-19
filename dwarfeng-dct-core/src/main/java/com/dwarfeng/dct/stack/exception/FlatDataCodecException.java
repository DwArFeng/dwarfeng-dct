package com.dwarfeng.dct.stack.exception;

/**
 * 扁平数据编解码器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FlatDataCodecException extends DctException {

    private static final long serialVersionUID = -6736829674130458863L;

    public FlatDataCodecException() {
    }

    public FlatDataCodecException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "扁平数据编解码器异常";
    }
}
