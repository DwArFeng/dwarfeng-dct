package com.dwarfeng.dct.node.configuration;

import com.dwarfeng.dct.impl.handler.DataCodingHandlerImpl;
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
 * 数据编码 Handler 元素的 BeanDefinitionParser。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataCodingHandlerDefinitionParser implements BeanDefinitionParser {

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

        BeanDefinitionBuilder dataCodingHandlerBuilder = BeanDefinitionBuilder.rootBeanDefinition(
                DataCodingHandlerImpl.class
        );
        dataCodingHandlerBuilder.getRawBeanDefinition().setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        ConstructorArgumentValues dataCodingHandlerConstructorArgumentValues = new ConstructorArgumentValues();
        dataCodingHandlerConstructorArgumentValues.addIndexedArgumentValue(
                0, new RuntimeBeanReference(configRef)
        );
        dataCodingHandlerBuilder.getRawBeanDefinition().setConstructorArgumentValues(
                dataCodingHandlerConstructorArgumentValues
        );
        dataCodingHandlerBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        dataCodingHandlerBuilder.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(handlerName, dataCodingHandlerBuilder.getBeanDefinition());

        return null;
    }
}
