package com.dwarfeng.dct.handler;

import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import javax.annotation.Nonnull;

/**
 * 数据编码处理器。
 *
 * <p>
 * 数据编码处理器将数据编码为文本，或者将文本解码为数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DataCodingHandler extends Handler {

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
     * 将指定的数据编码为文本。
     *
     * @param data 指定的数据。
     * @return 指定的数据编码后的文本。
     * @throws HandlerException 处理器异常。
     */
    @Nonnull
    String encode(@Nonnull Data data) throws HandlerException;

    /**
     * 将指定的文本解码为数据。
     *
     * @param string 指定的文本。
     * @return 指定的文本解码后的数据。
     * @throws HandlerException 处理器异常。
     */
    @Nonnull
    Data decode(@Nonnull String string) throws HandlerException;
}
