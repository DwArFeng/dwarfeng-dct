package com.dwarfeng.dct.configuration;

import com.dwarfeng.dct.handler.*;
import com.dwarfeng.dct.struct.DataCodingConfig;
import com.dwarfeng.dct.struct.ValueCodingConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.List;
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
        basePackages = "com.dwarfeng.dct.handler.vc",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = ValueCodec.class
        )
)
@ComponentScan(
        basePackages = "com.dwarfeng.dct.handler.fdc",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = FlatDataCodec.class
        )
)
public class SimpleConfiguration {

    @Bean
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

    @Bean
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
}
