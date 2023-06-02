package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;

/**
 * Integer 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class IntegerValueCodec extends AbstractValueCodec {

    public IntegerValueCodec() {
        super(Integer.class, "integer", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return Integer.toString((Integer) target);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return Integer.parseInt(text);
    }

    @Override
    public String toString() {
        return "IntegerValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
