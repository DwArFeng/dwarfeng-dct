package com.dwarfeng.dct.node.configuration;

import com.dwarfeng.dct.impl.handler.DataCodingHandlerImpl;
import com.dwarfeng.dct.impl.handler.DataCodingQosHandlerImpl;
import com.dwarfeng.dct.impl.handler.ValueCodingHandlerImpl;
import com.dwarfeng.dct.impl.handler.ValueCodingQosHandlerImpl;
import com.dwarfeng.dct.impl.service.DataCodingQosServiceImpl;
import com.dwarfeng.dct.impl.service.ValueCodingQosServiceImpl;
import com.dwarfeng.dct.stack.handler.*;
import com.dwarfeng.dct.stack.service.DataCodingQosService;
import com.dwarfeng.dct.stack.service.ValueCodingQosService;
import com.dwarfeng.dct.stack.struct.DataCodingConfig;
import com.dwarfeng.dct.stack.struct.ValueCodingConfig;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 简单配置。
 *
 * <p>
 * 以最简单的方式实现项目功能的配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Configuration
@ComponentScan(
        basePackages = "com.dwarfeng.dct.impl.handler.vc",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = ValueCodec.class
        )
)
@ComponentScan(
        basePackages = "com.dwarfeng.dct.impl.handler.fdc",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = FlatDataCodec.class
        )
)
public class SimpleConfiguration {

    public static final String BEAN_NAME_DATA_CODING_HANDLER = "dataCodingHandler";
    public static final String BEAN_NAME_VALUE_CODING_HANDLER = "valueCodingHandler";

    @Bean(name = BEAN_NAME_VALUE_CODING_HANDLER)
    public ValueCodingHandler valueCodingHandler(
            List<ValueCodec> valueCodecs
    ) {
        ValueCodingConfig config = new ValueCodingConfig.Builder()
                .addCodecs(valueCodecs)
                .addPreCacheClasses(
                        valueCodecs.stream().map(ValueCodec::getTargetClass).collect(Collectors.toList())
                )
                .addPreCachePrefixes(
                        valueCodecs.stream().map(ValueCodec::getValuePrefix).collect(Collectors.toList())
                )
                .build();
        return new ValueCodingHandlerImpl(config);
    }

    @Bean(name = BEAN_NAME_DATA_CODING_HANDLER)
    public DataCodingHandler dataCodingHandler(
            @Qualifier("fastJsonFlatDataCodec") FlatDataCodec flatDataCodec,
            ValueCodingHandler valueCodingHandler
    ) {
        DataCodingConfig config = new DataCodingConfig.Builder()
                .setFlatDataCodec(flatDataCodec)
                .setValueCodingHandler(valueCodingHandler)
                .build();
        return new DataCodingHandlerImpl(config);
    }

    /**
     * @since 3.0.0
     */
    @Bean
    public DataCodingQosHandler dataCodingQosHandler(
            @Qualifier("fastJsonFlatDataCodec") FlatDataCodec flatDataCodec,
            ValueCodingHandler valueCodingHandler
    ) {
        Map<String, DataCodingHandler> dataCodingHandlerMap = new HashMap<>();
        dataCodingHandlerMap.put(BEAN_NAME_DATA_CODING_HANDLER, dataCodingHandler(flatDataCodec, valueCodingHandler));
        return new DataCodingQosHandlerImpl(dataCodingHandlerMap);
    }

    /**
     * @since 3.0.0
     */
    @Bean
    public ValueCodingQosHandler valueCodingQosHandler(List<ValueCodec> valueCodecs) {
        Map<String, ValueCodingHandler> valueCodingHandlerMap = new HashMap<>();
        valueCodingHandlerMap.put(BEAN_NAME_VALUE_CODING_HANDLER, valueCodingHandler(valueCodecs));
        return new ValueCodingQosHandlerImpl(valueCodingHandlerMap);
    }

    /**
     * @since 3.0.0
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public DataCodingQosService dataCodingQosService(
            DataCodingQosHandler dataCodingQosHandler,
            ServiceExceptionMapper serviceExceptionMapper
    ) {
        return new DataCodingQosServiceImpl(dataCodingQosHandler, serviceExceptionMapper);
    }

    /**
     * @since 3.0.0
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public ValueCodingQosService valueCodingQosService(
            ValueCodingQosHandler valueCodingQosHandler,
            ServiceExceptionMapper serviceExceptionMapper
    ) {
        return new ValueCodingQosServiceImpl(valueCodingQosHandler, serviceExceptionMapper);
    }
}
