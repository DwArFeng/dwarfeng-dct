package com.dwarfeng.dct.impl.handler.vc;

import com.dwarfeng.dct.sdk.handler.vc.AbstractValueCodec;
import com.dwarfeng.dct.sdk.util.Constants;

import javax.annotation.Nonnull;

/**
 * Double 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DoubleValueCodec extends AbstractValueCodec {

    public DoubleValueCodec() {
        super(Double.class, "double", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return Double.toString((Double) target);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return Double.parseDouble(text);
    }

    @Override
    public String toString() {
        return "DoubleValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
