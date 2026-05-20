package com.dwarfeng.dct.node.configuration;

import com.dwarfeng.dct.sdk.configuration.ValueCodecClassPathBeanDefinitionScanner;
import com.dwarfeng.dct.sdk.util.BeanDefinitionParserUtil;
import com.dwarfeng.dct.stack.struct.ValueCodingConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 值编码 Config 元素的 BeanDefinitionParser。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class ValueCodingConfigDefinitionParser implements BeanDefinitionParser {

    private static final String DCT_NAMESPACE_URL = "http://dwarfeng.com/schema/dwarfeng-dct";

    @Override
    public BeanDefinition parse(Element element, @Nonnull ParserContext parserContext) {
        String configName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, element.getAttribute("config-name")
        );

        BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, configName);

        Element valueCodecElement = (Element) element
                .getElementsByTagNameNS(DCT_NAMESPACE_URL, "value-codec").item(0);

        ManagedList<BeanReference> codecBeanReferences = new ManagedList<>();
        if (Objects.nonNull(valueCodecElement)) {
            NodeList valueCodecImpls = valueCodecElement.getElementsByTagNameNS(
                    DCT_NAMESPACE_URL, "value-codec-impl"
            );
            for (int i = 0; i < valueCodecImpls.getLength(); i++) {
                Element valueCodecImplElement = (Element) valueCodecImpls.item(i);
                codecBeanReferences.addAll(parseValueCodecImpl(valueCodecImplElement, parserContext));
            }
        }

        RootBeanDefinition valueCodingConfigBuilderBeanDefinition = new RootBeanDefinition(
                ValueCodingConfig.Builder.class
        );
        valueCodingConfigBuilderBeanDefinition.getPropertyValues().add("codecs", codecBeanReferences);
        valueCodingConfigBuilderBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        valueCodingConfigBuilderBeanDefinition.setLazyInit(false);
        String valueCodingConfigBuilderBeanName = BeanDefinitionParserUtil.parseAvailableBeanName(
                parserContext, configName + "ConfigBuilder"
        );
        parserContext.getRegistry().registerBeanDefinition(
                valueCodingConfigBuilderBeanName, valueCodingConfigBuilderBeanDefinition
        );

        RootBeanDefinition valueCodingConfigBeanDefinition = new RootBeanDefinition(ValueCodingConfig.class);
        valueCodingConfigBeanDefinition.setFactoryBeanName(valueCodingConfigBuilderBeanName);
        valueCodingConfigBeanDefinition.setFactoryMethodName("build");
        valueCodingConfigBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        valueCodingConfigBeanDefinition.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(configName, valueCodingConfigBeanDefinition);

        return null;
    }

    private Set<BeanReference> parseValueCodecImpl(Element valueCodecImplElement, ParserContext parserContext) {
        String codecName = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, valueCodecImplElement.getAttribute("codec-name")
        );
        String clazz = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, valueCodecImplElement.getAttribute("class")
        );
        String codecRef = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, valueCodecImplElement.getAttribute("codec-ref")
        );
        String packageScan = (String) BeanDefinitionParserUtil.mayResolveSpel(
                parserContext, valueCodecImplElement.getAttribute("package-scan")
        );

        if (StringUtils.isNotEmpty(codecRef)) {
            return Collections.singleton(new RuntimeBeanReference(codecRef));
        } else if (StringUtils.isNotEmpty(packageScan)) {
            ValueCodecClassPathBeanDefinitionScanner scanner = new ValueCodecClassPathBeanDefinitionScanner(
                    parserContext.getRegistry(), parserContext.getReaderContext().getEnvironment()
            );
            scanner.scan(packageScan);
            Set<String> beanNames = scanner.getScannedBeanNames();
            Set<BeanReference> beanReferenceSet = new LinkedHashSet<>(beanNames.size());
            for (String beanName : beanNames) {
                beanReferenceSet.add(new RuntimeBeanReference(beanName));
            }
            return beanReferenceSet;
        } else {
            BeanDefinitionParserUtil.makeSureBeanNameNotDuplicated(parserContext, codecName);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
            builder.setScope(BeanDefinition.SCOPE_SINGLETON);
            builder.setLazyInit(false);
            parserContext.getRegistry().registerBeanDefinition(codecName, builder.getBeanDefinition());
            return Collections.singleton(new RuntimeBeanReference(codecName));
        }
    }
}
