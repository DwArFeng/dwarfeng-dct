package com.dwarfeng.dct.sdk.configuration;

import com.dwarfeng.dct.stack.handler.ValueCodec;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * 值编解码器类型过滤器。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
final class ValueCodecTypeFilter implements TypeFilter {

    public static final ValueCodecTypeFilter INSTANCE = new ValueCodecTypeFilter();

    private final AssignableTypeFilter assignableTypeFilter = new AssignableTypeFilter(ValueCodec.class);

    @Override
    public boolean match(@Nonnull MetadataReader metadataReader, @Nonnull MetadataReaderFactory metadataReaderFactory)
            throws IOException {
        return assignableTypeFilter.match(metadataReader, metadataReaderFactory);
    }
}
