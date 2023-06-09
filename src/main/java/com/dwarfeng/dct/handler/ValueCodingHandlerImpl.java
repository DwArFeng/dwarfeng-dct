package com.dwarfeng.dct.handler;

import com.dwarfeng.dct.exception.DctException;
import com.dwarfeng.dct.struct.ValueCodingConfig;
import com.dwarfeng.dct.util.Constants;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 值编码处理器实现。
 *
 * <p>
 * 该实现使用一个 {@link ValueCodingConfig} 作为配置，该配置中包含了一个由 {@link ValueCodec} 组成的列表。<br>
 * 列表中的 {@link ValueCodec} 组成了解析的空间，该处理器能够处理解析空间所提供的所有类型的对象。
 *
 * <p>
 * 该实现使用两个缓存，一个是类缓存，另一个是前缀缓存，
 * 它们存放了已知的目标类或文本前缀与 {@link ValueCodec} 的对应关系。<br>
 * 处理器编码或解码一个对象时，会首先尝试使用预缓存的 {@link ValueCodec} 进行编码或解码，
 * 如果缓存中没有对应的 {@link ValueCodec}，则会遍历解析空间中的 {@link ValueCodec}，找到所有支持的 {@link ValueCodec}，
 * 取出优先级最高的 {@link ValueCodec}，将其放入缓存中，并使用该 {@link ValueCodec} 进行编码或解码。
 *
 * <p>
 * <code>null</code> 是一种特殊的目标对象，它不属于任何一个类。在该实现中，它被编码为一个常量，这个常量没有前缀，
 * 并且在编码或解码的过程中会被特殊处理。
 *
 * <p>
 * 该实现在初始化方法中提供了预缓存的功能，可以将一些常用的 {@link ValueCodec} 预先放入缓存中，以提高性能。<br>
 * 需要预缓存的目标类与文本前缀可以在 {@link ValueCodingConfig} 中指定。
 *
 * <p>
 * 该实现是线程安全的。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ValueCodingHandlerImpl implements ValueCodingHandler {

    private static final Comparator<ValueCodec> CODEC_COMPARATOR = Comparator.comparingInt(ValueCodec::getPriority);

    private static final String NULL_VALUE_TEXT = "null";

    private final ValueCodingConfig config;

    private final Map<Class<?>, ValueCodec> classCodecCache = new HashMap<>();
    private final Map<String, ValueCodec> prefixCodecCache = new HashMap<>();

    private final Lock lock = new ReentrantLock();

    public ValueCodingHandlerImpl(ValueCodingConfig config) {
        this.config = config;
        init();
    }

    public void init() {
        // 展开配置。
        List<Class<?>> preCacheClasses = config.getPreCacheClasses();
        List<String> preCachePrefixes = config.getPreCachePrefixes();

        // 预缓存。
        preCacheClasses.forEach(this::preCacheTargetClass);
        preCachePrefixes.forEach(this::preCacheTextPrefix);
    }

    private void preCacheTargetClass(Class<?> targetClass) {
        if (classCodecCache.containsKey(targetClass)) {
            return;
        }

        ValueCodec valueCodec = config.getCodecs().stream().filter(
                codec -> codec.getTargetClass().isAssignableFrom(targetClass)
        ).min(CODEC_COMPARATOR).orElseThrow(
                () -> new IllegalStateException("找不到目标类 " + targetClass + " 对应的编解码器")
        );

        classCodecCache.put(targetClass, valueCodec);
    }

    private void preCacheTextPrefix(String textPrefix) {
        if (prefixCodecCache.containsKey(textPrefix)) {
            return;
        }

        ValueCodec valueCodec = config.getCodecs().stream().filter(
                codec -> codec.getValuePrefix().equals(textPrefix)
        ).min(CODEC_COMPARATOR).orElseThrow(
                () -> new IllegalStateException("找不到值前缀 " + textPrefix + " 对应的编解码器")
        );

        prefixCodecCache.put(textPrefix, valueCodec);
    }

    @BehaviorAnalyse
    @Nonnull
    @Override
    public String encode(@Nullable Object target) throws HandlerException {
        lock.lock();
        try {
            // 对 null 进行特殊处理。
            if (Objects.isNull(target)) {
                return NULL_VALUE_TEXT;
            }

            // 获取目标的类。
            Class<?> targetClass = target.getClass();
            // 获取编解码器。
            ValueCodec valueCodec = findByTargetClass(targetClass);
            // 编码。
            String valuePrefix = valueCodec.getValuePrefix();
            String encode = valueCodec.encode(target);
            return valuePrefix + Constants.FLAT_DATA_VALUE_PREFIX_DELIMITER + encode;
        } catch (DctException e) {
            throw e;
        } catch (Exception e) {
            throw new DctException(e);
        } finally {
            lock.unlock();
        }
    }

    private ValueCodec findByTargetClass(Class<?> targetClass) {
        if (classCodecCache.containsKey(targetClass)) {
            return classCodecCache.get(targetClass);
        }

        ValueCodec valueCodec = config.getCodecs().stream().filter(
                codec -> codec.getTargetClass().isAssignableFrom(targetClass)
        ).min(CODEC_COMPARATOR).orElseThrow(
                () -> new IllegalStateException("找不到目标类 " + targetClass + " 对应的编解码器")
        );

        classCodecCache.put(targetClass, valueCodec);
        return valueCodec;
    }

    @BehaviorAnalyse
    @Nullable
    @Override
    public Object decode(@Nonnull String text) throws HandlerException {
        lock.lock();
        try {
            // 对 null 进行特殊处理。
            if (Objects.equals(text, NULL_VALUE_TEXT)) {
                return null;
            }

            // 获取文本的前缀。
            String textPrefix = text.substring(0, text.indexOf(Constants.FLAT_DATA_VALUE_PREFIX_DELIMITER));
            // 获取编解码器。
            ValueCodec valueCodec = findByTextPrefix(textPrefix);
            // 去除前缀并解码。
            text = text.substring(textPrefix.length() + Constants.FLAT_DATA_VALUE_PREFIX_DELIMITER.length());
            return valueCodec.decode(text);
        } catch (DctException e) {
            throw e;
        } catch (Exception e) {
            throw new DctException(e);
        } finally {
            lock.unlock();
        }
    }

    private ValueCodec findByTextPrefix(String textPrefix) {
        if (prefixCodecCache.containsKey(textPrefix)) {
            return prefixCodecCache.get(textPrefix);
        }

        ValueCodec valueCodec = config.getCodecs().stream().filter(
                codec -> codec.getValuePrefix().equals(textPrefix)
        ).min(CODEC_COMPARATOR).orElseThrow(
                () -> new IllegalStateException("找不到值前缀 " + textPrefix + " 对应的编解码器")
        );

        prefixCodecCache.put(textPrefix, valueCodec);
        return valueCodec;
    }

    @Override
    public String toString() {
        return "ValueCodingHandlerImpl{" +
                "config=" + config +
                ", classCodecCache=" + classCodecCache +
                ", prefixCodecCache=" + prefixCodecCache +
                '}';
    }
}
