package com.dwarfeng.dct.stack.exception;

/**
 * 没有数据编码处理器异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class NoDataCodingHandlerPresentException extends DataCodingQosException {

    private static final long serialVersionUID = -3119297623696940135L;

    public NoDataCodingHandlerPresentException() {
    }

    public NoDataCodingHandlerPresentException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "应用上下文中没有数据编码处理器";
    }
}
