package com.dwarfeng.dct.node.configuration;

import com.dwarfeng.dct.impl.handler.DataCodingQosHandlerImpl;
import com.dwarfeng.dct.impl.service.DataCodingQosServiceImpl;
import com.dwarfeng.dct.sdk.util.BeanDefinitionParserUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

/**
 * 数据编码 Qos 元素的 BeanDefinitionParser。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataCodingQosDefinitionParser implements BeanDefinitionParser {

    @SuppressWarnings("DuplicatedCode")
    @Override
    public BeanDefinition parse(Element element, @Nonnull ParserContext parserContext) {
        String serviceName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("service-name")
        );
        String qosHandlerName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("qos-handler-name")
        );
        String handlerRef = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("handler-ref")
        );
        String semRef = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("sem-ref")
        );

        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, qosHandlerName);
        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, serviceName);

        ManagedMap<String, BeanReference> dataCodingHandlerMap = new ManagedMap<>();
        dataCodingHandlerMap.put(handlerRef, new RuntimeBeanReference(handlerRef));

        BeanDefinitionBuilder dataCodingQosHandlerBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                DataCodingQosHandlerImpl.class
        );
        dataCodingQosHandlerBuilder.getRawBeanDefinition().setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        ConstructorArgumentValues dataCodingQosHandlerConstructorArgumentValues = new ConstructorArgumentValues();
        dataCodingQosHandlerConstructorArgumentValues.addIndexedArgumentValue(0, dataCodingHandlerMap);
        dataCodingQosHandlerBuilder.getRawBeanDefinition().setConstructorArgumentValues(
                dataCodingQosHandlerConstructorArgumentValues
        );
        dataCodingQosHandlerBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        dataCodingQosHandlerBuilder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(
                qosHandlerName, dataCodingQosHandlerBuilder.getBeanDefinition()
        );

        BeanDefinitionBuilder dataCodingQosServiceBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                DataCodingQosServiceImpl.class
        );
        dataCodingQosServiceBuilder.getRawBeanDefinition().setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        ConstructorArgumentValues dataCodingQosServiceConstructorArgumentValues = new ConstructorArgumentValues();
        dataCodingQosServiceConstructorArgumentValues.addIndexedArgumentValue(
                0, new RuntimeBeanReference(qosHandlerName)
        );
        dataCodingQosServiceConstructorArgumentValues.addIndexedArgumentValue(
                1, new RuntimeBeanReference(semRef)
        );
        dataCodingQosServiceBuilder.getRawBeanDefinition().setConstructorArgumentValues(
                dataCodingQosServiceConstructorArgumentValues
        );
        dataCodingQosServiceBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        dataCodingQosServiceBuilder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(serviceName, dataCodingQosServiceBuilder.getBeanDefinition());

        return null;
    }
}
