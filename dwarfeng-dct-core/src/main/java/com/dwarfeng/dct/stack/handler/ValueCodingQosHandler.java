package com.dwarfeng.dct.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 值编码 QoS 处理器。
 *
 * <p>
 * 参数 <code>handlerName</code> 为对应 {@link ValueCodingHandler} 实例的 <code>bean name</code>。<br>
 * 当应用上下文中只有一个 {@link ValueCodingHandler} 时，参数 <code>handlerName</code> 可以为 <code>null</code>。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public interface ValueCodingQosHandler {

    /**
     * 列出所有值编码处理器名称。
     *
     * <p>
     * 返回结果按字典序排序且不可变。
     *
     * @return 所有处理器的名称组成的列表（按字典序排序，不可变）。
     * @throws HandlerException 处理器异常。
     */
    List<String> listHandlerNames() throws HandlerException;

    /**
     * 将指定的目标对象编码为文本。
     *
     * @param handlerName 处理器名称。
     * @param target      指定的目标对象。
     * @return 指定的目标对象编码后的文本。
     * @throws HandlerException 处理器异常。
     */
    @Nonnull
    String encode(@Nullable String handlerName, @Nullable Object target) throws HandlerException;

    /**
     * 将指定的值文本解码为对象。
     *
     * @param handlerName 处理器名称。
     * @param text        指定的值文本。
     * @return 指定的值文本解码后的对象。
     * @throws HandlerException 处理器异常。
     */
    @Nullable
    Object decode(@Nullable String handlerName, @Nonnull String text) throws HandlerException;
}
