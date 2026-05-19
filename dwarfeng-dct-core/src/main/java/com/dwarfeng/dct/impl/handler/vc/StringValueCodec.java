package com.dwarfeng.dct.impl.handler.vc;

import com.dwarfeng.dct.sdk.handler.vc.AbstractValueCodec;
import com.dwarfeng.dct.sdk.util.Constants;

import javax.annotation.Nonnull;

/**
 * String 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class StringValueCodec extends AbstractValueCodec {

    public StringValueCodec() {
        super(String.class, "string", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return (String) target;
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return text;
    }

    @Override
    public String toString() {
        return "StringValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
