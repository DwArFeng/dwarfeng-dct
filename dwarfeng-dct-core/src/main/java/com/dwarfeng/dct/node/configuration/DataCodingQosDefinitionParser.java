package com.dwarfeng.dct.node.configuration;

import com.dwarfeng.dct.impl.handler.DataCodingQosHandlerImpl;
import com.dwarfeng.dct.impl.service.DataCodingQosServiceImpl;
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
 * 数据编码 Qos 元素的 BeanDefinitionParser。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataCodingQosDefinitionParser implements BeanDefinitionParser {

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

        BeanDefinitionBuilder dataCodingQosHandlerBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                DataCodingQosHandlerImpl.class
        );
        dataCodingQosHandlerBuilder.getRawBeanDefinition().setAutowireMode(
                AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR
        );
        dataCodingQosHandlerBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        dataCodingQosHandlerBuilder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(
                qosHandlerName, dataCodingQosHandlerBuilder.getBeanDefinition()
        );

        BeanDefinitionBuilder dataCodingQosServiceBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                DataCodingQosServiceImpl.class
        );
        dataCodingQosServiceBuilder.getRawBeanDefinition().setAutowireMode(
                AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR
        );
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
        parserContext.getRegistry().registerBeanDefinition(
                qosServiceName, dataCodingQosServiceBuilder.getBeanDefinition()
        );

        return null;
    }
}
