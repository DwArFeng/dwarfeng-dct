package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;
import java.math.BigInteger;

/**
 * BigInteger 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class BigIntegerValueCodec extends AbstractValueCodec {

    public BigIntegerValueCodec() {
        super(BigInteger.class, "big_integer", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return target.toString();
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return new BigInteger(text);
    }

    @Override
    public String toString() {
        return "BigIntegerValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
