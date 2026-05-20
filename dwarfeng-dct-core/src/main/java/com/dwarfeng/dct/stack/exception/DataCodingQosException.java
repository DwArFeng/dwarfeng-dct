package com.dwarfeng.dct.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 数据编码 QoS 异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataCodingQosException extends HandlerException {

    private static final long serialVersionUID = -5759849306485417198L;

    public DataCodingQosException() {
    }

    public DataCodingQosException(String message) {
        super(message);
    }

    public DataCodingQosException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataCodingQosException(Throwable cause) {
        super(cause);
    }
}
