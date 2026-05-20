package com.dwarfeng.dct.impl.service;

import com.dwarfeng.dct.stack.handler.DataCodingQosHandler;
import com.dwarfeng.dct.stack.service.DataCodingQosService;
import com.dwarfeng.dct.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 数据编码 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataCodingQosServiceImpl implements DataCodingQosService {

    private final DataCodingQosHandler dataCodingQosHandler;
    private final ServiceExceptionMapper sem;

    public DataCodingQosServiceImpl(DataCodingQosHandler dataCodingQosHandler, ServiceExceptionMapper sem) {
        this.dataCodingQosHandler = dataCodingQosHandler;
        this.sem = sem;
    }

    @Override
    public List<String> listHandlerNames() throws ServiceException {
        try {
            return dataCodingQosHandler.listHandlerNames();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("列出所有数据编码处理器名称时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Nonnull
    @Override
    public String encode(@Nullable String handlerName, @Nonnull Data data) throws ServiceException {
        try {
            return dataCodingQosHandler.encode(handlerName, data);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("数据编码时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Nonnull
    @Override
    public Data decode(@Nullable String handlerName, @Nonnull String string) throws ServiceException {
        try {
            return dataCodingQosHandler.decode(handlerName, string);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("数据解码时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
