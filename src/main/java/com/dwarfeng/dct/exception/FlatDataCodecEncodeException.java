package com.dwarfeng.dct.exception;

import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dct.handler.FlatDataCodec;

/**
 * 扁平数据编解码器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FlatDataCodecEncodeException extends FlatDataCodecException {

    private static final long serialVersionUID = -1200861456122349554L;

    protected final FlatData target;

    public FlatDataCodecEncodeException(FlatDataCodec codec, FlatData target) {
        super(codec);
        this.target = target;
    }

    public FlatDataCodecEncodeException(Throwable cause, FlatDataCodec codec, FlatData target) {
        super(cause, codec);
        this.target = target;
    }

    @Override
    public String getMessage() {
        return "扁平数据编解码器异常, 编解码器: " + codec + ", 目标: " + target;
    }
}
