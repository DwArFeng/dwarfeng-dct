package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;

/**
 * Float 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FloatValueCodec extends AbstractValueCodec {

    public FloatValueCodec() {
        super(Float.class, "float", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return Float.toString((Float) target);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return Float.parseFloat(text);
    }

    @Override
    public String toString() {
        return "FloatValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
