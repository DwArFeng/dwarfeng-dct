package com.dwarfeng.dct.node.configuration;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * DCT 命名空间处理器。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public class DctNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("value-coding-config", new ValueCodingConfigDefinitionParser());
        registerBeanDefinitionParser("data-coding-config", new DataCodingConfigDefinitionParser());
        registerBeanDefinitionParser("value-coding-handler", new ValueCodingHandlerDefinitionParser());
        registerBeanDefinitionParser("data-coding-handler", new DataCodingHandlerDefinitionParser());
        registerBeanDefinitionParser("value-coding-qos", new ValueCodingQosDefinitionParser());
        registerBeanDefinitionParser("data-coding-qos", new DataCodingQosDefinitionParser());
    }
}
