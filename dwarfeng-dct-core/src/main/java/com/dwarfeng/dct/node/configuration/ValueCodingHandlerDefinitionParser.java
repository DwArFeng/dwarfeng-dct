package com.dwarfeng.dct.node.configuration;

import com.dwarfeng.dct.impl.handler.ValueCodingHandlerImpl;
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
 * 值编码 Handler 元素的 BeanDefinitionParser。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ValueCodingHandlerDefinitionParser implements BeanDefinitionParser {

    @SuppressWarnings("DuplicatedCode")
    @Override
    public BeanDefinition parse(Element element, @Nonnull ParserContext parserContext) {
        String handlerName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("handler-name")
        );
        String configRef = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("config-ref")
        );

        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, handlerName);

        BeanDefinitionBuilder valueCodingHandlerBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                ValueCodingHandlerImpl.class
        );
        valueCodingHandlerBuilder.getRawBeanDefinition().setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        ConstructorArgumentValues valueCodingHandlerConstructorArgumentValues = new ConstructorArgumentValues();
        valueCodingHandlerConstructorArgumentValues.addIndexedArgumentValue(
                0, new RuntimeBeanReference(configRef)
        );
        valueCodingHandlerBuilder.getRawBeanDefinition().setConstructorArgumentValues(
                valueCodingHandlerConstructorArgumentValues
        );
        valueCodingHandlerBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        valueCodingHandlerBuilder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(handlerName, valueCodingHandlerBuilder.getBeanDefinition());

        return null;
    }
}
