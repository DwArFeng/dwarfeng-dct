package com.dwarfeng.dct.sdk.configuration;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.TypeFilter;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 值编解码器类路径 Bean 定义扫描器。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class ValueCodecClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private final Set<String> scannedBeanNames = new LinkedHashSet<>();

    public ValueCodecClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, Environment environment) {
        super(registry, false, environment);
        super.addIncludeFilter(ValueCodecTypeFilter.INSTANCE);
        super.setBeanNameGenerator(DefaultBeanNameGenerator.INSTANCE);
    }

    @Override
    public void addIncludeFilter(@Nonnull TypeFilter includeFilter) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @Override
    public void addExcludeFilter(@Nonnull TypeFilter excludeFilter) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @Override
    protected void postProcessBeanDefinition(
            @Nonnull AbstractBeanDefinition beanDefinition, @Nonnull String beanName
    ) {
        scannedBeanNames.add(beanName);
    }

    public Set<String> getScannedBeanNames() {
        return scannedBeanNames;
    }

    @Override
    public String toString() {
        return "ValueCodecClassPathBeanDefinitionScanner{" +
                "scannedBeanNames=" + scannedBeanNames +
                '}';
    }
}
