package com.dwarfeng.dct.api.integration.configuration;

import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 服务异常映射配置。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> des = ServiceExceptionHelper.putDefaultDestination(null);
        des = com.dwarfeng.dct.sdk.util.ServiceExceptionHelper.putDefaultDestination(des);
        des = com.dwarfeng.springtelqos.sdk.util.ServiceExceptionHelper.putDefaultDestination(des);
        return new MapServiceExceptionMapper(des, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINED);
    }
}
