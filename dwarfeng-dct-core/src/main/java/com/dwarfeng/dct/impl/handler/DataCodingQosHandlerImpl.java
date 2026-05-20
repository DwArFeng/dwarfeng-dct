package com.dwarfeng.dct.impl.handler;

import com.dwarfeng.dct.stack.exception.AmbiguousDataCodingHandlerException;
import com.dwarfeng.dct.stack.exception.DataCodingHandlerNotFoundException;
import com.dwarfeng.dct.stack.exception.NoDataCodingHandlerPresentException;
import com.dwarfeng.dct.stack.handler.DataCodingHandler;
import com.dwarfeng.dct.stack.handler.DataCodingQosHandler;
import com.dwarfeng.dct.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据编码 QoS 处理器实现。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataCodingQosHandlerImpl implements DataCodingQosHandler {

    private final Map<String, DataCodingHandler> dataCodingHandlerMap;

    public DataCodingQosHandlerImpl(Map<String, DataCodingHandler> dataCodingHandlerMap) {
        this.dataCodingHandlerMap = Optional.ofNullable(dataCodingHandlerMap).orElse(Collections.emptyMap());
    }

    @Override
    public List<String> listHandlerNames() throws HandlerException {
        try {
            List<String> handlerNames = dataCodingHandlerMap.keySet().stream().sorted().collect(Collectors.toList());
            return Collections.unmodifiableList(handlerNames);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Nonnull
    @Override
    public String encode(@Nullable String handlerName, @Nonnull Data data) throws HandlerException {
        try {
            return determineHandler(handlerName).encode(data);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Nonnull
    @Override
    public Data decode(@Nullable String handlerName, @Nonnull String string) throws HandlerException {
        try {
            return determineHandler(handlerName).decode(string);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private DataCodingHandler determineHandler(@Nullable String handlerName) throws Exception {
        if (dataCodingHandlerMap.isEmpty()) {
            throw new NoDataCodingHandlerPresentException();
        }
        if (handlerName == null) {
            if (dataCodingHandlerMap.size() == 1) {
                return dataCodingHandlerMap.values().iterator().next();
            } else {
                throw new AmbiguousDataCodingHandlerException();
            }
        } else {
            if (!dataCodingHandlerMap.containsKey(handlerName)) {
                throw new DataCodingHandlerNotFoundException(handlerName);
            }
            return dataCodingHandlerMap.get(handlerName);
        }
    }

    @Override
    public String toString() {
        return "DataCodingQosHandlerImpl{" +
                "dataCodingHandlerMap=" + dataCodingHandlerMap +
                '}';
    }
}
