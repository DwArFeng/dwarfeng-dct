package com.dwarfeng.dct.impl.handler.vc;

import com.dwarfeng.dct.sdk.handler.vc.AbstractValueCodec;
import com.dwarfeng.dct.sdk.util.Constants;

import javax.annotation.Nonnull;

/**
 * Long 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class LongValueCodec extends AbstractValueCodec {

    public LongValueCodec() {
        super(Long.class, "long", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return Long.toString((Long) target);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return Long.parseLong(text);
    }

    @Override
    public String toString() {
        return "LongValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
