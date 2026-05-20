package com.dwarfeng.dct.stack.service;

import com.dwarfeng.dct.stack.handler.DataCodingHandler;
import com.dwarfeng.dct.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 数据编码 QoS 服务。
 *
 * <p>
 * 参数 <code>handlerName</code> 为对应 {@link DataCodingHandler} 实例的 <code>bean name</code>。<br>
 * 当应用上下文中只有一个 {@link DataCodingHandler} 时，参数 <code>handlerName</code> 可以为 <code>null</code>。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public interface DataCodingQosService {

    /**
     * 列出所有数据编码处理器名称。
     *
     * <p>
     * 返回结果按字典序排序且不可变。
     *
     * @return 所有处理器的名称组成的列表（按字典序排序，不可变）。
     * @throws ServiceException 服务异常。
     */
    List<String> listHandlerNames() throws ServiceException;

    /**
     * 将指定的数据编码为文本。
     *
     * @param handlerName 处理器名称。
     * @param data        指定的数据。
     * @return 指定的数据编码后的文本。
     * @throws ServiceException 服务异常。
     */
    @Nonnull
    String encode(@Nullable String handlerName, @Nonnull Data data) throws ServiceException;

    /**
     * 将指定的文本解码为数据。
     *
     * @param handlerName 处理器名称。
     * @param string      指定的文本。
     * @return 指定的文本解码后的数据。
     * @throws ServiceException 服务异常。
     */
    @Nonnull
    Data decode(@Nullable String handlerName, @Nonnull String string) throws ServiceException;
}
