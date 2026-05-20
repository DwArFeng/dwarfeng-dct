package com.dwarfeng.dct.impl.service;

import com.dwarfeng.dct.stack.handler.ValueCodingQosHandler;
import com.dwarfeng.dct.stack.service.ValueCodingQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 值编码 QoS 服务实现。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ValueCodingQosServiceImpl implements ValueCodingQosService {

    private final ValueCodingQosHandler valueCodingQosHandler;
    private final ServiceExceptionMapper sem;

    public ValueCodingQosServiceImpl(ValueCodingQosHandler valueCodingQosHandler, ServiceExceptionMapper sem) {
        this.valueCodingQosHandler = valueCodingQosHandler;
        this.sem = sem;
    }

    @Override
    public List<String> listHandlerNames() throws ServiceException {
        try {
            return valueCodingQosHandler.listHandlerNames();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("列出所有值编码处理器名称时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Nonnull
    @Override
    public String encode(@Nullable String handlerName, @Nullable Object target) throws ServiceException {
        try {
            return valueCodingQosHandler.encode(handlerName, target);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("值编码时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Nullable
    @Override
    public Object decode(@Nullable String handlerName, @Nonnull String text) throws ServiceException {
        try {
            return valueCodingQosHandler.decode(handlerName, text);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("值解码时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
