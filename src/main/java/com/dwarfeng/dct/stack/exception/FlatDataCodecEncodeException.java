package com.dwarfeng.dct.stack.exception;

import com.dwarfeng.dct.stack.bean.dto.FlatData;

/**
 * 扁平数据编解码器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FlatDataCodecEncodeException extends FlatDataCodecException {

    private static final long serialVersionUID = 3415244234867805014L;
    
    protected final FlatData target;

    public FlatDataCodecEncodeException(FlatData target) {
        this.target = target;
    }

    public FlatDataCodecEncodeException(Throwable cause, FlatData target) {
        super(cause);
        this.target = target;
    }

    @Override
    public String getMessage() {
        return "扁平数据编解码器异常, 目标: " + target;
    }
}
