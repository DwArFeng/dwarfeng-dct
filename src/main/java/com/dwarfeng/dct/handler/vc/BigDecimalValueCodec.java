package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * BigDecimal 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@ComponentScan
public class BigDecimalValueCodec extends AbstractValueCodec {

    public BigDecimalValueCodec() {
        super(BigDecimal.class, "big_decimal", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return ((BigDecimal) target).toPlainString();
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return new BigDecimal(text);
    }

    @Override
    public String toString() {
        return "BigDecimalValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
