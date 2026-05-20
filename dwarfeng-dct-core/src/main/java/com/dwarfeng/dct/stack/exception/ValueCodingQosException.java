package com.dwarfeng.dct.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 值编码 QoS 异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ValueCodingQosException extends HandlerException {

    private static final long serialVersionUID = 5556403664514551419L;

    public ValueCodingQosException() {
    }

    public ValueCodingQosException(String message) {
        super(message);
    }

    public ValueCodingQosException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueCodingQosException(Throwable cause) {
        super(cause);
    }
}
