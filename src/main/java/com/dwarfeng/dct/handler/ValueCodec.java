package com.dwarfeng.dct.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nonnull;

/**
 * 值编解码器。
 *
 * @author DwArFeng
 * @see ValueCodingHandler#encode(Object)
 * @see ValueCodingHandler#decode(String)
 * @see ValueCodingHandlerImpl
 * @since 1.0.0
 */
public interface ValueCodec {

    /**
     * 获取目标类。
     *
     * <p>
     * 该方法每次被调用时，返回的值必须是一样的。
     *
     * @return 目标类。
     */
    @Nonnull
    Class<?> getTargetClass();

    /**
     * 获取值前缀。
     *
     * <p>
     * 该方法每次被调用时，返回的值必须是一样的。
     *
     * @return 值前缀。
     */
    @Nonnull
    String getValuePrefix();

    /**
     * 获取优先级。
     *
     * <p>
     * 该方法每次被调用时，返回的值必须是一样的。
     *
     * <p>
     * 需要注意的是，返回的值越小，优先级越高。
     *
     * @return 优先级。
     * @see com.dwarfeng.dct.util.Constants#VALUE_CODEC_PRIORITY_MAX
     * @see com.dwarfeng.dct.util.Constants#VALUE_CODEC_PRIORITY_MIN
     */
    int getPriority();

    /**
     * 将指定的目标对象编码为文本。
     *
     * <p>
     * 与 {@link ValueCodingHandler#encode(Object)}不同的是，该方法传入的目标对象不会为 null。
     *
     * @param target 指定的目标对象。
     * @return 指定的目标对象编码后的文本。
     * @throws HandlerException 处理器异常。
     */
    @Nonnull
    String encode(@Nonnull Object target) throws HandlerException;

    /**
     * 将指定的值文本解码为对象。
     *
     * <p>
     * 与 {@link ValueCodingHandler#decode(String)}不同的是，该方法返回的解码后的对象不会为 null。
     *
     * @param text 指定的值文本。
     * @return 指定的值文本解码后的对象。
     * @throws HandlerException 处理器异常。
     */
    @Nonnull
    Object decode(@Nonnull String text) throws HandlerException;
}
