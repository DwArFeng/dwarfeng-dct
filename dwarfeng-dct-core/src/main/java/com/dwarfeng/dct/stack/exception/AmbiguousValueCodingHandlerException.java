package com.dwarfeng.dct.stack.exception;

/**
 * 值编码处理器歧义异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class AmbiguousValueCodingHandlerException extends ValueCodingQosException {

    private static final long serialVersionUID = 7481395341308778231L;

    public AmbiguousValueCodingHandlerException() {
    }

    public AmbiguousValueCodingHandlerException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "应用上下文中有多个值编码处理器, 但是没有指定 handlerName";
    }
}
