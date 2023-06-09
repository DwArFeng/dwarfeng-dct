package com.dwarfeng.dct.exception;

/**
 * 扁平数据编解码器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FlatDataCodecException extends DctException {

    private static final long serialVersionUID = 2319576608361757700L;

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
