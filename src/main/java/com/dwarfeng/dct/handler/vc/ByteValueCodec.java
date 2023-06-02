package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;

/**
 * Byte 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ByteValueCodec extends AbstractValueCodec {

    public ByteValueCodec() {
        super(Byte.class, "byte", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return Byte.toString((Byte) target);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return Byte.parseByte(text);
    }

    @Override
    public String toString() {
        return "ByteValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
