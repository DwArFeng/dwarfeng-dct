package com.dwarfeng.dct.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * DCT 模块值编码 Handler 层基础异常。
 *
 * <p>
 * 表示与值编码处理器相关的失败语义，具体场景由子类细分（如值编解码失败）。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ValueCodingException extends HandlerException {

    private static final long serialVersionUID = 3363707378726221720L;

    public ValueCodingException() {
        super();
    }

    public ValueCodingException(String message) {
        super(message);
    }

    public ValueCodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueCodingException(Throwable cause) {
        super(cause);
    }
}
