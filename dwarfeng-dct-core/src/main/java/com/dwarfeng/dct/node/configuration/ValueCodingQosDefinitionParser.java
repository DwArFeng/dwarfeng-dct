package com.dwarfeng.dct.node.configuration;

import com.dwarfeng.dct.impl.handler.ValueCodingQosHandlerImpl;
import com.dwarfeng.dct.impl.service.ValueCodingQosServiceImpl;
import com.dwarfeng.dct.sdk.util.BeanDefinitionParserUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

/**
 * 值编码 Qos 元素的 BeanDefinitionParser。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ValueCodingQosDefinitionParser implements BeanDefinitionParser {

    @SuppressWarnings("DuplicatedCode")
    @Override
    public BeanDefinition parse(Element element, @Nonnull ParserContext parserContext) {
        String qosHandlerName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("qos-handler-name")
        );
        String qosServiceName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("qos-service-name")
        );
        String semRef = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("sem-ref")
        );

        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, qosHandlerName);
        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, qosServiceName);

        BeanDefinitionBuilder valueCodingQosHandlerBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                ValueCodingQosHandlerImpl.class
        );
        valueCodingQosHandlerBuilder.getRawBeanDefinition().setAutowireMode(
                AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR
        );
        valueCodingQosHandlerBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        valueCodingQosHandlerBuilder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(
                qosHandlerName, valueCodingQosHandlerBuilder.getBeanDefinition()
        );

        BeanDefinitionBuilder valueCodingQosServiceBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                ValueCodingQosServiceImpl.class
        );
        valueCodingQosServiceBuilder.getRawBeanDefinition().setAutowireMode(
                AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR
        );
        ConstructorArgumentValues valueCodingQosServiceConstructorArgumentValues = new ConstructorArgumentValues();
        valueCodingQosServiceConstructorArgumentValues.addIndexedArgumentValue(
                0, new RuntimeBeanReference(qosHandlerName)
        );
        valueCodingQosServiceConstructorArgumentValues.addIndexedArgumentValue(
                1, new RuntimeBeanReference(semRef)
        );
        valueCodingQosServiceBuilder.getRawBeanDefinition().setConstructorArgumentValues(
                valueCodingQosServiceConstructorArgumentValues
        );
        valueCodingQosServiceBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        valueCodingQosServiceBuilder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(
                qosServiceName, valueCodingQosServiceBuilder.getBeanDefinition()
        );

        return null;
    }
}
