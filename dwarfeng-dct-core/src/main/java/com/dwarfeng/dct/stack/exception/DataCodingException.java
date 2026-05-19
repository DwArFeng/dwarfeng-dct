package com.dwarfeng.dct.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * DCT 模块数据编码 Handler 层基础异常。
 *
 * <p>
 * 表示与数据编码处理器相关的失败语义，具体场景由子类细分（如扁平数据编解码失败）。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataCodingException extends HandlerException {

    private static final long serialVersionUID = -4537228310078236173L;

    public DataCodingException() {
        super();
    }

    public DataCodingException(String message) {
        super(message);
    }

    public DataCodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataCodingException(Throwable cause) {
        super(cause);
    }
}
