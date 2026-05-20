package com.dwarfeng.dct.stack.exception;

/**
 * 数据编码处理器歧义异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class AmbiguousDataCodingHandlerException extends DataCodingQosException {

    private static final long serialVersionUID = 2085701651811988144L;

    public AmbiguousDataCodingHandlerException() {
    }

    public AmbiguousDataCodingHandlerException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "应用上下文中有多个数据编码处理器, 但是没有指定 handlerName";
    }
}
