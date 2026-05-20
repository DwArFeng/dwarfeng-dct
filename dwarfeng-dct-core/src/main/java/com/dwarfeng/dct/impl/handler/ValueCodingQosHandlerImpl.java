package com.dwarfeng.dct.impl.handler;

import com.dwarfeng.dct.stack.exception.AmbiguousValueCodingHandlerException;
import com.dwarfeng.dct.stack.exception.NoValueCodingHandlerPresentException;
import com.dwarfeng.dct.stack.exception.ValueCodingHandlerNotFoundException;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.dct.stack.handler.ValueCodingQosHandler;
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
 * 值编码 QoS 处理器实现。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ValueCodingQosHandlerImpl implements ValueCodingQosHandler {

    private final Map<String, ValueCodingHandler> valueCodingHandlerMap;

    public ValueCodingQosHandlerImpl(Map<String, ValueCodingHandler> valueCodingHandlerMap) {
        this.valueCodingHandlerMap = Optional.ofNullable(valueCodingHandlerMap).orElse(Collections.emptyMap());
    }

    @Override
    public List<String> listHandlerNames() throws HandlerException {
        try {
            List<String> handlerNames = valueCodingHandlerMap.keySet().stream().sorted().collect(Collectors.toList());
            return Collections.unmodifiableList(handlerNames);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Nonnull
    @Override
    public String encode(@Nullable String handlerName, @Nullable Object target) throws HandlerException {
        try {
            return determineHandler(handlerName).encode(target);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    @Nullable
    @Override
    public Object decode(@Nullable String handlerName, @Nonnull String text) throws HandlerException {
        try {
            return determineHandler(handlerName).decode(text);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private ValueCodingHandler determineHandler(@Nullable String handlerName) throws Exception {
        if (valueCodingHandlerMap.isEmpty()) {
            throw new NoValueCodingHandlerPresentException();
        }
        if (handlerName == null) {
            if (valueCodingHandlerMap.size() == 1) {
                return valueCodingHandlerMap.values().iterator().next();
            } else {
                throw new AmbiguousValueCodingHandlerException();
            }
        } else {
            if (!valueCodingHandlerMap.containsKey(handlerName)) {
                throw new ValueCodingHandlerNotFoundException(handlerName);
            }
            return valueCodingHandlerMap.get(handlerName);
        }
    }

    @Override
    public String toString() {
        return "ValueCodingQosHandlerImpl{" +
                "valueCodingHandlerMap=" + valueCodingHandlerMap +
                '}';
    }
}
