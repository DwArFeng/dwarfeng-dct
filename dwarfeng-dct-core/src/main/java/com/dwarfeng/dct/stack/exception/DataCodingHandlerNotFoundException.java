package com.dwarfeng.dct.stack.exception;

/**
 * 数据编码处理器未找到异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataCodingHandlerNotFoundException extends DataCodingQosException {

    private static final long serialVersionUID = -3295969680748754788L;

    private final String handlerName;

    public DataCodingHandlerNotFoundException(String handlerName) {
        this.handlerName = handlerName;
    }

    public DataCodingHandlerNotFoundException(Throwable cause, String handlerName) {
        super(cause);
        this.handlerName = handlerName;
    }

    @Override
    public String getMessage() {
        return "应用上下文中没有找到名称为 " + handlerName + " 的数据编码处理器";
    }
}
