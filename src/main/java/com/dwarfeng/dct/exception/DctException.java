package com.dwarfeng.dct.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * DCT 异常。
 *
 * <p>
 * 该异常是 dwarfeng-dct 中所有异常的父类。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DctException extends HandlerException {

    private static final long serialVersionUID = -5955902266794271479L;

    public DctException() {
    }

    public DctException(String message, Throwable cause) {
        super(message, cause);
    }

    public DctException(String message) {
        super(message);
    }

    public DctException(Throwable cause) {
        super(cause);
    }
}
