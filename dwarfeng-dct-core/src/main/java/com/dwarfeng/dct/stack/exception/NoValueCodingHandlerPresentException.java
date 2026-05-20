package com.dwarfeng.dct.stack.exception;

/**
 * 没有值编码处理器异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class NoValueCodingHandlerPresentException extends ValueCodingQosException {

    private static final long serialVersionUID = -403635148839542156L;

    public NoValueCodingHandlerPresentException() {
    }

    public NoValueCodingHandlerPresentException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "应用上下文中没有值编码处理器";
    }
}
