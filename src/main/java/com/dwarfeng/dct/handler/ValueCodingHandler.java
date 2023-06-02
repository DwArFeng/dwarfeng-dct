package com.dwarfeng.dct.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 值编码处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface ValueCodingHandler extends Handler {

    /**
     * 初始化处理器。
     *
     * <p>
     * 该方法在使用处理器之前必须被调用，且如果多次调用该方法，只有第一次调用时有效。
     *
     * @throws HandlerException 处理器异常。
     */
    void init() throws HandlerException;

    /**
     * 将指定的目标对象编码为文本。
     *
     * @param target 指定的目标对象。
     * @return 指定的目标对象编码后的文本。
     * @throws HandlerException 处理器异常。
     */
    @Nonnull
    String encode(@Nullable Object target) throws HandlerException;

    /**
     * 将指定的值文本解码为对象。
     *
     * @param text 指定的值文本。
     * @return 指定的值文本解码后的对象。
     * @throws HandlerException 处理器异常。
     */
    @Nullable
    Object decode(@Nonnull String text) throws HandlerException;
}
