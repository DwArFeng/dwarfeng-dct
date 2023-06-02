package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * Date 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DateValueCodec extends AbstractValueCodec {

    public DateValueCodec() {
        super(Date.class, "date", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return Long.toString(((Date) target).getTime());
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return new Date(Long.parseLong(text));
    }

    @Override
    public String toString() {
        return "DateValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
