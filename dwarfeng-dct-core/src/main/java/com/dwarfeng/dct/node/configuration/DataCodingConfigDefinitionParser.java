package com.dwarfeng.dct.node.configuration;

import com.dwarfeng.dct.sdk.util.BeanDefinitionParserUtil;
import com.dwarfeng.dct.stack.struct.DataCodingConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

/**
 * 数据编码 Config 元素的 BeanDefinitionParser。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DataCodingConfigDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, @Nonnull ParserContext parserContext) {
        String configName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("config-name")
        );
        String flatDataCodecRef = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("flat-data-codec-ref")
        );
        String valueCodingHandlerRef = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("value-coding-handler-ref")
        );

        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, configName);

        RootBeanDefinition dataCodingConfigBuilderBeanDefinition = new RootBeanDefinition(
                DataCodingConfig.Builder.class
        );
        dataCodingConfigBuilderBeanDefinition.getPropertyValues().add(
                "flatDataCodec", new RuntimeBeanReference(flatDataCodecRef)
        );
        dataCodingConfigBuilderBeanDefinition.getPropertyValues().add(
                "valueCodingHandler", new RuntimeBeanReference(valueCodingHandlerRef)
        );
        dataCodingConfigBuilderBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        dataCodingConfigBuilderBeanDefinition.setLazyInit(false);
        String dataCodingConfigBuilderBeanName = BeanDefinitionParserUtil.parseAvailableBeanName(
                parserContext, configName + "ConfigBuilder"
        );
        parserContext.getRegistry().registerBeanDefinition(
                dataCodingConfigBuilderBeanName, dataCodingConfigBuilderBeanDefinition
        );

        RootBeanDefinition dataCodingConfigBeanDefinition = new RootBeanDefinition(DataCodingConfig.class);
        dataCodingConfigBeanDefinition.setFactoryBeanName(dataCodingConfigBuilderBeanName);
        dataCodingConfigBeanDefinition.setFactoryMethodName("build");
        dataCodingConfigBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        dataCodingConfigBeanDefinition.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(configName, dataCodingConfigBeanDefinition);

        return null;
    }
}
