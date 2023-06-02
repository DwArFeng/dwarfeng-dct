package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;

/**
 * Short 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ShortValueCodec extends AbstractValueCodec {

    public ShortValueCodec() {
        super(Short.class, "short", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return Short.toString((Short) target);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return Short.parseShort(text);
    }

    @Override
    public String toString() {
        return "ShortValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
