package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.exception.DctException;
import com.dwarfeng.dct.handler.ValueCodec;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nonnull;

/**
 * 值编解码器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractValueCodec implements ValueCodec {

    protected final Class<?> targetClass;
    protected final String valuePrefix;
    protected final int priority;

    public AbstractValueCodec(@Nonnull Class<?> targetClass, @Nonnull String valuePrefix, int priority) {
        this.targetClass = targetClass;
        this.valuePrefix = valuePrefix;
        this.priority = priority;
    }

    @Nonnull
    @Override
    public Class<?> getTargetClass() {
        return targetClass;
    }

    @Nonnull
    @Override
    public String getValuePrefix() {
        return valuePrefix;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Nonnull
    @Override
    public String encode(@Nonnull Object target) throws HandlerException {
        try {
            return doEncode(target);
        } catch (DctException e) {
            throw e;
        } catch (Exception e) {
            throw new DctException(e);
        }
    }

    /**
     * 编码方法的具体实现。
     *
     * @param target 指定的目标对象。
     * @return 编码后的文本。
     * @throws Exception 任何可能的异常。
     */
    @Nonnull
    protected abstract String doEncode(@Nonnull Object target) throws Exception;

    @Nonnull
    @Override
    public Object decode(@Nonnull String text) throws HandlerException {
        try {
            return doDecode(text);
        } catch (DctException e) {
            throw e;
        } catch (Exception e) {
            throw new DctException(e);
        }
    }

    /**
     * 解码方法的具体实现。
     *
     * @param text 指定的文本。
     * @return 解码后的对象。
     * @throws Exception 任何可能的异常。
     */
    @Nonnull
    protected abstract Object doDecode(@Nonnull String text) throws Exception;

    @Override
    public String toString() {
        return "AbstractValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
