package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;

/**
 * Boolean 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class BooleanValueCodec extends AbstractValueCodec {

    public BooleanValueCodec() {
        super(Boolean.class, "boolean", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return Boolean.toString((Boolean) target);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return Boolean.parseBoolean(text);
    }

    @Override
    public String toString() {
        return "BooleanValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
