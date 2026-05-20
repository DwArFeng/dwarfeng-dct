package com.dwarfeng.dct.stack.exception;

/**
 * 值编码处理器未找到异常。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ValueCodingHandlerNotFoundException extends ValueCodingQosException {

    private static final long serialVersionUID = 4177843505711104131L;

    private final String handlerName;

    public ValueCodingHandlerNotFoundException(String handlerName) {
        this.handlerName = handlerName;
    }

    public ValueCodingHandlerNotFoundException(Throwable cause, String handlerName) {
        super(cause);
        this.handlerName = handlerName;
    }

    @Override
    public String getMessage() {
        return "应用上下文中没有找到名称为 " + handlerName + " 的值编码处理器";
    }
}
